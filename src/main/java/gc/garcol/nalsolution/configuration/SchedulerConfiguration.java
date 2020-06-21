package gc.garcol.nalsolution.configuration;

import gc.garcol.nalsolution.manager.scheduler.SchedulerManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author thai-van
 **/
@Configuration
@PropertySource("classpath:scheduler.properties")
public class SchedulerConfiguration {
    @Value("${scheduler.core-pool-size}")
    private int corePoolSize;

    @Bean
    SchedulerManager schedulerManager() {
        return new SchedulerManager(corePoolSize);
    }
}
