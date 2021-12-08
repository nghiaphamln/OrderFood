package com.android.food.models;

import com.android.food.domain.DetailDomain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AddOrderRequest {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("total_price")
    @Expose
    private int totalPrice;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("detail")
    @Expose
    private List<DetailDomain> detail = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DetailDomain> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailDomain> detail) {
        this.detail = detail;
    }
}
