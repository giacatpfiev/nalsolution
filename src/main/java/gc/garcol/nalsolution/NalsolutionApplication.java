package gc.garcol.nalsolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EntityScan(basePackages = {"gc.garcol.nalsolution.*"})
@EnableJpaRepositories(basePackages = {"gc.garcol.nalsolution.*"} )
@SpringBootApplication(scanBasePackages = {"gc.garcol.nalsolution.*"})
public class NalsolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(NalsolutionApplication.class, args);
    }

}
