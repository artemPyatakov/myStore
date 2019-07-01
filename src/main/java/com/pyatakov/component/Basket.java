package com.pyatakov.component;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Basket implements Serializable {
    private List<ProductsOrder> productIdList = new ArrayList<>();

    public Basket() {
    }

    public Basket(List<ProductsOrder> productIdList) {
        this.productIdList = productIdList;
    }

    public List<ProductsOrder> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<ProductsOrder> productIdList) {
        this.productIdList = productIdList;
    }


    public static class ProductsOrder implements Serializable {
        private int id_product;

        private int amount;

        private BigDecimal summa;

        public ProductsOrder() {
        }

        public ProductsOrder(int id_product, int amount, BigDecimal summa) {
            this.id_product = id_product;
            this.amount = amount;
            this.summa = summa;
        }

        public int getId_product() {
            return id_product;
        }

        public void setId_product(int id_product) {
            this.id_product = id_product;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public BigDecimal getSumma() {
            return summa;
        }

        public void setSumma(BigDecimal summa) {
            this.summa = summa;
        }
    }


    @Override
    public String toString() {
        return "Basket{" +
                "productIdList=" + Arrays.toString(productIdList.toArray()) +
                '}';
    }
}
