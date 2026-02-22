package com.example.export.service.impl;

import com.example.export.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service("FTP_UPLOAD")
@Slf4j
public class FtpUploadServiceImpl implements UploadService {
    private static final FTPSClient ftpsClient= new FTPSClient();

    @Async("ftpUploadExecutor")
    @Retryable(
        value = { IOException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 1.0)
    )
    public void uploadBatch(List<File> files) throws IOException {

        // 取得目前重試次數 (從 0 開始，所以 +1)
        int attempt = RetrySynchronizationManager.getContext().getRetryCount() + 1;

        try {
            log.info("正在執行上傳，第 {} 次嘗試 (共 3 次)", attempt);
            ftpsClient.connect("your.server.com");
            ftpsClient.login("user", "pass");
            ftpsClient.enterLocalPassiveMode();
            ftpsClient.setFileType(FTP.BINARY_FILE_TYPE);

            for (File file : files) {
                try (InputStream is = new FileInputStream(file)) {
                    boolean success = ftpsClient.storeFile(file.getName(), is);
                    if (!success) throw new IOException("無法上傳檔案: " + file.getName());
                }
            }
            ftpsClient.logout();
        } finally {
            if (ftpsClient.isConnected()) {
                ftpsClient.disconnect();
            }
        }
    }

    // 最終失敗處理 (選用)
    @Recover
    public void recover(IOException e, List<File> files) {
        log.error("重試全部失敗，記錄 Log 或發送通知: ", e);
    }


}
