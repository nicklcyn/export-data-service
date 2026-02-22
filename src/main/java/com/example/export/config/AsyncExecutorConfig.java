package com.example.export.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableRetry
public class AsyncExecutorConfig {


    @Bean(name = "auditLogExecutor")
    public Executor auditLogExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);      // 核心執行緒數
        executor.setMaxPoolSize(10);     // 最大執行緒數
        executor.setQueueCapacity(500);  // 等候隊列
        executor.setThreadNamePrefix("AuditLog-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "ftpUploadExecutor")
    public Executor ftpUploadExecutor() {
        int nCore = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(nCore);      // 核心執行緒數
        executor.setMaxPoolSize(10);     // 最大執行緒數
        executor.setQueueCapacity(500);  // 等候隊列
        executor.setThreadNamePrefix("FTPUpload-");
        executor.initialize();
        return executor;
    }

}
