package com.mycompany.hardwarestoredbms;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Product {

    private int productID;
    private int stockID;
    private String productName;
    private float productPrice;
    private String description;
    private int stockQuantity;


    public Product(int productID, int stockID, String productName, String description, float productPrice, int stockQuantity) {
        this.productID = productID;
        this.stockID = stockID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

 
    public static Connection getConnection() throws SQLException {
        Config.load();
        return DriverManager.getConnection(
            Config.getDbUrl(), 
            Config.getDbUser(), 
            Config.getDbPassword()
        );
    }

 
    public static void addProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter product ID: ");
            int productID = sc.nextInt();
            System.out.print("Enter stock ID: ");
            int stockID = sc.nextInt();
            sc.nextLine(); 
            System.out.print("Enter product name: ");
            String productName = sc.nextLine();
            System.out.print("Enter product description: ");
            String description = sc.nextLine();
            System.out.print("Enter product price: ");
            float productPrice = sc.nextFloat();
            System.out.print("Enter stock quantity: ");
            int stockQuantity = sc.nextInt();

            String query = "INSERT INTO product (product_id, stock_id, product_name, description, price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, productID);
                stmt.setInt(2, stockID);
                stmt.setString(3, productName);
                stmt.setString(4, description);
                stmt.setFloat(5, productPrice);
                stmt.setInt(6, stockQuantity);
                stmt.executeUpdate();
                System.out.println("\n\n Product added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }


    public static void editProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter product ID to edit: ");
            int productID = sc.nextInt();
            System.out.print("Enter new stock ID: ");
            int stockID = sc.nextInt();
            sc.nextLine(); 
            System.out.print("Enter new product name: ");
            String productName = sc.nextLine();
            System.out.print("Enter new product description: ");
            String description = sc.nextLine();
            System.out.print("Enter new product price: ");
            float productPrice = sc.nextFloat();
            System.out.print("Enter new stock quantity: ");
            int stockQuantity = sc.nextInt();

            String query = "UPDATE product SET stock_id = ?, product_name = ?, description = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, stockID);
                stmt.setString(2, productName);
                stmt.setString(3, description);
                stmt.setFloat(4, productPrice);
                stmt.setInt(5, stockQuantity);
                stmt.setInt(6, productID);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("\n\n Product updated successfully.");
                } else {
                    System.out.println("Product ID not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error editing product: " + e.getMessage());
        }
    }

    public static void deleteProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter product ID to delete: ");
            int productID = sc.nextInt();

            String query = "DELETE FROM product WHERE product_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, productID);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Product deleted successfully.");
                } else {
                    System.out.println("Product ID not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    public static void listProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter product ID to display: ");
            int productID = sc.nextInt();

            String query = "SELECT * FROM product WHERE product_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, productID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Product ID: " + rs.getInt("product_id"));
                    System.out.println("Stock ID: " + rs.getInt("stock_id"));
                    System.out.println("Name: " + rs.getString("product_name"));
                    System.out.println("Description: " + rs.getString("description"));
                    System.out.println("Price: " + rs.getFloat("price"));
                    System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
                } else {
                    System.out.println("Product ID not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving product: " + e.getMessage());
        }
    }

    public static void listAllProducts(Connection conn) {
    try {
        String query = "SELECT * FROM product";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            System.out.printf("%-10s %-15s %-25s %-70s %-10s %-15s%n", 
                "Product ID", "Stock ID", "Product Name", "Description", "Price", "Stock Quantity");
            System.out.println("======================================================================================================================================================================");

            while (rs.next()) {
                System.out.printf("%-10d %-15d %-25s %-50s %-10.2f %-15d%n",
                    rs.getInt("product_id"),
                    rs.getInt("stock_id"),
                    rs.getString("product_name"),
                    rs.getString("description"),
                    rs.getFloat("price"),
                    rs.getInt("stock_quantity"));
            }
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving products: " + e.getMessage());
    }
}

    public static void backupProducts(Connection conn) {
        String query = "SELECT * FROM product";
        String backupFile = "products_backup.csv";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile))) {
            writer.write("product_id,stock_id,product_name,description,price,stock_quantity");
            writer.newLine();
            while (rs.next()) {
                writer.write(rs.getInt("product_id") + "," +
                        rs.getInt("stock_id") + "," +
                        rs.getString("product_name") + "," +
                        rs.getString("description") + "," +
                        rs.getFloat("price") + "," +
                        rs.getInt("stock_quantity"));
                writer.newLine();
            }
            System.out.println("Backup completed successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error backing up products: " + e.getMessage());
        }
    }

    public static void restoreProducts(Connection conn) {
    String restoreFile = "products_backup.csv";
    String query = "INSERT INTO product (product_id, stock_id, product_name, description, price, stock_quantity) " +
                   "VALUES (?, ?, ?, ?, ?, ?) " +
                   "ON DUPLICATE KEY UPDATE " +
                   "stock_id = VALUES(stock_id), " +
                   "product_name = VALUES(product_name), " +
                   "description = VALUES(description), " +
                   "price = VALUES(price), " +
                   "stock_quantity = VALUES(stock_quantity)";
    try (BufferedReader reader = new BufferedReader(new FileReader(restoreFile));
         PreparedStatement stmt = conn.prepareStatement(query)) {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {

            String[] data = parseCSVLine(line);
            if (data.length != 6) {
                System.err.println("Skipping malformed line: " + line);
                continue;
            }

            stmt.setInt(1, Integer.parseInt(data[0].trim()));
            stmt.setInt(2, Integer.parseInt(data[1].trim())); 
            stmt.setString(3, data[2].trim());              
            stmt.setString(4, data[3].trim());               
            stmt.setFloat(5, Float.parseFloat(data[4].trim())); 
            stmt.setInt(6, Integer.parseInt(data[5].trim())); 
            stmt.executeUpdate();
        }
        System.out.println("Restore completed successfully!");
    } catch (IOException | SQLException e) {
        System.err.println("Error restoring products: " + e.getMessage());
    }
}


private static String[] parseCSVLine(String line) {

    String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    return line.split(regex, -1);
}


}
