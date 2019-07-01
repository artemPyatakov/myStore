package com.pyatakov.service;

import com.pyatakov.entity.Order;
import com.pyatakov.other.OrderDTO;

import java.util.List;

public interface OrderService {

    List<Order> findAll();
    Order save(Order order);
    void updateStatus(Long id_order, int id_status);
    List<OrderDTO> findOrderForCSV();
}
