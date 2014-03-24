/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy;

import com.bwinparty.kanban.proxy.handler.*;
import com.bwinparty.kanban.proxy.service.VertX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zyclonite on 11/03/14.
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private VertX vertx = VertX.getInstance();
    private final Object sync = new Object();
    private final CountDownLatch stopLatch = new CountDownLatch(1);

    public Application() {
        vertx.registerGetHandler("/rest/:endpoint", new GetHandler());
        vertx.registerPostHandler("/rest/:endpoint", new PostHandler());
        vertx.registerGetHandler("/data/:collection", new GetDataHandler());
        vertx.registerPostHandler("/data/:collection", new InsertDataHandler());
        vertx.registerPutHandler("/data/:collection", new UpdateDataHandler());
        vertx.registerDeleteHandler("/data/:collection", new DeleteDataHandler());
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.addShutdownHook();
        LOG.info("application started");
        app.block();
    }

    private void block() {
        while (true) {
            try {
                stopLatch.await();
                break;
            } catch (InterruptedException e) {
                //Ignore
            }
        }
    }

    private void unblock() {
        stopLatch.countDown();
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOG.info("shutting down...");
                synchronized (sync) {
                }
                vertx.shutdown();
            }
        });
    }
}
