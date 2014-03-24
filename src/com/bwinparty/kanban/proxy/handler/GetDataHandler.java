/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.handler;

import com.bwinparty.kanban.proxy.service.MongoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

/**
 * Created by zyclonite on 12/03/14.
 */
public class GetDataHandler implements Handler<HttpServerRequest> {
    private static final Logger LOG = LoggerFactory.getLogger(GetDataHandler.class);
    private MongoDB db = MongoDB.getInstance();

    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        httpServerRequest.response().setStatusCode(200);
        httpServerRequest.response().putHeader("Content-Type", "application/json; charset=utf-8");
        httpServerRequest.response().putHeader("Access-Control-Allow-Origin", "*");
        final String collection = httpServerRequest.params().get("collection");
        String output;
        try {
            httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            httpServerRequest.response().putHeader("Pragma", "no-cache");
            output = db.selectAll(collection);
        } catch (Exception ex) {
            output = "{\"error\":\"server error\",\"exception\":\""+ex.getMessage()+"\"}";
            LOG.warn(ex.getMessage(), ex);
        }
        httpServerRequest.response().end(output);
    }
}
