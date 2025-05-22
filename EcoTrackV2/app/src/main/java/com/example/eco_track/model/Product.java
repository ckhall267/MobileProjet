package com.example.eco_track.model;

public class Product {
    private String codeBarre;
    private String nom;
    private String nutriscore;
    private String ecoscore;
    private String origine;
    private String carbonFootprint; // Empreinte carbone

    public Product(String codeBarre, String nom, String nutriscore, String ecoscore, String origine, String carbonFootprint) {
        this.codeBarre = codeBarre;
        this.nom = nom;
        this.nutriscore = nutriscore;
        this.ecoscore = ecoscore;
        this.origine = origine;
        this.carbonFootprint = carbonFootprint;
    }

    // Getters
    public String getCodeBarre() { return codeBarre; }
    public String getNom() { return nom; }
    public String getNutriscore() { return nutriscore; }
    public String getEcoscore() { return ecoscore; }
    public String getOrigine() { return origine; }
    public String getCarbonFootprint() { return carbonFootprint; }
}
