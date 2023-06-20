package com.example.ecommerce;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrder(Customer customer, Product product){
        String groupOrderId = "SELECT max(groupOrderId) +1 id FROM orders";
        DBConnection conn = new DBConnection();
        try{
            ResultSet rs = conn.getQuerytable(groupOrderId);
            if(rs.next()){
                String placeOrder ="INSERT INTO ORDERS(groupOrderId, customerId, productId) VALUES("+rs.getInt(groupOrderId)+","+customer.getId()+", "+product.getId()+")";
                return conn.updateDatabase(placeOrder) != 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
