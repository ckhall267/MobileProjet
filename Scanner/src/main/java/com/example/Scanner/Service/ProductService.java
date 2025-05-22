package com.example.Scanner.Service;


import com.example.Scanner.Model.OpenFoodFactsResponse;
import com.example.Scanner.Model.Product;
import com.example.Scanner.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final String API_URL = "https://world.openfoodfacts.org/api/v0/product/";

    public Product getProductByBarcode(String barcode) {
        Optional<Product> localProduct = productRepository.findByCodeBarre(barcode);
        if (localProduct.isPresent()) {
            return localProduct.get();
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String url = API_URL + barcode + ".json";
            var response = restTemplate.getForObject(url, OpenFoodFactsResponse.class);

            if (response != null && response.getProduct() != null) {
                var p = response.getProduct();
                Product product = new Product(null, barcode, p.getProductName(), p.getNutritionGradeFr(), p.getEcoscoreGrade(), p.getOrigins());
                return productRepository.save(product);
            } else {
                return null;
            }
        }
    }
}
