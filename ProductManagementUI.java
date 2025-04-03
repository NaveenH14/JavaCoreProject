package com.productmanagement.ui;

import com.productmanagement.model.Product;
import com.productmanagement.service.ProductService;

import java.util.List;
import java.util.Scanner;

/**
 * UI class that handles user interactions for the product management system
 */
public class ProductManagementUI {
    private ProductService productService;
    private Scanner scanner;
    private boolean running;

    /**
     * Constructor that initializes the UI with a product service
     * 
     * @param productService The product service to use
     */
    public ProductManagementUI(ProductService productService) {
        this.productService = productService;
        this.scanner = new Scanner(System.in);
        this.running = false;
    }

    /**
     * Start the UI and enter the main menu loop
     */
    public void start() {
        running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    addProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    updateStock();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Display the main menu options
     */
    private void displayMainMenu() {
        System.out.println("\n===== Product Management System =====");
        System.out.println("1. Display All Products");
        System.out.println("2. Search Products");
        System.out.println("3. Add New Product");
        System.out.println("4. Update Product");
        System.out.println("5. Delete Product");
        System.out.println("6. Update Stock");
        System.out.println("0. Exit");
        System.out.println("=====================================");
    }

    /**
     * Display all products in the system
     */
    private void displayAllProducts() {
        List<Product> products = productService.getAllProducts();
        
        if (products.isEmpty()) {
            System.out.println("No products found in the system.");
            return;
        }
        
        System.out.println("\n===== All Products =====");
        displayProducts(products);
    }

    /**
     * Search for products by keyword
     */
    private void searchProducts() {
        String keyword = getStringInput("Enter search keyword: ");
        List<Product> results = productService.searchProducts(keyword);
        
        if (results.isEmpty()) {
            System.out.println("No products found matching the keyword: " + keyword);
            return;
        }
        
        System.out.println("\n===== Search Results =====");
        displayProducts(results);
    }

    /**
     * Add a new product to the system
     */
    private void addProduct() {
        System.out.println("\n===== Add New Product =====");
        
        String name = getStringInput("Enter product name: ");
        String category = getStringInput("Enter product category: ");
        double price = getDoubleInput("Enter product price: ");
        int stock = getIntInput("Enter initial stock: ");
        
        Product product = new Product(0, name, category, price, stock);
        productService.addProduct(product);
        
        System.out.println("Product added successfully with ID: " + product.getId());
    }

    /**
     * Update an existing product
     */
    private void updateProduct() {
        System.out.println("\n===== Update Product =====");
        
        int id = getIntInput("Enter product ID to update: ");
        Product product = productService.getProductById(id);
        
        if (product == null) {
            System.out.println("Product not found with ID: " + id);
            return;
        }
        
        System.out.println("Current details: " + product);
        
        String name = getStringInput("Enter new name (or press enter to keep current): ");
        if (!name.isEmpty()) {
            product.setName(name);
        }
        
        String category = getStringInput("Enter new category (or press enter to keep current): ");
        if (!category.isEmpty()) {
            product.setCategory(category);
        }
        
        String priceStr = getStringInput("Enter new price (or press enter to keep current): ");
        if (!priceStr.isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                product.setPrice(price);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Price not updated.");
            }
        }
        
        Product updated = productService.updateProduct(product);
        if (updated != null) {
            System.out.println("Product updated successfully: " + updated);
        } else {
            System.out.println("Failed to update product.");
        }
    }

    /**
     * Delete a product from the system
     */
    private void deleteProduct() {
        System.out.println("\n===== Delete Product =====");
        
        int id = getIntInput("Enter product ID to delete: ");
        Product product = productService.getProductById(id);
        
        if (product == null) {
            System.out.println("Product not found with ID: " + id);
            return;
        }
        
        System.out.println("Product to delete: " + product);
        String confirm = getStringInput("Are you sure you want to delete this product? (y/n): ");
        
        if (confirm.equalsIgnoreCase("y")) {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete product.");
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    /**
     * Update the stock of a product
     */
    private void updateStock() {
        System.out.println("\n===== Update Stock =====");
        
        int id = getIntInput("Enter product ID: ");
        Product product = productService.getProductById(id);
        
        if (product == null) {
            System.out.println("Product not found with ID: " + id);
            return;
        }
        
        System.out.println("Current product: " + product);
        System.out.println("Current stock: " + product.getStock());
        
        int quantity = getIntInput("Enter quantity to add (positive) or remove (negative): ");
        
        Product updated = productService.updateStock(id, quantity);
        if (updated != null) {
            System.out.println("Stock updated successfully. New stock: " + updated.getStock());
        } else {
            System.out.println("Failed to update stock. Check if requested quantity exceeds available stock.");
        }
    }

    /**
     * Exit the application
     */
    private void exit() {
        System.out.println("Exiting Product Management System. Goodbye!");
        running = false;
        scanner.close();
    }

    /**
     * Display a list of products in a formatted manner
     * 
     * @param products The list of products to display
     */
    private void displayProducts(List<Product> products) {
        System.out.printf("%-5s %-20s %-15s %-10s %-10s%n", "ID", "Name", "Category", "Price", "Stock");
        System.out.println("-------------------------------------------------------------------");
        
        for (Product product : products) {
            System.out.printf("%-5d %-20s %-15s $%-9.2f %-10d%n",
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getStock());
        }
    }

    /**
     * Get string input from the user
     * 
     * @param prompt The prompt to display
     * @return The user's input
     */
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Get integer input from the user
     * 
     * @param prompt The prompt to display
     * @return The user's input as an integer
     */
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Get double input from the user
     * 
     * @param prompt The prompt to display
     * @return The user's input as a double
     */
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
} 