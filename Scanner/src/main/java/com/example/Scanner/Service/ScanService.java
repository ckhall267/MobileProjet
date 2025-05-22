package com.example.Scanner.Service;


import com.example.Scanner.Model.Product;
import com.example.Scanner.Model.Scan;
import com.example.Scanner.Model.User;
import com.example.Scanner.Repository.ProductRepository;
import com.example.Scanner.Repository.ScanRepository;
import com.example.Scanner.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ScanService {

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Scan createScan(Long userId, String barcode, Double empreinteEstimee) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Product> productOpt = productRepository.findByCodeBarre(barcode);

        if (userOpt.isPresent() && productOpt.isPresent()) {
            Scan scan = new Scan();
            scan.setUser(userOpt.get());
            scan.setProduct(productOpt.get());
            scan.setDateScan(LocalDateTime.now());
            scan.setEmpreinteEstimee(empreinteEstimee);
            return scanRepository.save(scan);
        } else {
            throw new RuntimeException("User or Product not found");
        }
    }
}