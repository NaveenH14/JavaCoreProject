package com.productmanagement.service;

import com.productmanagement.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class that handles product management operations
 */
public class ProductService {
    private Map<Integer, Product> products;
    private int nextId;

    /**
     * Constructor that initializes the product repository
     */
    public ProductService() {
        products = new HashMap<>();
        nextId = 1;
    }

    /**
     * Add a new product to the system
     * 
     * @param product The product to add
     * @return The added product
     */
    public Product addProduct(Product product) {
        if (product.getId() == 0) {
            product.setId(nextId++);
        } else {
            nextId = Math.max(nextId, product.getId() + 1);
        }
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Update an existing product
     * 
     * @param product The product with updated details
     * @return The updated product, or null if not found
     */
    public Product updateProduct(Product product) {
        if (products.containsKey(product.getId())) {
            products.put(product.getId(), product);
            return product;
        }
        return null;
    }

    /**
     * Delete a product from the system
     * 
     * @param id The ID of the product to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteProduct(int id) {
        if (products.containsKey(id)) {
            products.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Get a product by its ID
     * 
     * @param id The ID of the product to find
     * @return The product if found, or null if not found
     */
    public Product getProductById(int id) {
        return products.get(id);
    }

    /**
     * Get all products in the system
     * 
     * @return A list of all products
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    /**
     * Search for products by name
     * 
     * @param keyword The keyword to search for in product names
     * @return A list of products matching the search
     */
    public List<Product> searchProducts(String keyword) {
        List<Product> result = new ArrayList<>();
        String lowercaseKeyword = keyword.toLowerCase();
        
        for (Product product : products.values()) {
            if (product.getName().toLowerCase().contains(lowercaseKeyword) || 
                product.getCategory().toLowerCase().contains(lowercaseKeyword)) {
                result.add(product);
            }
        }
        
        return result;
    }

    /**
     * Update the stock of a product
     * 
     * @param id The ID of the product
     * @param quantity The quantity to add (positive) or remove (negative)
     * @return The updated product, or null if not found
     */
    public Product updateStock(int id, int quantity) {
        Product product = products.get(id);
        
        if (product != null) {
            int newStock = product.getStock() + quantity;
            if (newStock < 0) {
                return null; // Can't have negative stock
            }
            
            product.setStock(newStock);
            return product;
        }
        
        return null;
    }
} 