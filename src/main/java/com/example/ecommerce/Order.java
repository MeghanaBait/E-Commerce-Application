package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrder(Customer customer, Product product){
        String groupOrderId = "SELECT max(groupOrderId) +1 id FROM orders";
        DBConnection conn = new DBConnection();
        try{
            ResultSet rs = conn.getQuerytable(groupOrderId);
            if(rs.next()){
                String placeOrder ="INSERT INTO orders(groupOrderId, customerId, productId) VALUES("+rs.getInt("id")+","+customer.getId()+", "+product.getId()+")";
                return conn.updateDatabase(placeOrder) != 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){
        String groupOrderId = "SELECT max(groupOrderId) +1 id FROM orders";
        DBConnection conn = new DBConnection();
        try{
            ResultSet rs = conn.getQuerytable(groupOrderId);
            int count = 0;
            if(rs.next()){
                for(Product product: productList){
                    String placeOrder ="INSERT INTO orders(groupOrderId, customerId, productId) VALUES("+rs.getInt("id")+","+customer.getId()+", "+product.getId()+")";
                    count += conn.updateDatabase(placeOrder);
                }
                return count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
