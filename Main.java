package com.productmanagement;

import com.productmanagement.model.Product;
import com.productmanagement.service.ProductService;
import com.productmanagement.ui.ProductManagementUI;

import java.util.Scanner;

/**
 * Main class for the Product Management System
 * This class serves as the entry point for the application
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("===== Product Management System =====");
        
        // Initialize the product service
        ProductService productService = new ProductService();
        
        // Load sample products for testing
        loadSampleProducts(productService);
        
        // Create UI manager and start the application
        ProductManagementUI ui = new ProductManagementUI(productService);
        ui.start();
    }
    
    /**
     * Loads sample products into the system for demonstration purposes
     * @param productService The product service to add products to
     */
    private static void loadSampleProducts(ProductService productService) {
        productService.addProduct(new Product(1, "Laptop", "Electronics", 999.99, 10));
        productService.addProduct(new Product(2, "Smartphone", "Electronics", 699.99, 15));
        productService.addProduct(new Product(3, "Desk Chair", "Furniture", 149.99, 5));
        productService.addProduct(new Product(4, "Coffee Maker", "Appliances", 79.99, 8));
        productService.addProduct(new Product(5, "Headphones", "Electronics", 129.99, 20));
    }
} 