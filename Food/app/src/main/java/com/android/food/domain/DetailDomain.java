package com.android.food.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailDomain {

    @SerializedName("product_id")
    @Expose
    private Integer productId;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
