/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.service;

import com.bwinparty.kanban.proxy.model.Epic;
import com.versionone.apiclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyclonite on 12/03/14.
 */
public class V1Client {
    private static V1Client ourInstance;
    private static final Logger LOG = LoggerFactory.getLogger(V1Client.class);
    private static final String META_URL_SUFFIX = "meta.v1/";
    private static final String DATA_URL_SUFFIX = "rest-1.v1/";
    private static final String DEFAULT_URL = "http://127.0.0.1/";
    private final IMetaModel _metaModel;
    private final IServices _services;

    static {
        ourInstance = new V1Client();
    }

    public static V1Client getInstance() {
        return ourInstance;
    }

    private V1Client() {
        final AppConfig config = AppConfig.getInstance();
        V1APIConnector metaConnector = new V1APIConnector(config.getString("versionone.url", DEFAULT_URL) + META_URL_SUFFIX);
        _metaModel = new MetaModel(metaConnector);
        V1APIConnector dataConnector;
        if(config.containsKey("versionone.username")) {
            dataConnector = new V1APIConnector(config.getString("versionone.url", DEFAULT_URL) + DATA_URL_SUFFIX, config.getString("versionone.username"), config.getString("versionone.password"));
        } else {
            dataConnector = new V1APIConnector(config.getString("versionone.url", DEFAULT_URL) + DATA_URL_SUFFIX);
        }
        _services = new Services(_metaModel, dataConnector);
        try {
            _services.getLoggedIn();
            LOG.debug("VersionOne connection initialized");
        } catch (APIException | ConnectionException | OidException e) {
            LOG.error("VersionOne connection failed - "+e.getMessage(), e);
        }
    }

    public List<Epic> getAllCorpEpics() throws Exception {
        LOG.debug("getAllCorpEpics");
        IAssetType requestType = _metaModel.getAssetType("Epic");
        Query query = new Query(requestType);
        IAttributeDefinition idAttribute = requestType.getAttributeDefinition("ID");
        query.getSelection().add(idAttribute);
        IAttributeDefinition nameAttribute = requestType.getAttributeDefinition("Name");
        query.getSelection().add(nameAttribute);
        IAttributeDefinition numberAttribute = requestType.getAttributeDefinition("Number");
        query.getSelection().add(numberAttribute);
        //IAttributeDefinition descriptionAttribute = requestType.getAttributeDefinition("Description");
        //query.getSelection().add(descriptionAttribute);
        IAttributeDefinition statusAttribute = requestType.getAttributeDefinition("Status.Name");
        query.getSelection().add(statusAttribute);
        IAttributeDefinition swagAttribute = requestType.getAttributeDefinition("Swag");
        query.getSelection().add(swagAttribute);
        IAttributeDefinition estimatedDoneAttribute = requestType.getAttributeDefinition("EstimatedDone");
        query.getSelection().add(estimatedDoneAttribute);
        IAttributeDefinition plannedEndAttribute = requestType.getAttributeDefinition("PlannedEnd");
        query.getSelection().add(plannedEndAttribute);
        IAttributeDefinition plannedStartAttribute = requestType.getAttributeDefinition("PlannedStart");
        query.getSelection().add(plannedStartAttribute);
        IAttributeDefinition healthAttribute = requestType.getAttributeDefinition("Custom_Health.Name");
        query.getSelection().add(healthAttribute);
        IAttributeDefinition healthCommentAttribute = requestType.getAttributeDefinition("Custom_HealthComment");
        query.getSelection().add(healthCommentAttribute);
        IAttributeDefinition launchDateAttribute = requestType.getAttributeDefinition("Custom_LaunchDate");
        query.getSelection().add(launchDateAttribute);
        IAttributeDefinition patentprotectionAttribute = requestType.getAttributeDefinition("Custom_Patentprotection2");
        query.getSelection().add(patentprotectionAttribute);
        IAttributeDefinition capitalizableAttribute = requestType.getAttributeDefinition("Custom_Capitalizable2");
        query.getSelection().add(capitalizableAttribute);
        IAttributeDefinition createdByAttribute = requestType.getAttributeDefinition("CreatedBy.Name");
        query.getSelection().add(createdByAttribute);
        IAttributeDefinition changedByAttribute = requestType.getAttributeDefinition("ChangedBy.Name");
        query.getSelection().add(changedByAttribute);
        IAttributeDefinition createDateUTCAttribute = requestType.getAttributeDefinition("CreateDateUTC");
        query.getSelection().add(createDateUTCAttribute);
        IAttributeDefinition changeDateUTCAttribute = requestType.getAttributeDefinition("ChangeDateUTC");
        query.getSelection().add(changeDateUTCAttribute);
        IAttributeDefinition riskAttribute = requestType.getAttributeDefinition("Risk");
        query.getSelection().add(riskAttribute);
        IAttributeDefinition valueAttribute = requestType.getAttributeDefinition("Value");
        query.getSelection().add(valueAttribute);
        IAttributeDefinition requestedByAttribute = requestType.getAttributeDefinition("RequestedBy");
        query.getSelection().add(requestedByAttribute);
        IAttributeDefinition scopeAttribute = requestType.getAttributeDefinition("Scope.Name");
        query.getSelection().add(scopeAttribute);

        IAttributeDefinition corpInterestAttribute = requestType.getAttributeDefinition("Custom_Corporateinterest2.Name");

        FilterTerm where = new FilterTerm(corpInterestAttribute);
        where.equal("Yes");
        query.setFilter(where);

        QueryResult result = _services.retrieve(query);

        List<Epic> epics = new ArrayList<>();
        for (Asset request : result.getAssets()) {
            Epic epic = new Epic();
            //System.out.println(idAttribute.getAttributeType().name());
            if(request.getAttribute(idAttribute).getValue() != null) {
                epic.setID(request.getAttribute(idAttribute).getValue().toString());
            }
            if(request.getAttribute(nameAttribute).getValue() != null) {
                epic.setName(request.getAttribute(nameAttribute).getValue().toString());
            }
            if(request.getAttribute(numberAttribute).getValue() != null) {
                epic.setNumber(request.getAttribute(numberAttribute).getValue().toString());
            }
            /*if(request.getAttribute(descriptionAttribute).getValue() != null) {
                epic.setDescription(request.getAttribute(descriptionAttribute).getValue().toString());
            }*/
            if(request.getAttribute(statusAttribute).getValue() != null) {
                epic.setStatus(request.getAttribute(statusAttribute).getValue().toString());
            }
            if(request.getAttribute(swagAttribute).getValue() != null) {
                epic.setSwag((double) request.getAttribute(swagAttribute).getValue());
            }
            if(request.getAttribute(estimatedDoneAttribute).getValue() != null) {
                epic.setEstimatedDone(request.getAttribute(estimatedDoneAttribute).getValue().toString());
            }
            if(request.getAttribute(plannedEndAttribute).getValue() != null) {
                epic.setPlannedEnd((Date) request.getAttribute(plannedEndAttribute).getValue());
            }
            if(request.getAttribute(plannedStartAttribute).getValue() != null) {
                epic.setPlannedStart((Date) request.getAttribute(plannedStartAttribute).getValue());
            }
            if(request.getAttribute(healthAttribute).getValue() != null) {
                epic.setHealth(request.getAttribute(healthAttribute).getValue().toString());
            }
            if(request.getAttribute(healthCommentAttribute).getValue() != null) {
                epic.setHealthComment(request.getAttribute(healthCommentAttribute).getValue().toString());
            }
            if(request.getAttribute(launchDateAttribute).getValue() != null) {
                epic.setLaunchDate((Date) request.getAttribute(launchDateAttribute).getValue());
            }
            if(request.getAttribute(patentprotectionAttribute).getValue() != null) {
                epic.setPatentprotection(request.getAttribute(patentprotectionAttribute).getValue().toString());
            }
            if(request.getAttribute(capitalizableAttribute).getValue() != null) {
                epic.setCapitalizable(request.getAttribute(capitalizableAttribute).getValue().toString());
            }
            if(request.getAttribute(createdByAttribute).getValue() != null) {
                epic.setCreatedBy(request.getAttribute(createdByAttribute).getValue().toString());
            }
            if(request.getAttribute(changedByAttribute).getValue() != null) {
                epic.setChangedBy(request.getAttribute(changedByAttribute).getValue().toString());
            }
            if(request.getAttribute(createDateUTCAttribute).getValue() != null) {
                epic.setCreateDateUTC((Date)request.getAttribute(createDateUTCAttribute).getValue());
            }
            if(request.getAttribute(changeDateUTCAttribute).getValue() != null) {
                epic.setChangeDateUTC((Date)request.getAttribute(changeDateUTCAttribute).getValue());
            }
            if(request.getAttribute(riskAttribute).getValue() != null) {
                epic.setRisk((double)request.getAttribute(riskAttribute).getValue());
            }
            if(request.getAttribute(valueAttribute).getValue() != null) {
                epic.setValue((double)request.getAttribute(valueAttribute).getValue());
            }
            if(request.getAttribute(requestedByAttribute).getValue() != null) {
                epic.setRequestedBy(request.getAttribute(requestedByAttribute).getValue().toString());
            }
            if(request.getAttribute(scopeAttribute).getValue() != null) {
                epic.setScope(request.getAttribute(scopeAttribute).getValue().toString());
            }
            epics.add(epic);
        }

        return epics;
    }
}
