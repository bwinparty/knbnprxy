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
public class Backlog {
    @SerializedName("_oid")
    private String ID;
    private String Name;

    @SerializedName("Owner.Name")
    private String Owner;
    @SerializedName("Parent.Name")
    private String Parent;
    private Date BeginDate;


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

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getParent() {
        return Parent;
    }

    public void setParent(String parent) {
        Parent = parent;
    }

    public Date getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(Date beginDate) {
        BeginDate = beginDate;
    }

}

