package com.example.export.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuditLogServiceImplTest {

    @Autowired
    AuditLogServiceImpl logService;
//    @Qualifier("auditLogEx") // 指定你的 Bean 名稱
//    private Executor executor;

    @Test
    void testPoolConfiguration() {
//        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) executor;
//        // 驗證核心參數是否與配置一致
//        assertEquals(10, taskExecutor.getCorePoolSize());
//        assertTrue(taskExecutor.getThreadNamePrefix().startsWith("MyExecutor-"));
        for (int i = 0; i < 30; i++) {
            logService.recordAppendFile();
        }

        for (int i = 0; i < 30; i++) {
            logService.recordSendMQ();
        }

    }
}
