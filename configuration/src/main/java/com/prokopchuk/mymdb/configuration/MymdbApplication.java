package com.prokopchuk.mymdb.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.prokopchuk.mymdb")
@EntityScan("com.prokopchuk.mymdb.common.persistence.entity")
@EnableJpaRepositories("com.prokopchuk.mymdb.common.persistence.repo")
public class MymdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymdbApplication.class, args);
    }

}
