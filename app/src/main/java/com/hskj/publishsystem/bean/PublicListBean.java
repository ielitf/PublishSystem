package com.hskj.publishsystem.bean;

public class PublicListBean {
    private int id;
    private String name;
    private int playmode;
    private String startTime;
    private String startDate;
    private String endTime;
    private String endDate;
    private int approveStatus;
    private int approveUserId;
    private String approveTime;
    private String approveDate;
    private int isDel;
    private String gmtCreate;
    private String createDate;
    private String gmtModified;
    private String description;
    private String companyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaymode() {
        return playmode;
    }

    public void setPlaymode(int playmode) {
        this.playmode = playmode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(int approveStatus) {
        this.approveStatus = approveStatus;
    }

    public int getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(int approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "PublicListBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playmode=" + playmode +
                ", startTime='" + startTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endDate='" + endDate + '\'' +
                ", approveStatus=" + approveStatus +
                ", approveUserId=" + approveUserId +
                ", approveTime='" + approveTime + '\'' +
                ", approveDate='" + approveDate + '\'' +
                ", isDel=" + isDel +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", gmtModified='" + gmtModified + '\'' +
                ", description='" + description + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
