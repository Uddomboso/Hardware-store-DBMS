/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.hardwarestoredbms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HardwareStoreDBMS {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        Config.load();
        String url = Config.getDbUrl();
        String user = Config.getDbUser();
        String password = Config.getDbPassword();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the Hardware Store database!");

            while (running) {
                System.out.println("\n====== Hardware Store Management System ======");
                System.out.println("1. Product Management");
                System.out.println("2. Customer Management");
                System.out.println("3. Order Management");
                System.out.println("4. Supplier Management");
                System.out.println("5.Stock Management");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    sc.nextLine(); 

                    switch (choice) {
                        case 1 -> productMenu(conn, sc);
                        case 2 -> customerMenu(conn, sc);
                        case 3 -> orderMenu(conn, sc);
                        case 4 -> supplierMenu(sc);
                        case 5 ->  stockMenu(sc);
                        case 7 ->{
                            System.out.println("Exiting the system...");
                            running = false;
                        }
                        default -> System.out.println("Invalid choice! Please select a number between 1 and 6.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next(); 
                }
            }

        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
        } finally {
            sc.close(); 
        }
    }

    public static void productMenu(Connection conn, Scanner sc) throws IOException {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n======= Product Menu =======");
            System.out.println("1. Add a new product");
            System.out.println("2. Update product information");
            System.out.println("3. Delete a product");
            System.out.println("4. Display a product");
            System.out.println("5. Display all products");
            System.out.println("6. Backup product records");
            System.out.println("7. Restore product records");
            System.out.println("8. Go back to main menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> Product.addProduct(conn, sc); 
                case 2 -> Product.editProduct(conn, sc);
                case 3 -> Product.deleteProduct(conn, sc);
                case 4 -> Product.listProduct(conn, sc); 
                case 5 -> Product.listAllProducts(conn);
                case 6 -> Product.backupProducts(conn);
                case 7 -> Product.restoreProducts(conn); 
                case 8 -> backToMain = true;
                default -> System.out.println("Invalid choice! Please select a number between 1 and 7.");
            }
        }
    }


 public static void customerMenu(Connection conn, Scanner sc) throws IOException {
    boolean backToMain = false;

    while (!backToMain) {
        System.out.println("\n======= Customer Menu =======");
        System.out.println("1. Add a new customer");
        System.out.println("2. Update customer information");
        System.out.println("3. Delete a customer");
        System.out.println("4. Display customer by ID");
        System.out.println("5. Display all customers");
        System.out.println("7. Backup customer records");
        System.out.println("8. Restore customer records");
        System.out.println("9. Go back to main menu");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();  

        switch (choice) {
            case 1 -> Customer.addCustomer(conn, sc);  
            case 2 -> Customer.editCustomer(conn, sc);  
            case 3 -> Customer.deleteCustomer(conn, sc);  
            case 4 -> Customer.listCustomer(conn, sc);  
            case 5 -> Customer.listAllCustomers(conn);  
            case 7 -> Customer.backupCustomers(conn); 
            case 8 -> Customer.restoreCustomers(conn); 
            case 9 -> backToMain = true;  
            default -> System.out.println("Invalid choice! Please select a number between 1 and 9.");
        }
    }
}


    public static void orderMenu(Connection conn, Scanner sc) throws IOException {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n======= Order Menu =======");
            System.out.println("1. Add a new order");
            System.out.println("2. Update order information");
            System.out.println("3. Delete an order");
            System.out.println("4. Display an order");
            System.out.println("5. Display all orders");
            System.out.println("6. Backup order records");
            System.out.println("7. Restore order records");
            System.out.println("8. Go back to main menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> Order.addOrder(sc); 
                case 2 -> Order.editOrder(sc); 
                case 3 -> Order.deleteOrder(sc); 
                case 4 -> Order.listOrder(sc);
                case 5 -> Order.listAllOrders();
                case 6 -> Order.backupOrders(); 
                case 7 -> Order.restoreOrders(); 
                case 8 -> backToMain = true;
                default -> System.out.println("Invalid choice! Please select a number between 1 and 8.");
            }
        }
    }

    
    private static void stockMenu(Scanner sc) {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n======= Stock Menu =======");
            System.out.println("1. Add a new stock");
            System.out.println("2. Update stock information");
            System.out.println("3. Delete a stock");
            System.out.println("4. Display a stock");
            System.out.println("5. Display all stocks");
            System.out.println("6. Delete all stocks");
            System.out.println("7. Backup stock records");
            System.out.println("8. Restore stock records");
            System.out.println("9. Go back to main menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> Stock.addStock(sc); 
                case 2 -> Stock.editStock(sc); 
                case 3 -> Stock.deleteStockById(sc); 
                case 4 -> Stock.listStock(sc); 
                case 5 -> Stock.listAllStocks();
                case 6 -> Stock.deleteAllStocks();
                case 7 -> Stock.backupStocks(); 
                case 8 -> Stock.restoreStocks(); 
                case 9 -> backToMain = true;
                default -> System.out.println("Invalid choice! Please select a number between 1 and 9.");
            }
        }
    }

    

    private static void supplierMenu(Scanner sc) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Supplier Management Menu ---");
            System.out.println("1. Add Supplier");
            System.out.println("2. Edit Supplier");
            System.out.println("3. Delete Supplier");
            System.out.println("4. Display a Supplier");
            System.out.println("5. Display All Suppliers");
            System.out.println("6. Delete All Suppliers");
            System.out.println("7. Backup Suppliers");
            System.out.println("8. Restore Suppliers");
            System.out.println("9. Exit");
            System.out.print("Please choose an option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> Supplier.addSupplier(sc);
                case 2 -> Supplier.editSupplier(sc);
                case 3 -> Supplier.deleteSupplier(sc);
                case 4 -> Supplier.listSupplier(sc);
                case 5 -> Supplier.listAllSuppliers();
                case 6 -> Supplier.deleteAllSuppliers();
                case 7 -> Supplier.backupSuppliers();
                case 8 -> Supplier.restoreSuppliers();
                case 9 -> {
                    exit = true;
                    System.out.println("Exiting Supplier Management Menu.");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }


}
