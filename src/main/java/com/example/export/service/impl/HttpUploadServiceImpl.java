package com.example.export.service.impl;


import com.example.export.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service("HTTP_UPLOAD")
public class HttpUploadServiceImpl implements UploadService {
    @Override
    @Retryable(
            value = { IOException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 1.0)
    )
    public void uploadBatch(List<File> files) throws IOException {
        int attempt = RetrySynchronizationManager.getContext().getRetryCount() + 1;
        log.info("正在執行上傳，第 {} 次嘗試 (共 3 次)", attempt);
        log.info("Http upload success");
        throw new IOException("uncheck suuport");
    }

    @Override
    @Recover
    public void recover(IOException e, List<File> files) {
        log.error("重試全部失敗，記錄 Log 或發送通知: ", e);
    }
}
