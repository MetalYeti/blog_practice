package ru.yandex.practicum.blog_practice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ImageServiceImpl implements ImageService {

    private final String PROJECT_PATH;

    public ImageServiceImpl(@Value("${imageFolder.path}") String path) {
        this.PROJECT_PATH = path;
    }

    @Override
    public boolean saveImage(String filename, byte[] rawData) {
        File destFile = new File(PROJECT_PATH);
        if (!destFile.exists()) {
            destFile.mkdir();
        }
        try (FileOutputStream fos = new FileOutputStream(destFile + "/" + filename)) {
            fos.write(rawData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void deleteImage(String filename) {
        File destFile = new File(PROJECT_PATH + "/" + filename);
        if (destFile.exists()) {
            destFile.delete();
        }
    }
}
