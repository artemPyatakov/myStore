package com.pyatakov.other.objectContainer;

import com.pyatakov.entity.Order;

import java.io.Serializable;
import java.util.List;

public class Orders implements Serializable {

    private List<Order> orders;

    public Orders() {
    }

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
