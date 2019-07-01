package com.pyatakov.other.objectContainer.product;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDTO implements Serializable {

    private Long id;
    private String name;
    private BigDecimal price;
    private String info;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductDTO(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", info='" + info + '\'' +
                '}';
    }
}
