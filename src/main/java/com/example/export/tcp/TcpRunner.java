package com.example.export.tcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TcpRunner implements CommandLineRunner {

    @Autowired
    TcpServer server;

    @Override
    public void run(String... args) throws Exception {
        log.info("Tcp running");

        server.start();
    }
}
