/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hardwarestoredbms;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Supplier {

    private int supplierID;
    private String supplierName;
    private String supplierEmail;
    private String supplierPhone;
    private String supplierAddress;
    private int stockID; 


    public Supplier(int supplierID, String supplierName, String supplierEmail, String supplierPhone, String supplierAddress, int stockID) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
        this.supplierPhone = supplierPhone;
        this.supplierAddress = supplierAddress;
        this.stockID = stockID;
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hardwaredb", "root", "29102001");
    }


    public void setSupplierID(int supplierID) { this.supplierID = supplierID; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public void setSupplierEmail(String supplierEmail) { this.supplierEmail = supplierEmail; }
    public void setSupplierPhone(String supplierPhone) { this.supplierPhone = supplierPhone; }
    public void setSupplierAddress(String supplierAddress) { this.supplierAddress = supplierAddress; }
    public void setStockID(int stockID) { this.stockID = stockID; }

    public int getSupplierID() { return supplierID; }
    public String getSupplierName() { return supplierName; }
    public String getSupplierEmail() { return supplierEmail; }
    public String getSupplierPhone() { return supplierPhone; }
    public String getSupplierAddress() { return supplierAddress; }
    public int getStockID() { return stockID; }

    public static void addSupplier(Scanner sc) {
        System.out.println("Enter Supplier ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Supplier Name: ");
        String name = sc.nextLine();
        System.out.println("Enter Supplier Email: ");
        String email = sc.nextLine();
        System.out.println("Enter Supplier Phone: ");
        String phone = sc.nextLine();
        System.out.println("Enter Supplier Address: ");
        String address = sc.nextLine();
        System.out.println("Enter Stock ID: ");
        int stockID = sc.nextInt();

        String query = "INSERT INTO supplier (supplier_name, supplier_email, supplier_phone, supplier_address, stock_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setInt(6, stockID);
            stmt.executeUpdate();
            System.out.println("Supplier added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding supplier: " + e.getMessage());
        }
    }

    public static void editSupplier(Scanner sc) {
        System.out.println("Enter Supplier ID to edit: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new Supplier Name: ");
        String name = sc.nextLine();
        System.out.println("Enter new Supplier Email: ");
        String email = sc.nextLine();
        System.out.println("Enter new Supplier Phone: ");
        String phone = sc.nextLine();
        System.out.println("Enter new Supplier Address: ");
        String address = sc.nextLine();
        System.out.println("Enter new Stock ID: ");
        int stockID = sc.nextInt();

        String query = "UPDATE supplier SET supplier_name = ?, supplier_email = ?, supplier_phone = ?, supplier_address = ?, stock_id = ? WHERE supplier_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            stmt.setInt(5, stockID);
            stmt.setInt(6, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Supplier updated successfully.");
            } else {
                System.out.println("Supplier ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error editing supplier: " + e.getMessage());
        }
    }


    public static void deleteSupplier(Scanner sc) {
        System.out.println("Enter Supplier ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM supplier WHERE supplier_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Supplier deleted successfully.");
            } else {
                System.out.println("Supplier ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting supplier: " + e.getMessage());
        }
    }


    public static void listSupplier(Scanner sc) {
        System.out.println("Enter Supplier ID to list: ");
        int id = sc.nextInt();

        String query = "SELECT * FROM supplier WHERE supplier_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Supplier ID not found.");
                return; 
            }

            System.out.println("Supplier ID: " + rs.getInt("supplier_id"));
            System.out.println("Name: " + rs.getString("supplier_name"));
            System.out.println("Email: " + rs.getString("supplier_email"));
            System.out.println("Phone: " + rs.getString("supplier_phone"));
            System.out.println("Address: " + rs.getString("supplier_address"));
            System.out.println("Stock ID: " + rs.getInt("stock_id")); 
        } catch (SQLException e) {
            System.err.println("Error retrieving supplier: " + e.getMessage());
        }
    }



    public static void listAllSuppliers() {
        String query = "SELECT * FROM supplier";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Supplier List:");
            while (rs.next()) {
                System.out.println(rs.getInt("supplier_id") + " | " +
                                   rs.getString("supplier_name") + " | " +
                                   rs.getString("supplier_email") + " | " +
                                   rs.getString("supplier_phone") + " | " +
                                   rs.getString("supplier_address") + " | " +
                                   rs.getInt("stock_id")); 
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving suppliers: " + e.getMessage());
        }
    }


    public static void backupSuppliers() {
        String query = "SELECT * FROM supplier";
        String backupFile = "suppliers_backup.csv";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile))) {
            writer.write("supplier_id,supplier_name,supplier_email,supplier_phone,supplier_address,stock_id");
            writer.newLine();
            while (rs.next()) {
                writer.write(rs.getInt("supplier_id") + "," +
                             rs.getString("supplier_name") + "," +
                             rs.getString("supplier_email") + "," +
                             rs.getString("supplier_phone") + "," +
                             rs.getString("supplier_address") + "," +
                             rs.getInt("stock_id"));
                writer.newLine();
            }
            System.out.println("Backup completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error backing up suppliers: " + e.getMessage());
        }
    }

    public static void restoreSuppliers() {
        String restoreFile = "suppliers_backup.csv";
        String query = "INSERT INTO supplier (supplier_id, supplier_name, supplier_email, supplier_phone, supplier_address, stock_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             BufferedReader reader = new BufferedReader(new FileReader(restoreFile));
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                stmt.setInt(1, Integer.parseInt(data[0])); 
                stmt.setString(2, data[1]);              
                stmt.setString(3, data[2]);            
                stmt.setString(4, data[3]);            
                stmt.setString(5, data[4]);          
                stmt.setInt(6, Integer.parseInt(data[5])); 
                stmt.executeUpdate();
            }
            System.out.println("Restore completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error restoring suppliers: " + e.getMessage());
        }
    }


    public static void deleteAllSuppliers() {
        String query = "DELETE FROM supplier";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("All suppliers deleted successfully. Total rows deleted: " + rows);
            } else {
                System.out.println("No suppliers to delete.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting all suppliers: " + e.getMessage());
        }
    }

    
}
