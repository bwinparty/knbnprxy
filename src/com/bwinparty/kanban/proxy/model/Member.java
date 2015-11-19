/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zyclonite on 12/03/14.
 */
public class Member {
    @SerializedName("_oid")
    private String ID;
    private String Name;
    private String Email;
    @SerializedName("Custom_EmployeeID")
    private String EmployeeId;
    @SerializedName("IsLoginDisabled")
    private Boolean IsDisabled;
    private Object ParticipatesIn;

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getIsDisabled() {
        return IsDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        IsDisabled = isDisabled;
    }

    public Object getParticipatesIn() {
        return ParticipatesIn;
    }

    public void setParticipatesIn(Object participatesIn) {
        ParticipatesIn = participatesIn;
    }
}

