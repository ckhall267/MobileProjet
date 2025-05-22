package com.example.eco_track.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eco_track.R;
import com.example.eco_track.model.ProductHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductHistoryAdapter extends RecyclerView.Adapter<ProductHistoryAdapter.ViewHolder> {
    private List<ProductHistory> allProducts;
    private List<ProductHistory> filteredProducts;
    private SimpleDateFormat dateFormat;

    public ProductHistoryAdapter() {
        this.allProducts = new ArrayList<>();
        this.filteredProducts = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductHistory product = filteredProducts.get(position);
        holder.textViewProductName.setText(product.getName());
        holder.textViewNutriscore.setText("Nutriscore : " + product.getNutriscore());
        holder.textViewEcoscore.setText("Ecoscore : " + product.getEcoscore());
        holder.textViewCarbonFootprint.setText("Empreinte carbone : " + product.getCarbonFootprint());
        holder.textViewScanDate.setText("Scanné le " + dateFormat.format(product.getScanDate()));
    }

    @Override
    public int getItemCount() {
        return filteredProducts.size();
    }

    public void setProducts(List<ProductHistory> products) {
        this.allProducts = products;
        this.filteredProducts = new ArrayList<>(products);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredProducts.clear();
        if (query.isEmpty()) {
            filteredProducts.addAll(allProducts);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (ProductHistory product : allProducts) {
                if (product.getName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredProducts.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void addProduct(ProductHistory product) {
        allProducts.add(0, product);
        filter("");  // Réappliquer le filtre actuel
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName;
        TextView textViewNutriscore;
        TextView textViewEcoscore;
        TextView textViewCarbonFootprint;
        TextView textViewScanDate;

        ViewHolder(View view) {
            super(view);
            textViewProductName = view.findViewById(R.id.textViewProductName);
            textViewNutriscore = view.findViewById(R.id.textViewNutriscore);
            textViewEcoscore = view.findViewById(R.id.textViewEcoscore);
            textViewCarbonFootprint = view.findViewById(R.id.textViewCarbonFootprint);
            textViewScanDate = view.findViewById(R.id.textViewScanDate);
        }
    }
} 