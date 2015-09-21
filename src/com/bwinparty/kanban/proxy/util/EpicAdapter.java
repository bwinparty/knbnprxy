/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.util;

import com.bwinparty.kanban.proxy.model.Epic;
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
public class EpicAdapter implements JsonSerializer<Epic> {
    final DateFormat df;

    public EpicAdapter() {
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(tz);
    }
    @Override
    public JsonElement serialize(Epic epic, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonEpic = new JsonObject();
        addProperty(jsonEpic, "ID", epic.getID());
        addProperty(jsonEpic, "Name", epic.getName());
        addProperty(jsonEpic, "Number", epic.getNumber());
        addProperty(jsonEpic, "Description", epic.getDescription());
        addProperty(jsonEpic, "Status", epic.getStatus());
        addProperty(jsonEpic, "Swag", epic.getSwag());
        addProperty(jsonEpic, "EstimatedDone", epic.getEstimatedDone());
        addProperty(jsonEpic, "PlannedEnd", epic.getPlannedEnd());
        addProperty(jsonEpic, "PlannedStart", epic.getPlannedStart());
        addProperty(jsonEpic, "Health", epic.getHealth());
        addProperty(jsonEpic, "HealthComment", epic.getHealthComment());
        addProperty(jsonEpic, "LaunchDate", epic.getLaunchDate());
        addProperty(jsonEpic, "Patentprotection", epic.getPatentprotection());
        addProperty(jsonEpic, "Capitalizable", epic.getCapitalizable());
        addProperty(jsonEpic, "CreatedBy", epic.getCreatedBy());
        addProperty(jsonEpic, "ChangedBy", epic.getChangedBy());
        addProperty(jsonEpic, "CreateDateUTC", epic.getCreateDateUTC());
        addProperty(jsonEpic, "ChangeDateUTC", epic.getChangeDateUTC());
        addProperty(jsonEpic, "CategoryName", epic.getCategoryName());
        addProperty(jsonEpic, "Risk", epic.getRisk());
        addProperty(jsonEpic, "Value", epic.getValue());
        addProperty(jsonEpic, "RequestedBy", epic.getRequestedBy());
        addProperty(jsonEpic, "BusinessBacklog", epic.getBusinessBacklog());
        addProperty(jsonEpic, "BusinessBacklogID", epic.getBusinessBacklogID().toString());
        addProperty(jsonEpic, "EpicRootNumber", epic.getEpicRootNumber());
        addProperty(jsonEpic, "EpicRootName", epic.getEpicRootName());
        addProperty(jsonEpic, "EpicAttachments", Arrays.toString(epic.getEpicAttachments()));
        addProperty(jsonEpic, "EpicAttachmentNames", Arrays.toString(epic.getEpicAttachmentNames()));
        addProperty(jsonEpic, "StrategicThemesNames", Arrays.toString(epic.getStrategicThemesNames()));
        addProperty(jsonEpic, "InitiativeOwner", epic.getInitiativeOwner());
        addProperty(jsonEpic, "LaunchDateWhy", epic.getLaunchDateWhy());
        addProperty(jsonEpic, "ElevatorPitch", epic.getElevatorPitch());
        addProperty(jsonEpic, "PortfolioApproval", epic.getPortfolioApproval());
        addProperty(jsonEpic, "IsClosed", epic.getIsClosed());
        addProperty(jsonEpic, "IsCompleted", epic.getIsCompleted());

        return jsonEpic;
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
