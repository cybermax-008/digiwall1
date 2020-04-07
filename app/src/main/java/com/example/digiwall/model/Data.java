package com.example.digiwall.model;

import java.util.StringTokenizer;

public class Data {

    private double amount;
    private String type;
    private String note;
    private String name;
    private String id;
    private String date;

    public Data(){

    }
    public Data(double amount, String type, String note, String name,String id, String date) {
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.name =name;
        this.id = id;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
