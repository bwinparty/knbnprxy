/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zyclonite on 02/04/14.
 */
public class DateAdapter implements JsonDeserializer<Date> {
    private static final String FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX";
    private static final String FORMAT2 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS";
    private final DateFormat sdf1 = new SimpleDateFormat(FORMAT1);
    private final DateFormat sdf2 = new SimpleDateFormat(FORMAT2);
    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            String jsontime = jsonElement.getAsString();
            if (jsontime.length() == FORMAT1.length())
                return sdf1.parse(jsontime);
            return sdf2.parse(jsontime);
        } catch (final java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
