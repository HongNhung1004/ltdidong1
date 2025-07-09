package com.example.hongnhung;

public class products {
    private int id;
    private String title;
    private double price;
    private String image;

    public products(int id, String title, double price, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getImage() { return image; }

    @Override
    public String toString() {
        return "SP: " + title + ", Giá: " + price;
    }
}

