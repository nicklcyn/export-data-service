package com.example.export.service.impl;

import com.example.export.service.UploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UploadServiceImplTest {

    @Autowired
    private Map<String, UploadService> upload;

    @Test
    void uploadBatchTest() {

        try {
            upload.get("FTP_UPLOAD").uploadBatch(new ArrayList<>());
        } catch (IOException e) {
            ;
        }

        try {
            upload.get("HTTP_UPLOAD").uploadBatch(new ArrayList<>());
        } catch (IOException e) {
            ;
        }
    }
}