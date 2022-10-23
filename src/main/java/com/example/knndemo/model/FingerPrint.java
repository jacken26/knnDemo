package com.example.knndemo.model;


import lombok.Data;

@Data
public class FingerPrint {
    private  Integer id;
    private double x;
    private double y;
    private double rssi1;
    private double rssi2;
    private double rssi3;
    private double rssi4;
    private double rssi5;
    private double rssi6;
    private double rssi7;
    private double rssi8;
    private String msg;
    private double distanct;
    private Integer k;

    private double weight;


    public FingerPrint() {
    }

    public FingerPrint(Integer id, double x, double y, double rssi1, double rssi2, double rssi3, double rssi4, double rssi5, double rssi6, double rssi7, double rssi8, String msg, double distanct, Integer k) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rssi1 = rssi1;
        this.rssi2 = rssi2;
        this.rssi3 = rssi3;
        this.rssi4 = rssi4;
        this.rssi5 = rssi5;
        this.rssi6 = rssi6;
        this.rssi7 = rssi7;
        this.rssi8 = rssi8;
        this.msg = msg;
        this.distanct = distanct;
        this.k = k;
    }
}
