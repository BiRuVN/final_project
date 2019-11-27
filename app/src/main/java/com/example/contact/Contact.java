package com.example.contact;


import java.io.Serializable;

public class Contact implements Serializable{
    String name;
    String phone;
    int id;
    int fav = 0;

    public Contact(int id, String name, String phone, int fav)  {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.fav = fav;
    }
    public Contact(String name, String phone)  {
        this.name = name;
        this.phone = phone;
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

}
