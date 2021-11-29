package com.android.food.models;

import static com.android.food.client.ApiUtils.BASE_URL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsRespone {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getCategoryId() {
    return categoryId;
    }

    public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }

    public String getId() {
    return id;
    }

    public void setId(String id) {
    this.id = id;
    }

    public String getImage() {
    return BASE_URL + image;
    }

    public void setImage(String image) {
    this.image = image;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}