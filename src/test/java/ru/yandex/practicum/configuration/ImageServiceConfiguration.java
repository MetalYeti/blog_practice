package ru.yandex.practicum.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.service.ImageService;
import ru.yandex.practicum.service.ImageServiceImpl;

@Configuration
public class ImageServiceConfiguration {

    @Bean
    public ImageService imageService(@Value("${imageFolder.path}") String path) {
        return new ImageServiceImpl(path);
    }
}
