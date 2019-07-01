package com.pyatakov.other;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private BigDecimal summa;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String firstName, String lastName, String phone, BigDecimal summa) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.summa = summa;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", summa=" + summa +
                '}';
    }
}
