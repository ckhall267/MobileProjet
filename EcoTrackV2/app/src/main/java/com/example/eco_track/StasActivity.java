package com.example.eco_track;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StasActivity extends AppCompatActivity {
    private TextView totalProductsText;
    private TextView averageCarbonText;
    private PieChart nutriscoreChart;
    private LineChart carbonChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stas);

        // Initialisation des vues
        totalProductsText = findViewById(R.id.totalProductsText);
        averageCarbonText = findViewById(R.id.averageCarbonText);
        nutriscoreChart = findViewById(R.id.nutriscoreChart);
        carbonChart = findViewById(R.id.carbonChart);

        // Configuration des graphiques
        setupNutriscoreChart();
        setupCarbonChart();

        // Chargement des données de test
        loadTestData();
    }

    private void setupNutriscoreChart() {
        nutriscoreChart.getDescription().setEnabled(false);
        nutriscoreChart.setHoleRadius(40f);
        nutriscoreChart.setTransparentCircleRadius(45f);
        nutriscoreChart.setDrawEntryLabels(true);
        nutriscoreChart.setEntryLabelTextSize(12f);
        nutriscoreChart.setEntryLabelColor(Color.BLACK);
        nutriscoreChart.getLegend().setEnabled(true);
    }

    private void setupCarbonChart() {
        carbonChart.getDescription().setEnabled(false);
        carbonChart.setTouchEnabled(true);
        carbonChart.setDragEnabled(true);
        carbonChart.setScaleEnabled(true);
        carbonChart.setPinchZoom(true);
        carbonChart.setDrawGridBackground(false);

        XAxis xAxis = carbonChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
    }

    private void loadTestData() {
        // Données de test pour les statistiques générales
        int totalProducts = 10;
        double averageCarbon = 0.85;
        totalProductsText.setText("Produits scannés : " + totalProducts);
        averageCarbonText.setText(String.format("Moyenne empreinte carbone : %.2f kg CO₂e", averageCarbon));

        // Données de test pour le graphique Nutriscore
        List<PieEntry> nutriscoreEntries = new ArrayList<>();
        nutriscoreEntries.add(new PieEntry(4, "A"));
        nutriscoreEntries.add(new PieEntry(3, "B"));
        nutriscoreEntries.add(new PieEntry(2, "C"));
        nutriscoreEntries.add(new PieEntry(1, "D"));

        PieDataSet nutriscoreDataSet = new PieDataSet(nutriscoreEntries, "Nutriscores");
        nutriscoreDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        nutriscoreDataSet.setValueTextSize(12f);
        nutriscoreDataSet.setValueTextColor(Color.BLACK);

        PieData nutriscoreData = new PieData(nutriscoreDataSet);
        nutriscoreChart.setData(nutriscoreData);
        nutriscoreChart.invalidate();

        // Données de test pour le graphique d'empreinte carbone
        List<Entry> carbonEntries = new ArrayList<>();
        carbonEntries.add(new Entry(0, 1.0f));
        carbonEntries.add(new Entry(1, 0.75f));
        carbonEntries.add(new Entry(2, 0.9f));
        carbonEntries.add(new Entry(3, 0.8f));
        carbonEntries.add(new Entry(4, 0.85f));

        LineDataSet carbonDataSet = new LineDataSet(carbonEntries, "Empreinte carbone (kg CO₂e)");
        carbonDataSet.setColor(Color.BLUE);
        carbonDataSet.setCircleColor(Color.BLUE);
        carbonDataSet.setLineWidth(2f);
        carbonDataSet.setCircleRadius(4f);
        carbonDataSet.setDrawValues(true);
        carbonDataSet.setValueTextSize(10f);

        LineData carbonData = new LineData(carbonDataSet);
        carbonChart.setData(carbonData);
        carbonChart.invalidate();
    }
}