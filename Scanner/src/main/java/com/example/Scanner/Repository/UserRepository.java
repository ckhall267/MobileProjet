package com.example.Scanner.Repository;


import com.example.Scanner.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // Pour vérifier si l'email existe déjà
    User findByEmail(String email);

}