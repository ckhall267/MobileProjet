package com.example.Scanner.Controller;




import com.example.Scanner.Model.Product;
import com.example.Scanner.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{barcode}")
    public ResponseEntity<Product> getProduct(@PathVariable String barcode) {
        Product product = productService.getProductByBarcode(barcode);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}