package com.android.food.domain;

public class CategoryDomain {
    private String title;
    private String image;

    public CategoryDomain(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String pic) {
        this.image = pic;
    }
}
