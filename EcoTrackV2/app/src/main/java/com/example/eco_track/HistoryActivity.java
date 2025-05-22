package com.example.eco_track;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_track.adapter.ProductHistoryAdapter;
import com.example.eco_track.model.ProductHistory;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductHistoryAdapter adapter;
    private TextInputEditText searchEditText;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerViewHistory);
        searchEditText = findViewById(R.id.searchEditText);
        emptyView = findViewById(R.id.emptyView);

        // Configuration du RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductHistoryAdapter();
        recyclerView.setAdapter(adapter);

        // Gestion de la recherche
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                adapter.filter(s.toString());
                updateEmptyView();
            }
        });

        // Chargement des données
        loadTestData();
    }

    private void loadTestData() {
        List<ProductHistory> products = new ArrayList<>();
        products.add(new ProductHistory("Sidi Ali", "A", "NOT-APPLICABLE", "1,00 kg CO₂e"));
        products.add(new ProductHistory("Aquafina 50cl", "B", "NOT-APPLICABLE", "0,75 kg CO₂e"));
        adapter.setProducts(products);
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}