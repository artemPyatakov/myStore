package com.pyatakov.other.objectContainer.product;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {

    private List<ProductDTO> products;

    public Products(List<ProductDTO> products) {
        this.products = products;
    }

    public Products() {
    }

    public List<ProductDTO> getProducts() {
        return products;
    }
}
