package com.pyatakov.repository;

import com.pyatakov.entity.Order;
import com.pyatakov.other.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    @Modifying
    @Query("update Order o set o.orderStatus = :id_status where o.id = :id_order")
    void updateStatus(@Param("id_order") Long id_order, @Param("id_status") int id_status);

    
    @Query("select new com.pyatakov.other.OrderDTO(o.id, o.firstName, o.lastName, o.phone, sum(d.summa)) " +
           "from Order o join o.orderDetails d  " +
           "where o.orderStatus = 3" +
           "group by o.id, o.orderStatus, o.firstName, o.lastName, o.phone")
    List<OrderDTO> findOrderForCSV();
}
