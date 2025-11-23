package com.example.finalproject.models;

public class Developer {
    private String name;
    private String registrationNumber;
    private String role;
    private int imageResource; // 0 means no image

    public Developer(String name, String registrationNumber, String role) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.role = role;
        this.imageResource = 0;
    }

    public Developer(String name, String registrationNumber, String role, int imageResource) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.role = role;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}


