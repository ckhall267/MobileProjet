package com.example.Scanner.Model;

import lombok.Data;

@Data
public class OpenFoodFactsResponse {
    private ProductData product;

    @Data
    public static class ProductData {
        private String product_name;
        private String nutrition_grade_fr;
        private String ecoscore_grade;
        private String origins;

        public String getProductName() {
            return product_name;
        }

        public String getNutritionGradeFr() {
            return nutrition_grade_fr;
        }

        public String getEcoscoreGrade() {
            return ecoscore_grade;
        }

        public String getOrigins() {
            return origins;
        }
    }
}
