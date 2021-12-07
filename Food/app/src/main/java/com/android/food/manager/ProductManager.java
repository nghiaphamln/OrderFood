package com.android.food.manager;

import com.android.food.models.ProductsRespone;

import java.util.ArrayList;

public class ProductManager {
    private ProductsRespone selectedProduct;

    private ArrayList<ProductsRespone> productsByCategory;

    private static ProductManager instance;


    public static ProductManager getInstance() {
        if (instance == null)
            instance = new ProductManager();
        return instance;
    }

    private ProductManager() {

    }

    public ProductsRespone getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductsRespone selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public ArrayList<ProductsRespone> getProductsByCategory() {
        return productsByCategory;
    }

    public void setProductsByCategory(ArrayList<ProductsRespone> productsByCategory) {
        this.productsByCategory = productsByCategory;
    }
}
