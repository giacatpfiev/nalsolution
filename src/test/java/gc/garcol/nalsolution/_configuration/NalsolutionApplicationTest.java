package gc.garcol.nalsolution._configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author thai-van
 **/
@EnableCaching
@Configuration
@EnableAutoConfiguration
@ComponentScan({"gc.garcol.nalsolution.*"})
@EntityScan(basePackages = {"gc.garcol.nalsolution.*"})
@EnableJpaRepositories(basePackages = {"gc.garcol.nalsolution.*"})
@EnableTransactionManagement
public class NalsolutionApplicationTest {
}
