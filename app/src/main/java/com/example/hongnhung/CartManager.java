package com.example.hongnhung;

import java.lang.reflect.Array;
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
            if (existing.name.equals(item.name)) {
                existing.quantity += item.quantity;
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
            total += item.quantity;
        }
        return total;
    }

    public void clear() {
        cartItems.clear();
    }
}

