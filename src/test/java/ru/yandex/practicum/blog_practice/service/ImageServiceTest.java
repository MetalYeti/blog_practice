package ru.yandex.practicum.blog_practice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.ResourceUtils;
import ru.yandex.practicum.blog_practice.configuration.ImageServiceConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {ImageServiceConfiguration.class})
@ActiveProfiles("test")
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    public void testSaveAndDeleteFile(@Value("${imageFolder.path}") String path) throws IOException {

        File fileName = ResourceUtils.getFile("classpath:test_pic.jpg");
        byte[] content = Files.readAllBytes(fileName.toPath());
        Path testFilePath = Paths.get(path, fileName.getName());

        boolean success = imageService.saveImage(fileName.getName(), content);

        assertTrue(success);
        assertTrue(Files.exists(testFilePath));
        assertArrayEquals(content, Files.readAllBytes(testFilePath));

        imageService.deleteImage(fileName.getName());
        assertFalse(Files.exists(testFilePath));
    }
}
