/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.handler;

import com.bwinparty.kanban.proxy.service.V1Client;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

/**
 * Created by zyclonite on 12/03/14.
 */
public class GetHandler implements Handler<HttpServerRequest> {
    private static final Logger LOG = LoggerFactory.getLogger(GetHandler.class);
    private Gson gson = new Gson();
    private V1Client v1client = V1Client.getInstance();

    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        LOG.trace("handling request");
        httpServerRequest.response().putHeader("Content-Type", "application/json; charset=utf-8");
        httpServerRequest.response().putHeader("Access-Control-Allow-Origin", "*");
        final String endpoint = httpServerRequest.params().get("endpoint");
        String output;
        try {
            switch (endpoint) {
                case "epics":
                    httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                    httpServerRequest.response().putHeader("Pragma", "no-cache");
                    output = gson.toJson(v1client.getAllCorpEpics());
                    break;
                default:
                    output = "{\"error\":\"wrong endpoint\"}";
            }
            httpServerRequest.response().setStatusCode(200);
        } catch (Exception ex) {
            httpServerRequest.response().setStatusCode(500);
            output = "{\"error\":\"server error\",\"exception\":\""+ex.getMessage()+"\"}";
            LOG.warn(ex.getMessage(), ex);
        }
        httpServerRequest.response().end(output);
    }
}
