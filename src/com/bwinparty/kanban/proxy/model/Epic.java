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
public class Epic {
    @SerializedName("_oid")
    private String ID;
    private String Name;
    private String Number;
    private String Description;
    @SerializedName("Status.Name")
    private String Status;
    private double Swag;
    private String EstimatedDone;
    private Date PlannedEnd;
    private Date PlannedStart;
    @SerializedName("Custom_Health.Name")
    private String Health;
    @SerializedName("Custom_HealthComment")
    private String HealthComment;
    @SerializedName("Custom_LaunchDate")
    private Date LaunchDate;
    @SerializedName("Custom_Patentprotection2.Name")
    private String Patentprotection;
    @SerializedName("Custom_Capitalizable2.Name")
    private String Capitalizable;
    @SerializedName("CreatedBy.Name")
    private String CreatedBy;
    @SerializedName("ChangedBy.Name")
    private String ChangedBy;
    private Date CreateDateUTC;
    private Date ChangeDateUTC;
    @SerializedName("Category.Name")
    private String CategoryName;
    private double Risk;
    private double Value;
    private String RequestedBy;
    @SerializedName("Scope.Name")
    private String BusinessBacklog;
    @SerializedName("Scope.ID")
    private Object BusinessBacklogID;
    @SerializedName("Super.Name")
    private String EpicRootName;
    @SerializedName("Super.Number")
    private String EpicRootNumber;
    @SerializedName("Attachments")
    private Object[] EpicAttachments;
    @SerializedName("Attachments.Name")
    private Object[] EpicAttachmentNames;
    @SerializedName("StrategicThemes.Name")
    private Object[] StrategicThemesNames;

    @SerializedName("Custom_InitativeOwner")
    private String InitiativeOwner;
    @SerializedName("Custom_LaunchDateWhy")
    private String LaunchDateWhy;
    @SerializedName("Custom_ElevatorPitch")
    private String ElevatorPitch;
    @SerializedName("Custom_Corporateinterest2.Name")
    private String PortfolioApproval;
    private Boolean IsClosed;
    private Boolean IsCompleted;

    public Boolean getIsClosed() {
        return IsClosed;
    }

        public void setIsClosed(Boolean isClosed) {
        IsClosed = isClosed;

    }

    public Boolean getIsCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        IsCompleted = isCompleted;
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

    public String getBusinessBacklog() {
        return BusinessBacklog;
    }

    public void setBusinessBacklog(String businessBacklog) {
        BusinessBacklog = businessBacklog;
    }

    public Object getBusinessBacklogID() {
        return BusinessBacklogID;
    }

    public void setBusinessBacklogID(Object businessBacklogID) {
        BusinessBacklogID = businessBacklogID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }



    public String getEpicRootName() {
        return EpicRootName;
    }

    public void setEpicRootName(String epicRootName) {
        EpicRootName = epicRootName;
    }

    public String getEpicRootNumber() {
        return EpicRootNumber;
    }

    public void setEpicRootNumber(String epicRootNumber) {
        EpicRootNumber = epicRootNumber;
    }



    public Object[] getEpicAttachments() {

        return EpicAttachments;
    }

    public void setEpicAttachments(Object[] epicAttachments) {
        EpicAttachments = epicAttachments;
    }

    public Object[] getEpicAttachmentNames() {

        return EpicAttachmentNames;
    }

    public void setEpicAttachmentNames(Object[] epicAttachmentNames) {
        EpicAttachmentNames = epicAttachmentNames;
    }

    public Object[] getStrategicThemesNames() {

        return StrategicThemesNames;
    }

    public void setStrategicThemesNames(Object[] strategicThemesNames) {
        StrategicThemesNames = strategicThemesNames;
    }


    public String getInitiativeOwner() {
        return InitiativeOwner;
    }
    public void setInitiativeOwner(String initiativeOwner) {
        InitiativeOwner = initiativeOwner;
    }

    public String getLaunchDateWhy() {
        return LaunchDateWhy;
    }
    public void setLaunchDateWhy(String launchDateWhy) {
        LaunchDateWhy = launchDateWhy;
    }

    public String getElevatorPitch() {
        return ElevatorPitch;
    }
    public void setElevatorPitch(String elevatorPitch) {
        ElevatorPitch = elevatorPitch;
    }

    public String getPortfolioApproval() {
        return PortfolioApproval;
    }
    public void setPortfolioApproval(String portfolioApproval) {
        PortfolioApproval = portfolioApproval;
    }

}
