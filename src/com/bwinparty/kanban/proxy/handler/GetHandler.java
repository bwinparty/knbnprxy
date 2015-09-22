/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.handler;

import com.bwinparty.kanban.proxy.model.Epic;
import com.bwinparty.kanban.proxy.model.Backlog;
import com.bwinparty.kanban.proxy.model.Program;
import com.bwinparty.kanban.proxy.model.Team;
import com.bwinparty.kanban.proxy.model.Member;


import com.bwinparty.kanban.proxy.service.V1Client;
import com.bwinparty.kanban.proxy.util.DateAdapter;
import com.bwinparty.kanban.proxy.util.EpicAdapter;
import com.bwinparty.kanban.proxy.util.BacklogAdapter;
import com.bwinparty.kanban.proxy.util.ProgramAdapter;
import com.bwinparty.kanban.proxy.util.TeamAdapter;
import com.bwinparty.kanban.proxy.util.MemberAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

import java.util.Date;

/**
 * Created by zyclonite on 12/03/14.
 */
public class GetHandler implements Handler<HttpServerRequest> {
    private static final Logger LOG = LoggerFactory.getLogger(GetHandler.class);
    private static final Gson GSON_Epic = new GsonBuilder().registerTypeAdapter(Epic.class, new EpicAdapter()).create();
    private static final Gson GSON_Backlog = new GsonBuilder().registerTypeAdapter(Backlog.class, new BacklogAdapter()).create();
    private static final Gson GSON_Programs = new GsonBuilder().registerTypeAdapter(Program.class, new ProgramAdapter()).create();
    private static final Gson GSON_Teams = new GsonBuilder().registerTypeAdapter(Team.class, new TeamAdapter()).create();
    private static final Gson GSON_Members = new GsonBuilder().registerTypeAdapter(Member.class, new MemberAdapter()).create();
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
                    output = GSON_Epic.toJson(v1client.getEpics());
                    break;
                case "backlogs":
                    httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                    httpServerRequest.response().putHeader("Pragma", "no-cache");
                    output = GSON_Backlog.toJson(v1client.getBacklogs());
                    break;
                case "programs":
                    httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                    httpServerRequest.response().putHeader("Pragma", "no-cache");
                    output = GSON_Programs.toJson(v1client.getPrograms());
                    break;
                case "teams":
                    httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                    httpServerRequest.response().putHeader("Pragma", "no-cache");
                    output = GSON_Teams.toJson(v1client.getTeams());
                    break;
                case "members":
                    httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                    httpServerRequest.response().putHeader("Pragma", "no-cache");
                    output = GSON_Members.toJson(v1client.getMembers());
                    break;
                case "epicsprogress":
                    httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                    httpServerRequest.response().putHeader("Pragma", "no-cache");
                    output = GSON_Members.toJson(v1client.getEpicProgress());
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
