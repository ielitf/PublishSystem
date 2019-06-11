package com.danggui.publishsystem.bean;

public class CheckDetailListBean {
    private int id;
    private String name;
    private String file_path;
    private String thumbnail_path;
    private String size;
    private String resolution;
    private int advertiserId;
    private String description;
    private int materialCategory;
    private int isDel;
    private String gmtCreate;
    private String gmtModified;
    private int companyId;

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

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(int materialCategory) {
        this.materialCategory = materialCategory;
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

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CheckDetailListBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", file_path='" + file_path + '\'' +
                ", thumbnail_path='" + thumbnail_path + '\'' +
                ", size='" + size + '\'' +
                ", resolution='" + resolution + '\'' +
                ", advertiserId=" + advertiserId +
                ", description='" + description + '\'' +
                ", materialCategory=" + materialCategory +
                ", isDel=" + isDel +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", gmtModified='" + gmtModified + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
