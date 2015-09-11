/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.util;

import com.bwinparty.kanban.proxy.model.Backlog;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zyclonite on 02/04/14.
 */
public class BacklogAdapter implements JsonSerializer<Backlog> {
    final DateFormat df;

    public BacklogAdapter() {
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(tz);
    }
    @Override
    public JsonElement serialize(Backlog backlog, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonBacklog = new JsonObject();
        addProperty(jsonBacklog, "ID", backlog.getID());
        addProperty(jsonBacklog, "Name", backlog.getName());
        addProperty(jsonBacklog, "Owner", backlog.getOwner());
        addProperty(jsonBacklog, "Parent", backlog.getParent());
        addProperty(jsonBacklog, "BeginDate", backlog.getBeginDate());

        return jsonBacklog;
    }

    private void addProperty(final JsonObject jsonObject, final String id, final Date value) {
        if(value == null) {
            return;
        }
        jsonObject.addProperty(id, df.format(value));
    }

    private void addProperty(final JsonObject jsonObject, final String id, final Number value) {
        if(value == null) {
            return;
        }
        jsonObject.addProperty(id, value);
    }

    private void addProperty(final JsonObject jsonObject, final String id, final String value) {
        if(value == null) {
            return;
        }
        jsonObject.addProperty(id, value);
    }

    private void addProperty(final JsonObject jsonObject, final String id, final Boolean value) {
        if(value == null) {
            return;
        }
        jsonObject.addProperty(id, value);
    }

    private void addProperty(final JsonObject jsonObject, final String id, final Character value) {
        if(value == null) {
            return;
        }
        jsonObject.addProperty(id, value);
    }


}
