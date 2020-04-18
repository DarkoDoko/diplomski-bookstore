package com.ddoko.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.ddoko.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.ddoko.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.ddoko.domain.User.class.getName());
            createCache(cm, com.ddoko.domain.Authority.class.getName());
            createCache(cm, com.ddoko.domain.User.class.getName() + ".authorities");
            createCache(cm, com.ddoko.domain.Customer.class.getName());
            createCache(cm, com.ddoko.domain.Customer.class.getName() + ".addresses");
            createCache(cm, com.ddoko.domain.Customer.class.getName() + ".orders");
            createCache(cm, com.ddoko.domain.Address.class.getName());
            createCache(cm, com.ddoko.domain.Book.class.getName());
            createCache(cm, com.ddoko.domain.Book.class.getName() + ".authors");
            createCache(cm, com.ddoko.domain.Author.class.getName());
            createCache(cm, com.ddoko.domain.Author.class.getName() + ".books");
            createCache(cm, com.ddoko.domain.Publisher.class.getName());
            createCache(cm, com.ddoko.domain.Category.class.getName());
            createCache(cm, com.ddoko.domain.Category.class.getName() + ".books");
            createCache(cm, com.ddoko.domain.Order.class.getName());
            createCache(cm, com.ddoko.domain.Order.class.getName() + ".orderItems");
            createCache(cm, com.ddoko.domain.OrderItem.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
