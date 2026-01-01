package com.mycompany.hardwarestoredbms;


import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Customer {

    private int customerID;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;


    public Customer(int customerID, String customerName, String customerEmail, String customerPhoneNumber) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;

    }


    public static Connection getConnection() throws SQLException {
        Config.load();
        return DriverManager.getConnection(
            Config.getDbUrl(), 
            Config.getDbUser(), 
            Config.getDbPassword()
        );
    }

    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public void setCustomerPhoneNumber(String customerPhoneNumber) { this.customerPhoneNumber = customerPhoneNumber; }

    public int getCustomerID() { return customerID; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public String getCustomerPhoneNumber() { return customerPhoneNumber; }
 

public static void addCustomer(Connection conn, Scanner sc) {
    System.out.print("Enter customer ID: ");
    int id = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter customer name: ");
    String name = sc.nextLine();
    System.out.print("Enter customer email: ");
    String email = sc.nextLine();
    System.out.print("Enter customer phone: ");
    String phone = sc.nextLine();


    String query = "INSERT INTO customer (customer_id, customer_name, email, phone) VALUES (?, ?, ?, ?)";
    

    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, id); 
        stmt.setString(2, name); 
        stmt.setString(3, email);
        stmt.setString(4, phone);
        stmt.executeUpdate(); 
        System.out.println("Customer added successfully.");
    } catch (SQLException e) {
        System.err.println("Error adding customer: " + e.getMessage());
    }
}

    public static void editCustomer(Connection conn, Scanner sc) {
        System.out.print("Enter customer ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter new customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter new customer email: ");
        String email = sc.nextLine();
        System.out.print("Enter new customer phone: ");
        String phone = sc.nextLine();

        String query = "UPDATE customer SET customer_name = ?, email = ?, phone = ?, WHERE customer_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setInt(5, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("Customer ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error editing customer: " + e.getMessage());
        }
    }

    public static void deleteCustomer(Connection conn, Scanner sc) {
        System.out.print("Enter customer ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); 

        String query = "DELETE FROM customer WHERE customer_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
        }
    }

    public static void listCustomer(Connection conn, Scanner sc) {
        System.out.print("Enter customer ID to display: ");
        int id = sc.nextInt();
        sc.nextLine();

        String query = "SELECT * FROM customer WHERE customer_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Name: " + rs.getString("customer_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
            } else {
                System.out.println("Customer ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
        }
    }

    public static void listAllCustomers(Connection conn) {
        String query = "SELECT * FROM customer";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Customer List:");
            while (rs.next()) {
                System.out.println(rs.getInt("customer_id") + " | " +
                                   rs.getString("customer_name") + " | " +
                                   rs.getString("email") + " | " +
                                   rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
    }

    public static void backupCustomers(Connection conn) {
        String query = "SELECT * FROM customer";
        String backupFile = "customers_backup.csv";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile))) {
            writer.write("customer_id,customer_name,email,phone");
            writer.newLine();
            while (rs.next()) {
                writer.write(rs.getInt("customer_id") + "," +
                             rs.getString("customer_name") + "," +
                             rs.getString("email") + "," +
                             rs.getString("phone"));
                writer.newLine();
            }
            System.out.println("Backup completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error backing up customers: " + e.getMessage());
        }
    }

    public static void restoreCustomers(Connection conn) {
        String restoreFile = "customers_backup.csv";
        String query = "INSERT INTO customer (customer_id, customer_name, email, phone) VALUES (?, ?, ?, ?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(restoreFile));
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
                stmt.executeUpdate();
            }
            System.out.println("Restore completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error restoring customers: " + e.getMessage());
        }
    }
}