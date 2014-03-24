/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.model;

import java.util.Date;

/**
 * Created by zyclonite on 12/03/14.
 */
public class Epic {
    private String ID;
    private String Name;
    private String Number;
    private String Description;
    private String Status;
    private double Swag;
    private String EstimatedDone;
    private Date PlannedEnd;
    private Date PlannedStart;
    private String Health;
    private String HealthComment;
    private Date LaunchDate;
    private String Patentprotection;
    private String Capitalizable;
    private String CreatedBy;
    private String ChangedBy;
    private Date CreateDateUTC;
    private Date ChangeDateUTC;
    private double Risk;
    private double Value;
    private String RequestedBy;
    private String Scope;

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

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public double getSwag() {
        return Swag;
    }

    public void setSwag(double swag) {
        Swag = swag;
    }

    public String getEstimatedDone() {
        return EstimatedDone;
    }

    public void setEstimatedDone(String estimatedDone) {
        EstimatedDone = estimatedDone;
    }

    public Date getPlannedEnd() {
        return PlannedEnd;
    }

    public void setPlannedEnd(Date plannedEnd) {
        PlannedEnd = plannedEnd;
    }

    public Date getPlannedStart() {
        return PlannedStart;
    }

    public void setPlannedStart(Date plannedStart) {
        PlannedStart = plannedStart;
    }

    public String getHealth() {
        return Health;
    }

    public void setHealth(String health) {
        Health = health;
    }

    public String getHealthComment() {
        return HealthComment;
    }

    public void setHealthComment(String healthComment) {
        HealthComment = healthComment;
    }

    public Date getLaunchDate() {
        return LaunchDate;
    }

    public void setLaunchDate(Date launchDate) {
        LaunchDate = launchDate;
    }

    public String getPatentprotection() {
        return Patentprotection;
    }

    public void setPatentprotection(String patentprotection) {
        Patentprotection = patentprotection;
    }

    public String getCapitalizable() {
        return Capitalizable;
    }

    public void setCapitalizable(String capitalizable) {
        Capitalizable = capitalizable;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getChangedBy() {
        return ChangedBy;
    }

    public void setChangedBy(String changedBy) {
        ChangedBy = changedBy;
    }

    public Date getCreateDateUTC() {
        return CreateDateUTC;
    }

    public void setCreateDateUTC(Date createDateUTC) {
        CreateDateUTC = createDateUTC;
    }

    public Date getChangeDateUTC() {
        return ChangeDateUTC;
    }

    public void setChangeDateUTC(Date changeDateUTC) {
        ChangeDateUTC = changeDateUTC;
    }

    public double getRisk() {
        return Risk;
    }

    public void setRisk(double risk) {
        Risk = risk;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
    }

    public String getScope() {
        return Scope;
    }

    public void setScope(String scope) {
        Scope = scope;
    }
}