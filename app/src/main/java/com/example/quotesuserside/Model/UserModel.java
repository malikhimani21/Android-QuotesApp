package com.example.quotesuserside.Model;

public class UserModel {
    String id, name, email, contact, pass;


    public UserModel() {
    }

    public UserModel(String id, String name, String email, String contact, String pass) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
