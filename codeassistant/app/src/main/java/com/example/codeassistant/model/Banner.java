// 包路径：com.example.codeassistant.model
package com.example.codeassistant.model;

// Banner实体（对应后端Banner）
public class Banner {
    private long id;
    private String title;
    private String imageUrl;
    private String linkUrl;
    private int sortOrder;
    private int isEnabled;
    private String createTime;
    private String updateTime;

    // Getter和Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }

    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }

    public int getIsEnabled() { return isEnabled; }
    public void setIsEnabled(int isEnabled) { this.isEnabled = isEnabled; }

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }
}

// Banner添加请求DTO（对应后端BannerAddDTO）
class BannerAddDTO {
    private String title;
    private String imageUrl;
    private String linkUrl;
    private int sortOrder;
    private int isEnabled;

    // Getter和Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }

    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }

    public int getIsEnabled() { return isEnabled; }
    public void setIsEnabled(int isEnabled) { this.isEnabled = isEnabled; }
}