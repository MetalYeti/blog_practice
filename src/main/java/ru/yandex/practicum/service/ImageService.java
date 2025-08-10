package ru.yandex.practicum.service;

import java.net.URISyntaxException;

public interface ImageService {
    void saveImage(String filename, byte[] rawData) throws URISyntaxException;
    void deleteImage(String filename);
}
