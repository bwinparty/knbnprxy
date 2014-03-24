/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.service;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zyclonite on 22/03/14.
 */
public class MongoDB {
    private static MongoDB ourInstance;
    private static final Logger LOG = LoggerFactory.getLogger(MongoDB.class);
    private static final String DEFAULT_URL = "mongodb://127.0.0.1:27017/kanban";
    private DB database;

    static {
        ourInstance = new MongoDB();
    }

    public static MongoDB getInstance() {
        return ourInstance;
    }

    private MongoDB() {
        final AppConfig config = AppConfig.getInstance();
        final String uri = config.getString("mongodb.uri", DEFAULT_URL);
        try {
            final MongoClientOptions.Builder options = new MongoClientOptions.Builder();
            options.autoConnectRetry(true);
            options.maxAutoConnectRetryTime(15); //s
            options.connectTimeout(10000); //ms
            options.socketTimeout(30000); //ms
            options.socketKeepAlive(false);
            options.connectionsPerHost(10);
            options.threadsAllowedToBlockForConnectionMultiplier(5);
            options.maxWaitTime(120000); //ms
            final MongoClientURI mongouri = new MongoClientURI(uri, options);
            final MongoClient mongo = new MongoClient(mongouri);
            database = mongo.getDB(mongouri.getDatabase());

            if ((mongouri.getUsername() != null) && (!mongouri.getUsername().trim().isEmpty()) && (!database.authenticate(mongouri.getUsername(), mongouri.getPassword()))) {
                throw new Exception("Unable to authenticate with MongoDB server.");
            }
            LOG.debug("MongoDB connection initialized");
        } catch (Exception e) {
            database = null;
            LOG.error("VersionOne connection initialized");
        }
    }

    public void insert(String json, String collection) {
        LOG.debug("insert into "+collection);
        DBCollection coll = database.getCollection(collection);
        coll.insert((DBObject)JSON.parse(json));
    }

    public String selectAll(String collection) {
        LOG.debug("select from "+collection);
        DBCollection coll = database.getCollection(collection);
        DBCursor cursor = coll.find();
        StringBuilder json = new StringBuilder("[");
        while (cursor.hasNext()) {
            json.append(cursor.next().toString());
            json.append(",");
        }
        if(json.length() > 1) {
            json.setLength(json.length() - 1);
        }
        json.append("]");
        return json.toString();
    }

    public void delete(String id, String collection) {
        LOG.debug("delete item "+id+" from "+collection);
        DBCollection coll = database.getCollection(collection);
        BasicDBObject query = new BasicDBObject();
        query.append("_id", new ObjectId(id));
        coll.remove(query);
    }

    public void update(String id, String json, String collection) {
        LOG.debug("update item "+id+" in "+collection);
        DBCollection coll = database.getCollection(collection);
        BasicDBObject query = new BasicDBObject();
        query.append("_id", new ObjectId(id));
        coll.update(query, (DBObject)JSON.parse(json));
    }
}