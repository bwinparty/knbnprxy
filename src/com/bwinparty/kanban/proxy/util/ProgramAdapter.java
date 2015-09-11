/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.util;

import com.bwinparty.kanban.proxy.model.Program;
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
public class ProgramAdapter implements JsonSerializer<Program> {
    final DateFormat df;

    public ProgramAdapter() {
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(tz);
    }
    @Override
    public JsonElement serialize(Program program, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonProgram = new JsonObject();
        addProperty(jsonProgram, "ID", program.getID());
        addProperty(jsonProgram, "Name", program.getName());
        addProperty(jsonProgram, "Description", program.getDescription());
        addProperty(jsonProgram, "Backlogs", Arrays.toString(program.getBacklogs()));


        return jsonProgram;
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
