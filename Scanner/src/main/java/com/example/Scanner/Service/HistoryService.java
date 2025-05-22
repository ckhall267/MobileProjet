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
public class HistoryService {

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Scan> getUserHistory(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return scanRepository.findByUser(userOpt.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}