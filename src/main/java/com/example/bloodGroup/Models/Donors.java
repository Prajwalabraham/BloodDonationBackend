package com.example.bloodGroup.Models;

import java.util.Date;

public class Donors {
    private Long id;
    private String name;
    private String bloodGroup;
    private int unitsOfBlood;
    private String phone;
    private String gender;
    private int age;
    private String address;
    private Date donatingDate;
    private String username;

    public Donors() {
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setUnitsOfBlood(int unitsOfBlood) {
        this.unitsOfBlood = unitsOfBlood;
    }

    public int getUnitsOfBlood() {
        return unitsOfBlood;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDonatingDate() {
        this.donatingDate = new Date();
    }

    public Date getDonatingDate() {
        return donatingDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
