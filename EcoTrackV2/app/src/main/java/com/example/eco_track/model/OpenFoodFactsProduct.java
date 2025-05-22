package com.example.eco_track.model;

public class OpenFoodFactsProduct {
    private String product_name;
    private String nutriscore_grade;
    private String ecoscore_grade;
    private String origins;
    private String quantity; // ex: "500 ml", "100 g"
    private Double carbon_footprint_100g; // Empreinte carbone pour 100g
    private Nutriments nutriments;

    public static class Nutriments {
        private double proteins_100g;
        private double carbohydrates_100g;
        private double fat_100g;
        
        public double getProteins100g() { return proteins_100g; }
        public double getCarbohydrates100g() { return carbohydrates_100g; }
        public double getFat100g() { return fat_100g; }
    }

    public String getProductName() { return product_name; }
    public String getNutriscoreGrade() { return nutriscore_grade; }
    public String getEcoscoreGrade() { return ecoscore_grade; }
    public String getOrigins() { return origins; }
    public String getQuantity() { return quantity; }
    public Double getCarbonFootprint100g() { return carbon_footprint_100g; }
    public Nutriments getNutriments() { return nutriments; }

    // Méthode utilitaire pour extraire la valeur numérique de la quantité
    public double getQuantityValue() {
        if (quantity == null) return 100.0; // valeur par défaut
        try {
            // Extrait les chiffres de la chaîne (ex: "500 ml" -> 500)
            return Double.parseDouble(quantity.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            return 100.0; // valeur par défaut si erreur
        }
    }
} 