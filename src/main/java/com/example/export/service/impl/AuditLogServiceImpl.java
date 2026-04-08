package com.example.export.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("auditLog")
@Slf4j(topic = "AUDIT_LOGGER")
public class AuditLogServiceImpl {


    @Async("auditLogExecutor")
    public void recordAppendFile() {
        log.info("Current thread info {}", Thread.currentThread().getName());
        log.debug("Current thread debug {}", Thread.currentThread().getName());
    }

    @Async
    public void recordIntoDataBase (){
        log.info("Current thread debug {}" , Thread.currentThread().getName());
        log.debug("Current thread debug {}", Thread.currentThread().getName());
    }

    public void recordSendMQ() {
        log.info("Current thread MQ {}" ,  Thread.currentThread().getName());
        log.debug("Current thread debug {}", Thread.currentThread().getName());
    }
}
