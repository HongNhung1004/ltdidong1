package com.example.hongnhung;

public class CartItem {
    public int imageRes;
    public String name;
    public String description;
    public int quantity;
    public int price;

    public CartItem(int imageRes, String name, String description, int quantity, int price) {
        this.imageRes = imageRes;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
