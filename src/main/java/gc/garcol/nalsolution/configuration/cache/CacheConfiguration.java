package gc.garcol.nalsolution.configuration.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * ref: https://github.com/mvpjava/spring-caffeine-cache-tutorial
 * @author thai-van
 **/
@Slf4j
@EnableCaching
@Configuration
@PropertySource("classpath:cache.properties")
public class CacheConfiguration {

    @Value("${cache.init-capacity}")
    private int initSize;

    @Value("${cache.max-size}")
    private int maxSize;

    @Value("${cache.expired-millis}")
    private long expiredMillis;

    @Bean
    public CacheManager cacheManager() {

        Field[] fields = CacheName.class.getDeclaredFields();

        Object[] cacheObjs = Arrays.asList(fields)
                                .stream()
                                .map(field -> getFieldValue(field))
                                .toArray();

        String[] cacheNames = Arrays.copyOf(cacheObjs, cacheObjs.length, String[].class);

        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Object getFieldValue(Field field) {
        try {
            return field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(initSize)
                .maximumSize(maxSize)
                .expireAfterAccess(expiredMillis, TimeUnit.MILLISECONDS)
                .weakKeys()
                .removalListener(new CustomRemovalListener())
                .recordStats();
    }

    class CustomRemovalListener implements RemovalListener<Object, Object> {
        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            log.info("removal listerner called with key {}, cause {}, evicted {}", key, cause.toString(), cause.wasEvicted());
        }
    }

}
