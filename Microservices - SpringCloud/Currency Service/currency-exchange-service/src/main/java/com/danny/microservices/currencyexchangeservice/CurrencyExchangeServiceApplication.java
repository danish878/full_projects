package com.danny.microservices.currencyexchangeservice;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableDiscoveryClient
public class CurrencyExchangeServiceApplication implements CommandLineRunner {

    @Autowired
    private ExchangeValueRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @Override
    public void run(String... args) throws Exception {
        ExchangeValue exchangeValue = new ExchangeValue("AED", "PKR", BigDecimal.valueOf(39.99));
        repository.save(exchangeValue);

        exchangeValue = new ExchangeValue("USD", "PKR", BigDecimal.valueOf(146.88));
        repository.save(exchangeValue);

        exchangeValue = new ExchangeValue("CAD", "PKR", BigDecimal.valueOf(108.45));
        repository.save(exchangeValue);
    }
}
