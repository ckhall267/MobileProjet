package com.example.eco_track;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private Long userId;
    private String userEmail;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des SharedPreferences
        sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userId = sharedPref.getLong("user_id", -1);
        userEmail = sharedPref.getString("user_email", null);

        // Initialisation de la Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        setupBottomNavigation();

        // Gestion du scan de code-barres au clic sur le carré "Scanner"
        FrameLayout scanSquare = findViewById(R.id.scanSquare);
        scanSquare.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scannez un code-barres");
            integrator.setCameraId(0);  // Utilise la caméra arrière
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });

        // Gestion du clic sur le carré "Carte"
        FrameLayout mapsSquare = findViewById(R.id.mapsSquare);
        mapsSquare.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        // Gestion du clic sur le carré "Historique"
        FrameLayout historySquare = findViewById(R.id.historySquare);
        historySquare.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        // Gestion du clic sur le carré "Statistiques"
        FrameLayout statsSquare = findViewById(R.id.statsSquare);
        statsSquare.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StasActivity.class);
            startActivity(intent);
        });
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                // Déjà sur la page d'accueil
                return true;
            } else if (itemId == R.id.navigation_profile) {
                // TODO: Lancer l'activité de profil
                Toast.makeText(this, "Profil", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.navigation_settings) {
                // TODO: Lancer l'activité des paramètres
                Toast.makeText(this, "Paramètres", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.navigation_logout) {
                // Gérer la déconnexion
                handleLogout();
                return true;
            }
            return false;
        });
    }

    private void handleLogout() {
        // Effacer les données de l'utilisateur
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

        // Rediriger vers la page de connexion
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Scan annulé
            } else {
                String barcode = result.getContents();

                // Va vers ProductDetailsActivity avec le code-barres
                Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                intent.putExtra("barcode", barcode);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}