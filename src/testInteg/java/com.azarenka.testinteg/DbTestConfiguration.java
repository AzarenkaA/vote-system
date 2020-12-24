package com.azarenka.testinteg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Integration Test Configuration.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 17.12.2020
 */
@EntityScan(value = "com.azarenka")
@EnableJpaRepositories({"com.azarenka.votingsystem.repository"})
@Configuration
@PropertySource(value = "classpath:application.properties")
@EnableTransactionManagement
@SpringBootApplication
public class DbTestConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(DbTestConfiguration.class, args);
    }
}
