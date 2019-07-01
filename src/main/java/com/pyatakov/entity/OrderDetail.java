package com.pyatakov.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_detail")
    private Long id;

    @Column(name = "id_product")
    private int id_product;

    @Column(name = "amount")
    private int amount;

    @Column(name = "summa")
    private BigDecimal summa;

    @JsonIgnore
    @Version
    @Column(name = "version")
    private int version;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(int id_product, int amount, BigDecimal summa, Order order) {
        this.id_product = id_product;
        this.amount = amount;
        this.summa = summa;
        this.order = order;
    }

    public OrderDetail(int id_product, int amount, BigDecimal summa) {
        this.id_product = id_product;
        this.amount = amount;
        this.summa = summa;
    }

    public Long getId() {
        return id;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id_product=" + id_product +
                ", amount=" + amount +
                ", summa=" + summa +
                '}';
    }
}
