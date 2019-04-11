package com.example.loginfirebase;

public class Model {

    String title, image;

    //constructor
    public Model() {

    }

    public Model(String title, String image){
        this.title = title;
        this.image = image;
    }

    //getter and setters press Alt+Insert

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
