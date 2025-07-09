package com.example.hongnhung;
public class CartItem {
    private String imageUrl;
    private String name;
    private String desc;
    private int quantity;
    private String price;

    public CartItem(String imageUrl, String name, String desc, int quantity, String price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

