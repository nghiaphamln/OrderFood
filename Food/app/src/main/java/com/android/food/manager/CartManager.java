package com.android.food.manager;

import com.android.food.models.ProductsRespone;

import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;

    private ArrayList<ProductsRespone> cartList;

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ArrayList<ProductsRespone> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<ProductsRespone> cartList) {
        if (this.cartList == null) {
            this.cartList = cartList;
        }
        else {
            this.cartList.addAll(cartList);
        }
    }
}
