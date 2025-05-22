package com.example.Scanner.Controller;


import com.example.Scanner.Model.Scan;
import com.example.Scanner.Service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scan")
public class ScanController {

    @Autowired
    private ScanService scanService;

    @PostMapping
    public ResponseEntity<Scan> createScan(@RequestParam Long userId,
                                           @RequestParam String barcode,
                                           @RequestParam Double empreinteEstimee) {
        try {
            Scan scan = scanService.createScan(userId, barcode, empreinteEstimee);
            return ResponseEntity.ok(scan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}