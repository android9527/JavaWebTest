package com.servlet.json.entity;

/**
 * Created by chenfeiyue on 16/8/29.
 */
public class Order {
    private int id;
    private String customer;
    private String orderDate, orderPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer='" + customer + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                '}';
    }
}
