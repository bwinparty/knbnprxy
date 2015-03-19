/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.service;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;

/**
 * Created by zyclonite on 12/03/14.
 */
public class VertX {
    private static VertX ourInstance;
    private static final int DEFAULT_PORT = 8585;
    private final HttpServer server;
    private final RouteMatcher routeMatcher;
    private final Vertx vertx;

    static {
        ourInstance = new VertX();
    }

    public static VertX getInstance() {
        return ourInstance;
    }

    private VertX() {
        final AppConfig config = AppConfig.getInstance();
        vertx = VertxFactory.newVertx();
        server = vertx.createHttpServer();
        routeMatcher = new RouteMatcher();
        server.requestHandler(routeMatcher);
        server.listen(config.getInteger("webservice.port", DEFAULT_PORT));
    }
    public void registerGetHandler(final String path, final Handler<HttpServerRequest> handler) {
        routeMatcher.get(path, handler);
    }

    public void registerPostHandler(final String path, final Handler<HttpServerRequest> handler) {
        routeMatcher.post(path, handler);
    }

    public void registerPutHandler(final String path, final Handler<HttpServerRequest> handler) {
        routeMatcher.put(path, handler);
    }

    public void registerDeleteHandler(final String path, final Handler<HttpServerRequest> handler) {
        routeMatcher.delete(path, handler);
    }

    public long setTimer(final long timeout, final Handler<Long> handler) {
        return vertx.setTimer(timeout, handler);
    }

    public long setPeriodic(final long timeout, final Handler<Long> handler) {
        return vertx.setPeriodic(timeout, handler);
    }

    public void cancelTimer(final long timerid) {
        vertx.cancelTimer(timerid);
    }

    public FileSystem getFileSystem() {
        return vertx.fileSystem();
    }

    public EventBus getEventBus() {
        return vertx.eventBus();
    }

    public void shutdown() {
        server.close();
    }
}
