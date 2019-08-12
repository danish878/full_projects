package com.danny.jba.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@EnableWebMvc
@ComponentScan(basePackages = "com.danny.jba")
@EnableScheduling
@EnableConfigurationProperties
@PropertySource("classpath:application.properties")
@PropertySource("classpath:jpa.properties")
public class AppConfig implements WebMvcConfigurer {

    /**
     * To enable @PropertySource to read external configuration from file
     *
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer getPropertySourcePlaceholderConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //noinspection SpringMVCViewInspection
        registry.addViewController("/login").setViewName("login");
    }

    // Not needed here because Apached Tiles is handling this
//    /**
//     * This configuration will use InternalResourceViewResolver to resolve jsp pages back to client user.
//     * All the pages will be located in src/main/webapp/WEB-INF
//     * @return InternalResourceViewResolver
//     */
//    @Bean
//    public InternalResourceViewResolver getJspViewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
////        viewResolver.setViewClass(JstlView.class);
////        viewResolver.setPrefix("/WEB-INF/");
////        viewResolver.setSuffix(".jsp");
////        viewResolver.setOrder(1);
//        return viewResolver;
//    }

//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver getMultipartResolver() {
//        return new CommonsMultipartResolver();
//    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * messageSource method name can not be change to others, else there will has errors when browse web page.
     * messageSource is used to make page text internalization. The message file is saved in src/main/resources/messages
     * You should create config folder by yourself if it dose not exist.
     *
     * @return MessageSource
     */
    @Bean(name = "messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
        ret.setBasename("classpath:messages");
        ret.setCacheSeconds(1);
        ret.setUseCodeAsDefaultMessage(true);
        ret.setDefaultEncoding("UTF-8");
        return ret;
    }

//    @Bean
//    public CookieLocaleResolver localeResolver(){
//        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
//        localeResolver.setDefaultLocale(Locale.ENGLISH);
//        localeResolver.setCookieName("locale-cookie");
//        localeResolver.setCookieMaxAge(3600);
//        return localeResolver;
//    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }

}