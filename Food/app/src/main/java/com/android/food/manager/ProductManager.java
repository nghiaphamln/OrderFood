package com.android.food.manager;

import com.android.food.models.ProductsRespone;

public class ProductManager {
    private ProductsRespone selectedProduct;

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
}
