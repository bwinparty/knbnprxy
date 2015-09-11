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
public class Team {
    @SerializedName("_oid")
    private String ID;
    private String Name;
    private String Description;
    @SerializedName("ScopeLabel.Name")
    private String Program;
    @SerializedName("Scope.Name")
    private String Backlog;
    @SerializedName("Schedule.Name")
    private String SprintSchedule;
    private Object Participants;


    public String getProgram() {
        return Program;
    }

    public void setProgram(String program) {
        Program = program;
    }

    public String getBacklog() {
        return Backlog;
    }

    public void setBacklog(String backlog) {
        Backlog = backlog;
    }

    public String getSprintSchedule() {
        return SprintSchedule;
    }

    public void setSprintSchedule(String sprintSchedule) {
        SprintSchedule = sprintSchedule;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public Object getParticipants() {
        return Participants;
    }
    public void setParticipants(Object participants) {
        Participants = participants;
    }


}

