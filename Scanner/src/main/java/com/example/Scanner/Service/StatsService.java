package com.example.Scanner.Service;


import com.example.Scanner.Model.Scan;
import com.example.Scanner.Model.User;
import com.example.Scanner.Repository.ScanRepository;
import com.example.Scanner.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatsService {

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private UserRepository userRepository;

    public Double getAverageCarbonFootprint(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            List<Scan> scans = scanRepository.findByUser(userOpt.get());
            if (scans.isEmpty()) return 0.0;

            return scans.stream()
                    .mapToDouble(Scan::getEmpreinteEstimee)
                    .average()
                    .orElse(0.0);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
