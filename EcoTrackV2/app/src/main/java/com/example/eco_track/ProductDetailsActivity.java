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

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView textViewName, textViewNutri, textViewEco, textViewOrigin, textViewCarbonFootprint;

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
                        (product.getOrigins() != null ? product.getOrigins() : ""));

                    // Calcul et affichage de l'empreinte carbone
                    double carbonFootprint = calculateCarbonFootprint(product);
                    textViewCarbonFootprint.setText(String.format("Empreinte carbone : %.2f kg CO₂e", carbonFootprint));
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

        // Si l'empreinte carbone est fournie directement par l'API
        if (product.getCarbonFootprint100g() != null) {
            double productWeight = product.getQuantityValue();
            return (productWeight / 100.0) * product.getCarbonFootprint100g();
        }

        // Valeurs par défaut selon le type de produit
        if (product.getProductName() != null) {
            String name = product.getProductName().toLowerCase();
            if (name.contains("eau") || name.contains("water")) {
                return 1.00; // Valeur par défaut pour l'eau
            }
        }

        return 1.00; // Valeur par défaut si aucune donnée n'est disponible
    }
}
