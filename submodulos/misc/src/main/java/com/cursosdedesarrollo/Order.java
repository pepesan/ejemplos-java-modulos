package com.cursosdedesarrollo;

public class Order {

    private int id;
    private String customerName;

    public Order(int id, String customerName) {
        this.id = id;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                '}';
        // return STR."Order{id=\{id}, customerName='\{customerName}\{'\''}\{'}'}";
    }
}
