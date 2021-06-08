package com.example.demo.model;

import java.io.Serializable;

public class Activity implements Serializable {
    private static final long serialVersionUID = 1346343600888361290L;
    private int id;
    private String activityName;
    private int categoryId;

    public Activity() {
        super();
    }

    public Activity(int id, String activityName, int categoryId) {
        this.id = id;
        this.activityName = activityName;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
