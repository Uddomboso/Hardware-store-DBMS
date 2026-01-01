package com.mycompany.hardwarestoredbms;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Stock {

    private int stockID;
    private int productID;
    private int quantity;

   
    public Stock(int stockID, int productID, int quantity) {
        this.stockID = stockID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public static Connection getConnection() throws SQLException {
        Config.load();
        return DriverManager.getConnection(
            Config.getDbUrl(), 
            Config.getDbUser(), 
            Config.getDbPassword()
        );
    }

   
    public void setStockID(int stockID) { this.stockID = stockID; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setProductID(int productID) { this.productID = productID; }


    public int getStockID() { return stockID; }
    public int getQuantity() { return quantity; }
    public int getProductID() { return productID; }


  
    public static void addStock(Scanner sc) {
        System.out.print("Enter stock ID: ");
        int stockID = sc.nextInt();
        System.out.print("Enter product ID: ");
        int productID = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine(); 

        String query = "INSERT INTO stock (stock_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, stockID);
            stmt.setInt(2, productID);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
            System.out.println("Stock added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding stock: " + e.getMessage());
        }
    }

    public static void editStock(Scanner sc) {
        System.out.print("Enter stock ID to edit: ");
        int stockID = sc.nextInt();
        System.out.print("Enter new product ID: ");
        int productID = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter new quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        String query = "UPDATE stock SET product_id = ?, quantity = ? WHERE stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productID);

            stmt.setInt(3, quantity);
            stmt.setInt(4, stockID);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Stock updated successfully.");
            } else {
                System.out.println("Stock ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error editing stock: " + e.getMessage());
        }
    }


    public static void deleteStockById(Scanner sc) {
        System.out.print("Enter stock ID to delete: ");
        int stockID = sc.nextInt();
        String query = "DELETE FROM stock WHERE stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, stockID);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Stock deleted successfully.");
            } else {
                System.out.println("Stock ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting stock: " + e.getMessage());
        }
    }

    public static void listStock(Scanner sc) {
        System.out.print("Enter stock ID to list: ");
        int stockID = sc.nextInt();
        String query = "SELECT * FROM stock WHERE stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, stockID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Stock ID: " + rs.getInt("stock_id"));
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
            } else {
                System.out.println("Stock ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving stock: " + e.getMessage());
        }
    }


    public static void listAllStocks() {
        String query = "SELECT * FROM stock";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Stock List:");
            while (rs.next()) {
                System.out.println(rs.getInt("stock_id") + " | " +
                                   rs.getInt("product_id") + " | " +
                                   rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving stocks: " + e.getMessage());
        }
    }


    public static void deleteAllStocks() {
        String query = "DELETE FROM stock";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("All stocks deleted successfully.");
            } else {
                System.out.println("No stocks to delete.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting all stocks: " + e.getMessage());
        }
    }


    public static void backupStocks() {
        String query = "SELECT * FROM stock";
        String backupFile = "stocks_backup.csv";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile))) {

            writer.newLine();
            while (rs.next()) {
                writer.write(rs.getInt("stock_id") + "," +
                             rs.getInt("product_id") + "," +

                             rs.getInt("quantity"));
                writer.newLine();
            }
            System.out.println("Backup completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error backing up stocks: " + e.getMessage());
        }
    }

    public static void restoreStocks() {
        String restoreFile = "stocks_backup.csv";
        String query = "INSERT INTO stock (stock_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             BufferedReader reader = new BufferedReader(new FileReader(restoreFile));
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                stmt.setInt(1, Integer.parseInt(data[0]));
                stmt.setInt(2, Integer.parseInt(data[1]));
                stmt.setString(3, data[2]);
                stmt.setInt(4, Integer.parseInt(data[3]));
                stmt.executeUpdate();
            }
            System.out.println("Restore completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error restoring stocks: " + e.getMessage());
        }
    }
}
