/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.util;

import com.bwinparty.kanban.proxy.model.Member;
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
public class MemberAdapter implements JsonSerializer<Member> {
    final DateFormat df;

    public MemberAdapter() {
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(tz);
    }
    @Override
    public JsonElement serialize(Member member, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonMember = new JsonObject();
        addProperty(jsonMember, "ID", member.getID());
        addProperty(jsonMember, "Name", member.getName());
        addProperty(jsonMember, "Email", member.getEmail());
        addProperty(jsonMember, "EmployeeId", member.getEmployeeId());
        addProperty(jsonMember, "IsDisabled", member.getIsDisabled());
        addProperty(jsonMember, "ParticipatesIn", member.getParticipatesIn().toString());
        return jsonMember;
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
