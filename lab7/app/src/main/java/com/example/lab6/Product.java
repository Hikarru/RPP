package com.example.lab6;

public class Product {
    private String name;
    private String price;
    private int count;

    public Product(String name, String price, int count){
        this.name = name;
        this.price = price;
        this.count = count;
    }
    public void setCount(int count){
        this.count = count;
    }
    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public int getCount(){
        return count;
    }
    public void setName(String name){this.name = name;}
    public void setPrice(String price){this.price = price;}
}
