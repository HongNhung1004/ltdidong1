package com.example.hongnhung;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(CartItem item) {
        for (CartItem existing : cartItems) {
            if (existing.getName().equals(item.getName())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getTotalQuantity() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }

    public void clear() {
        cartItems.clear();
    }
}
