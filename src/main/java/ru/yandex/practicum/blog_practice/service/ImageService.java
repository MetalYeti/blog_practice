package ru.yandex.practicum.blog_practice.service;


public interface ImageService {
    boolean saveImage(String filename, byte[] rawData);
    void deleteImage(String filename);
}
