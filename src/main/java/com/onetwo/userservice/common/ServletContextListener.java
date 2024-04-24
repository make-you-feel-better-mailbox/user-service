package com.onetwo.userservice.common;

import io.grpc.Server;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import lombok.RequiredArgsConstructor;

@WebListener
@RequiredArgsConstructor
public class ServletContextListener implements jakarta.servlet.ServletContextListener {

    private final Server server;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        server.shutdown();
        jakarta.servlet.ServletContextListener.super.contextDestroyed(sce);
    }
}
