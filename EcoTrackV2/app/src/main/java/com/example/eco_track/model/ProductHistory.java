package com.example.eco_track.model;

import java.util.Date;

public class ProductHistory {
    private String name;
    private String nutriscore;
    private String ecoscore;
    private String carbonFootprint;
    private Date scanDate;

    public ProductHistory(String name, String nutriscore, String ecoscore, String carbonFootprint) {
        this.name = name;
        this.nutriscore = nutriscore;
        this.ecoscore = ecoscore;
        this.carbonFootprint = carbonFootprint;
        this.scanDate = new Date();
    }

    // Getters
    public String getName() { return name; }
    public String getNutriscore() { return nutriscore; }
    public String getEcoscore() { return ecoscore; }
    public String getCarbonFootprint() { return carbonFootprint; }
    public Date getScanDate() { return scanDate; }
} 