/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hardwarestoredbms;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Order {

    private int orderId;
    private int cusId;
    private java.sql.Date date; 
    private double totalCost;
    private List<String> items;


    public Order(int orderId, int cusId, java.sql.Date date, double totalCost, List<String> items) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.date = date;
        this.totalCost = totalCost;
        this.items = items;
    }

 
    public static Connection getConnection() throws SQLException {
        Config.load();
        return DriverManager.getConnection(
            Config.getDbUrl(), 
            Config.getDbUser(), 
            Config.getDbPassword()
        );
    }


    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setCusId(int cusId) { this.cusId = cusId; }
    public void setDate(java.sql.Date date) { this.date = date; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public void setItems(List<String> items) { this.items = items; }


    public int getOrderId() { return orderId; }
    public int getCusId() { return cusId; }
    public java.sql.Date getDate() { return date; }
    public double getTotalCost() { return totalCost; }
    public List<String> getItems() { return items; }


    public static void addOrder(Scanner sc) {
        System.out.print("Enter order ID: ");
        int orderId = sc.nextInt();
        System.out.print("Enter customer ID: ");
        int cusId = sc.nextInt();


        System.out.print("Enter total cost: ");
        double totalCost = sc.nextDouble();
        sc.nextLine();

        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        String query = "INSERT INTO orders (order_id, cus_id, total_cost, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, cusId);
            stmt.setDouble(5, totalCost);
            stmt.setDate(6, currentDate); 
            stmt.executeUpdate();
            System.out.println("Order added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding order: " + e.getMessage());
        }
    }

  
    public static void editOrder(Scanner sc) {
        System.out.print("Enter order ID to edit: ");
        int orderId = sc.nextInt();
        System.out.print("Enter new customer ID: ");
        int cusId = sc.nextInt();
        System.out.print("Enter new total cost: ");
        double totalCost = sc.nextDouble();
        sc.nextLine(); 

        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        String query = "UPDATE orders SET cus_id = ?, total_cost = ?, date = ? WHERE order_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cusId);
            stmt.setDouble(4, totalCost);
            stmt.setDate(5, currentDate);
            stmt.setInt(6, orderId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Order updated successfully.");
            } else {
                System.out.println("Order ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error editing order: " + e.getMessage());
        }
    }

     public static void deleteOrder(Scanner sc) {
        System.out.print("Enter order ID to delete: ");
        int orderId = sc.nextInt();
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("Order ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }

    public static void listOrder(Scanner sc) {
        System.out.print("Enter order ID to list: ");
        int orderId = sc.nextInt();
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Order ID: " + rs.getInt("order_id"));
                System.out.println("Customer ID: " + rs.getInt("cus_id"));
                System.out.println("Total Cost: " + rs.getDouble("total_cost"));
                System.out.println("Date: " + rs.getDate("date"));
            } else {
                System.out.println("Order ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving order: " + e.getMessage());
        }
    }

    public static void listAllOrders() {
        String query = "SELECT * FROM orders";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Order List:");
            while (rs.next()) {
                System.out.println(rs.getInt("order_id") + " | " +
                                   rs.getInt("cus_id") + " | " +
                                   rs.getDouble("total_cost") + " | " +
                                   rs.getDate("date"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving orders: " + e.getMessage());
        }
    }
    
    public static void backupOrders() {
        String query = "SELECT * FROM orders";
        String backupFile = "orders_backup.csv";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile))) {
 
            writer.write("order_id,cus_id, total_cost,date,items");
            writer.newLine();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int cusId = rs.getInt("cus_id");
                double totalCost = rs.getDouble("total_cost");
                java.sql.Date date = rs.getDate("date");

                String items = rs.getString("items"); 

                writer.write(orderId + "," + cusId + "," + totalCost + "," + date + "," + items);
                writer.newLine();
            }
            System.out.println("Backup completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error backing up orders: " + e.getMessage());
        }
    }

    public static void restoreOrders() {
        String restoreFile = "orders_backup.csv";
        String query = "INSERT INTO orders (order_id, cus_id, total_cost, date, items) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             BufferedReader reader = new BufferedReader(new FileReader(restoreFile));
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            String line;
            reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                int orderId = Integer.parseInt(data[0]);
                int cusId = Integer.parseInt(data[1]);
                double totalCost = Double.parseDouble(data[4]);
                java.sql.Date date = java.sql.Date.valueOf(data[5]); 
                String itemsString = data[6];
                

                List<String> items = Arrays.asList(itemsString.split(";")); 

                stmt.setInt(1, orderId);
                stmt.setInt(2, cusId);
                stmt.setDouble(5, totalCost);
                stmt.setDate(6, date);
                stmt.setString(7, String.join(";", items));
                stmt.executeUpdate();
            }
            System.out.println("Restore completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error restoring orders: " + e.getMessage());
        }
    }
    
    
    
}
