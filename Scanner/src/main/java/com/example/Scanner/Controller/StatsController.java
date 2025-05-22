package com.example.Scanner.Controller;



import com.example.Scanner.Service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/{userId}")
    public ResponseEntity<Double> getAverageCarbonFootprint(@PathVariable Long userId) {
        try {
            Double average = statsService.getAverageCarbonFootprint(userId);
            return ResponseEntity.ok(average);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}