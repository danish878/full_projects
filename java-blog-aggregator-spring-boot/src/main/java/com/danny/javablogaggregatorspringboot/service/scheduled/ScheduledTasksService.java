package com.danny.javablogaggregatorspringboot.service.scheduled;

import com.danny.javablogaggregatorspringboot.dto.ItemDto;
import com.danny.javablogaggregatorspringboot.entity.Blog;
import com.danny.javablogaggregatorspringboot.entity.Category;
import com.danny.javablogaggregatorspringboot.entity.Configuration;
import com.danny.javablogaggregatorspringboot.entity.NewsItem;
import com.danny.javablogaggregatorspringboot.repository.BlogRepository;
import com.danny.javablogaggregatorspringboot.repository.ItemRepository;
import com.danny.javablogaggregatorspringboot.repository.NewsItemRepository;
import com.danny.javablogaggregatorspringboot.service.*;
import com.danny.javablogaggregatorspringboot.service.ItemService.OrderType;
import com.danny.javablogaggregatorspringboot.service.ItemService.MaxType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Slf4j
@AllArgsConstructor

@Service
public class ScheduledTasksService {

    private BlogRepository blogRepository;
    private BlogService blogService;
    private ItemService itemService;
    private ItemRepository itemRepository;
    private NewsItemRepository newsItemRepository;
    private NewsService newsService;
    private ConfigurationService configurationService;
    private CategoryService categoryService;
    private AllCategoriesService allCategoriesService;
    private RestTemplate restTemplate;

    /**
     * For each blog retrieve latest items and store them into database.
     */
    // 1 hour = 60 seconds * 60 minutes * 1000
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    @CacheEvict(value = "itemCount", allEntries = true)
    public void reloadBlogItems() {
        long millis = System.currentTimeMillis();
        log.info("reloading blog items started");
        // first process blogs which have aggregator = null,
        // next blogs with aggregator = false
        // and last blogs with aggregator = true
        List<Blog> blogs = blogRepository.findAll(new Sort(Direction.ASC, "aggregator"));

        // TODO this is very memory-intensive
        List<String> allLinks = itemRepository.findAllLinks();
        List<String> allLowercaseTitles = itemRepository.findAllLowercaseTitles();
        Map<String, Object> allLinksMap = new HashMap<>();
        for (String link : allLinks) {
            allLinksMap.put(link, null);
        }
        Map<String, Object> allLowercaseTitlesMap = new HashMap<>();
        for (String title : allLowercaseTitles) {
            allLowercaseTitlesMap.put(title, null);
        }
        for (Blog blog : blogs) {
            // reindex timeout must have passed in order to index this blog
            if (reindexTimeoutPassed(blog.getLastIndexedDate())) {
                // archived blogs won't be indexed
                if (blog.getArchived() == null || !blog.getArchived()) {
                    blogService.saveItems(blog, allLinksMap, allLowercaseTitlesMap);
                }
            }
        }
        blogService.setLastIndexedDateFinish(new Date());
        log.info("reloading blog items finished: {} seconds", ((System.currentTimeMillis() - millis) / 1000));
    }

    /**
     * Return whether reindex timeout passed. Reindex timeout is between two
     * dates: current date and the last time some item was saved for some blog.
     *
     * @param lastReindexDate lastReindexDate
     * @return boolean
     */
    protected boolean reindexTimeoutPassed(Date lastReindexDate) {
        if (lastReindexDate == null) {
            return true;
        }
        Calendar lastReindexCalendar = new GregorianCalendar();
        lastReindexCalendar.setTime(lastReindexDate);
        // reindex timeout is 6 hours
        lastReindexCalendar.add(Calendar.HOUR_OF_DAY, 6);
        return lastReindexCalendar.before(new GregorianCalendar());
    }

    /**
     * Run every day
     */
    @Transactional
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000, initialDelay = 10000)
    public void computePopularity() {
        long millis = System.currentTimeMillis();
        log.info("compute popularity started");
        for (Blog blog : blogService.findAll(true)) {
            Calendar dateFromCalendar = new GregorianCalendar();
            dateFromCalendar.add(Calendar.MONTH, -3);
            Integer sumPopularity = itemRepository.getSocialSum(blog.getId(), dateFromCalendar.getTime());
            int popularity = 0;
            if (sumPopularity != null) {
                popularity = sumPopularity;
            }
            blogRepository.setPopularity(blog.getId(), popularity);
        }
        log.info("compute popularity finished: {} seconds", ((System.currentTimeMillis() - millis) / 1000));
    }

    int[] getCurrentWeekAndYear(Date currentDate) {
        LocalDate date = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int week = date.get(woy);
        int year = date.getYear();
        return new int[]{week, year};
    }

    /**
     * Generate best of weekly news
     */
    @Transactional
    // cron format: second, minute, hour, day of month, month and day of week
    // should run every saturday at 7 A.M.
    @Scheduled(cron = "0 0 7 * * SUN")
    public void addWeeklyNews() {
        long millis = System.currentTimeMillis();
        log.info("add weekly news started");
        final int[] weekAndYear = getCurrentWeekAndYear(new Date());
        final int week = weekAndYear[0];
        final int year = weekAndYear[1];
        String currentWeekShortTitle = "best-of-" + week + "-" + year;
        NewsItem newsItem = newsItemRepository.findByShortName(currentWeekShortTitle);
        if (newsItem == null) {
            newsItem = new NewsItem();
            Configuration configuration = configurationService.find();
            newsItem.setTitle(configuration.getChannelTitle() + " Weekly: Best of " + week + "/" + year);
            newsItem.setShortName(currentWeekShortTitle);
            newsItem.setShortDescription("Best of " + configuration.getChannelTitle() + ", year " + year + ", week " + week);
            String description = "<p>" + configuration.getChannelTitle() + " brings you interesting news every day.";
            description += " Each week I select the best of:</p>";
            List<Category> categories = categoryService.findAll();
            for (Category category : categories) {
                description += "<table class='table'>";
                description += "<tr>";
                description += "<td>";
                description += "<h4>" + category.getName() + "</h4>";
                description += "</td>";
                description += "</tr>";
                List<ItemDto> dtoItems = itemService.getDtoItems(0, false, OrderType.MOST_VIEWED, MaxType.WEEK, new Integer[]{category.getId()});
                for (int i = 0; i < dtoItems.size() && i < 5; i++) {
                    ItemDto itemDto = dtoItems.get(i);
                    description += "<tr>";
                    description += "<td>";
                    description += "<a href='" + itemDto.getLink() + "' target='_blank'>";
                    description += "<img src='/spring/icon/" + itemDto.getBlog().getId() + "' style='float:left;padding-right:5px;height:30px' />";
                    description += itemDto.getTitle();
                    description += "</a>";
                    description += "</td>";
                    description += "</tr>";
                }
                description += "</table>";
            }
            newsItem.setDescription(description);
            newsService.save(newsItem);
            log.info("add weekly news finished: {} seconds", ((System.currentTimeMillis() - millis) / 1000));
        }
    }

    // will run every 3 hours
    @Scheduled(fixedDelay = 3 * 60 * 60 * 1000, initialDelay = 1000)
    public void retrieveSocialShareCount() {
        long millis = System.currentTimeMillis();
        log.info("retrieve social share count started");
        Integer[] allCategories = allCategoriesService.getAllCategoryIds();
        int page = 0;
        int retrievedItems = 0;
        do {
            List<ItemDto> dtoItems = itemService.getDtoItems(page++, true, OrderType.LATEST, MaxType.WEEK, allCategories);
            retrievedItems = dtoItems.size();
            String twitterOauth = configurationService.find().getTwitterOauth();
            for (ItemDto itemDto : dtoItems) {
                try {
                    // https://developer.twitter.com/en/apps
                    if (twitterOauth != null && !twitterOauth.trim().isEmpty()) {
                        String[] twitterOauthParts = twitterOauth.split(":");
                        String consumerKey = twitterOauthParts[0];
                        String consumerKeySecret = twitterOauthParts[1];
                        String accessToken = twitterOauthParts[2];
                        String accessTokenSecret = twitterOauthParts[3];
                        ConfigurationBuilder cb = new ConfigurationBuilder();
                        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerKeySecret).setOAuthAccessToken(accessToken)
                                .setOAuthAccessTokenSecret(accessTokenSecret);

                        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
                        Twitter twitter = twitterFactory.getInstance();

                        RateLimitStatus rateLimitStatus = twitter.getRateLimitStatus().get("/search/tweets");
                        int remaining = rateLimitStatus.getRemaining();
                        if (remaining <= 1) {
                            log.info("Twitter rate limit approaching, will wait for 15 minutes");
                            // sleep for 15 minutes, this will reset the limit for sure
                            Thread.sleep(15 * 60 * 1000);
                        }
                        Query query = new Query(itemDto.getLink());
                        QueryResult result = twitter.search(query);
                        int retweetCount = result.getTweets().size();
                        if (retweetCount > itemDto.getTwitterRetweetCount()) {
                            itemRepository.setTwitterRetweetCount(itemDto.getId(), retweetCount);
                        }
                    }
                } catch (Exception ex) {
                    log.error("Error retrieving twitter shares", ex);
                }
                String facebookSharesUrl = "https://graph.facebook.com/?id=" + itemDto.getLink();
                try {
                    // To prevent "Application request limit reached" error
                    // https://stackoverflow.com/questions/14092989/facebook-api-4-application-request-limit-reached
                    // https://developers.facebook.com/docs/graph-api/advanced/rate-limiting/
                    // limit seems to be 200 requests per user per hour, which means every 1 request per 18 seconds, 21 seconds sleep should be safe
                    Thread.sleep(21_000);
                    Optional<Integer> optional;
                    try {
                        optional = getFacebookShares(facebookSharesUrl, itemDto.getFacebookShareCount());
                    } catch (Exception e) {
                        log.error("Facebook throttled getting facebook shares, will try again");
                        // probably got throttled anyway :-( sleep for 10 minutes and try again
                        Thread.sleep(10 * 60 * 1000);
                        optional = getFacebookShares(facebookSharesUrl, itemDto.getFacebookShareCount());
                    }
                    optional.ifPresent(shareCount -> itemRepository.setFacebookShareCount(itemDto.getId(), shareCount));

                } catch (Exception ex) {
                    log.error("Error retrieving facebook shares, url = {}", facebookSharesUrl, ex);
                }
                // not working since 2018 :-(
                // https://warfareplugins.com/linkedin-drops-share-counts/
//				try {
//					LinkedinShareJson linkedinShareJson = restTemplate.getForObject("https://www.linkedin.com/countserv/count/share?format=json&url=" + itemDto.getLink(), LinkedinShareJson.class);
//					if (linkedinShareJson.getCount() != itemDto.getLinkedinShareCount()) {
//						itemRepository.setLinkedinShareCount(itemDto.getId(), linkedinShareJson.getCount());
//					}
//				} catch (Exception ex) {
//					log.error("Error retrieving linkedin shares", ex);
//				}
            }
        } while (retrievedItems > 0);
        log.info("retrieve social share count finished: {} seconds", ((System.currentTimeMillis() - millis) / 1000));
    }

//	private static class LinkedinShareJson {
//
//		private int count;
//
//		public int getCount() {
//			return count;
//		}
//
//		@SuppressWarnings("unused")
//		public void setCount(int count) {
//			this.count = count;
//		}
//	}

    private Optional<Integer> getFacebookShares(String facebookSharesUrl, int currentFacebookShareCount) {
        FacebookShareJson facebookShareJson = restTemplate.getForObject(facebookSharesUrl, FacebookShareJson.class);
        if (facebookShareJson != null && facebookShareJson.getShare() != null && facebookShareJson.getShare().getShareCount() != currentFacebookShareCount) {
            return Optional.of(facebookShareJson.getShare().getShareCount());
        }
        return Optional.empty();
    }

    private static class Share {

        @JsonProperty("share_count")
        private int shareCount;

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

    }

    private static class FacebookShareJson {

        private Share share;

        public Share getShare() {
            return share;
        }

        public void setShare(Share share) {
            this.share = share;
        }
    }

}