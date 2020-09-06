package com.example.myapplication.model;

public class ArticleModel {

    private String name ,price,description,image_path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public ArticleModel(String name, String price, String description, String image_path) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image_path = image_path;
    }

    public ArticleModel() {
    }
}
