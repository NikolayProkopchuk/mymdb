package com.prokopchuk.mymdb.common.persistance.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EntityScan("com.prokopchuk.mymdb.common.persistence.entity")
@EnableJpaRepositories("com.prokopchuk.mymdb.common.persistence.repo")
@ComponentScan("com.prokopchuk.mymdb.common.persistence")
public class TestConfig {
}
