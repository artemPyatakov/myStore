package com.pyatakov.service.impl;

import com.google.common.collect.Lists;
import com.pyatakov.entity.Order;
import com.pyatakov.other.OrderDTO;
import com.pyatakov.repository.OrderRepository;
import com.pyatakov.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Order> findAll() {
        return Lists.newArrayList(orderRepository.findAll());
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void updateStatus(Long id_order, int id_status) {
         orderRepository.updateStatus(id_order, id_status);
    }


    @Override
    public List<OrderDTO> findOrderForCSV() {
        return orderRepository.findOrderForCSV();
    }
    
}
