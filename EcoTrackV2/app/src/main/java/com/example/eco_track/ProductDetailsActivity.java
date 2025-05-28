package com.example.eco_track;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eco_track.api.OpenFoodFactsService;
import com.example.eco_track.api.RetrofitClient;
import com.example.eco_track.model.OpenFoodFactsProduct;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView textViewName, textViewNutri, textViewEco, textViewOrigin, textViewCarbonFootprint;
    
    // Coefficients d'impact carbone par type d'ingrédient (en kg CO2e par kg)
    private static final Map<String, Double> INGREDIENT_IMPACTS = new HashMap<String, Double>() {{
        put("boeuf", 60.0);
        put("porc", 7.0);
        put("poulet", 6.0);
        put("fromage", 21.0);
        put("oeufs", 4.5);
        put("pommes de terre", 0.5);
        put("légumes", 0.5);
        put("fruits", 0.5);
        put("lait", 1.39);
        put("poisson", 5.0);
        put("riz", 2.7);
        put("pain", 0.9);
        put("pâtes", 0.9);
        put("eau", 0.15);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Initialisation des TextViews
        textViewName = findViewById(R.id.textViewProductName);
        textViewNutri = findViewById(R.id.textViewNutriscore);
        textViewEco = findViewById(R.id.textViewEcoscore);
        textViewOrigin = findViewById(R.id.textViewOrigin);
        textViewCarbonFootprint = findViewById(R.id.textViewCarbonFootprint);

        // Récupération du code-barres depuis l'Intent
        String barcode = getIntent().getStringExtra("barcode");

        if (barcode != null && !barcode.isEmpty()) {
            fetchProductDetails(barcode);
        } else {
            textViewName.setText("Code-barres invalide");
        }
    }

    private void fetchProductDetails(String barcode) {
        OpenFoodFactsService api = RetrofitClient.getOpenFoodFactsService();
        Call<OpenFoodFactsService.OpenFoodFactsResponse> call = api.getProduct(barcode);

        call.enqueue(new Callback<OpenFoodFactsService.OpenFoodFactsResponse>() {
            @Override
            public void onResponse(Call<OpenFoodFactsService.OpenFoodFactsResponse> call,
                                 Response<OpenFoodFactsService.OpenFoodFactsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().product != null) {
                    OpenFoodFactsProduct product = response.body().product;

                    // Affichage des informations du produit
                    textViewName.setText(product.getProductName());
                    textViewNutri.setText("Nutriscore : " +
                        (product.getNutriscoreGrade() != null ? product.getNutriscoreGrade().toUpperCase() : "Non disponible"));
                    textViewEco.setText("Ecoscore : " +
                        (product.getEcoscoreGrade() != null ? product.getEcoscoreGrade().toUpperCase() : "NOT-APPLICABLE"));
                    textViewOrigin.setText("Origine : " +
                        (product.getOrigins() != null ? product.getOrigins() : "Non spécifiée"));

                    // Calcul et affichage de l'empreinte carbone
                    double carbonFootprint = calculateCarbonFootprint(product);
                    String details = getFootprintDetails(product, carbonFootprint);
                    textViewCarbonFootprint.setText(details);
                } else {
                    textViewName.setText("Produit non trouvé");
                }
            }

            @Override
            public void onFailure(Call<OpenFoodFactsService.OpenFoodFactsResponse> call, Throwable t) {
                textViewName.setText("Erreur de connexion");
            }
        });
    }

    private double calculateCarbonFootprint(OpenFoodFactsProduct product) {
        if (product == null) return 0.0;

        // 1. Utiliser l'empreinte carbone directe si disponible
        if (product.getCarbonFootprint100g() != null) {
            double productWeight = product.getQuantityValue();
            return (productWeight / 100.0) * product.getCarbonFootprint100g();
        }

        // 2. Calculer basé sur les ingrédients et caractéristiques
        double baseImpact = calculateBaseImpact(product);
        double transportImpact = calculateTransportImpact(product);
        double packagingImpact = calculatePackagingImpact(product);

        return baseImpact + transportImpact + packagingImpact;
    }

    private double calculateBaseImpact(OpenFoodFactsProduct product) {
        String name = product.getProductName() != null ? product.getProductName().toLowerCase(Locale.getDefault()) : "";
        double quantity = product.getQuantityValue() / 1000.0; // Conversion en kg
        
        // Chercher les correspondances dans les coefficients d'impact
        for (Map.Entry<String, Double> entry : INGREDIENT_IMPACTS.entrySet()) {
            if (name.contains(entry.getKey())) {
                return entry.getValue() * quantity;
            }
        }
        
        // Valeur par défaut si aucune correspondance
        return 0.6 * quantity;
    }

    private double calculateTransportImpact(OpenFoodFactsProduct product) {
        String origin = product.getOrigins() != null ? product.getOrigins().toLowerCase(Locale.getDefault()) : "";
        double baseImpact = calculateBaseImpact(product);
        
        // Facteurs d'impact du transport selon l'origine
        if (origin.contains("france")) {
            return baseImpact * 0.05; // +5% pour produits locaux
        } else if (origin.contains("europe")) {
            return baseImpact * 0.10; // +10% pour produits européens
        } else {
            return baseImpact * 0.20; // +20% pour produits importés lointains
        }
    }

    private double calculatePackagingImpact(OpenFoodFactsProduct product) {
        double baseImpact = calculateBaseImpact(product);
        
        // Par défaut, on considère un emballage léger (+10%)
        return baseImpact * 0.10;
    }

    private String getFootprintDetails(OpenFoodFactsProduct product, double totalFootprint) {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Empreinte carbone totale : %.2f kg CO₂e\n", totalFootprint));
        
        // Ajouter les détails du calcul
        double baseImpact = calculateBaseImpact(product);
        double transportImpact = calculateTransportImpact(product);
        double packagingImpact = calculatePackagingImpact(product);
        
        details.append(String.format("- Impact produit : %.2f kg CO₂e\n", baseImpact));
        details.append(String.format("- Impact transport : %.2f kg CO₂e\n", transportImpact));
        details.append(String.format("- Impact emballage : %.2f kg CO₂e", packagingImpact));
        
        return details.toString();
    }
}
