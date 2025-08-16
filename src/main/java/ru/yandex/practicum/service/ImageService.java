package ru.yandex.practicum.service;

import java.net.URISyntaxException;

public interface ImageService {
    boolean saveImage(String filename, byte[] rawData);
    void deleteImage(String filename);
}
