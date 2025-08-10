package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;

@Service
public class ImageServiceImpl implements ImageService {
    private final String PROJECT_PATH = "g:/server/file_storage/images";
    @Override
    public void saveImage(String filename, byte[] rawData) throws URISyntaxException {
        File destFile = new File(PROJECT_PATH);
        if (!destFile.exists()) {
            destFile.mkdir();
        }
        try (FileOutputStream fos = new FileOutputStream(destFile + "/" + filename)) {
            fos.write(rawData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImage(String filename) {
        File destFile = new File(PROJECT_PATH + "/" + filename);
        if (destFile.exists()) {
            destFile.delete();
        }
    }
}
