package com.example.jeevan.inonetask.model;

public class ContactsModel {
    public ContactsModel(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    String phoneNumber, name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
