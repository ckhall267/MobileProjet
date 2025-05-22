package com.example.Scanner.Repository;

import com.example.Scanner.Model.Scan;
import com.example.Scanner.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScanRepository extends JpaRepository<Scan, Long> {
    List<Scan> findByUser(User user);
}