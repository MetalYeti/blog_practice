package ru.yandex.practicum.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableJdbcRepositories
@ComponentScan(basePackages = {"ru.yandex.practicum"})
public class WebTestConfiguration {
}
