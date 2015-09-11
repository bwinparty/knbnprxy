/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by zyclonite on 12/03/14.
 */
public class Program {
    @SerializedName("_oid")
    private String ID;
    private String Name;
    private String Description;
    @SerializedName("Scopes.Name")
    private String[] Backlogs;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String[] getBacklogs() {
        return Backlogs;
    }

    public void setBacklogs(String[] backlogs) {
        Backlogs = backlogs;
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }


    public String getName() {
        return Name;
    }
       public void setName(String name) {
        Name = name;
    }



}

