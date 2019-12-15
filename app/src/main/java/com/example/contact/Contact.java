package com.example.contact;


import java.io.Serializable;

public class Contact implements Serializable{
    private String name = "";
    private String phone = "";
    private int id;
    private int fav = 0;
    private int image = R.drawable.img1;
    private String email = "";

    public Contact(int id, String name, String phone, int fav, int image, String email)  {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.fav = fav;
        this.image = image;
        this.email = email;
    }
    public Contact(String name, String phone, int img, String email)  {
        this.name = name;
        this.phone = phone;
        this.image = img;
        this.email = email;
    }
    public Contact(){

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getFav(){
        return fav;
    }
    public void setFav(int fav){
        this.fav = fav;
    }
    public int getImage() { return image; }
    public void setImage(int image) { this.image = image; }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
