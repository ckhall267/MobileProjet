package com.example.Scanner.Service;

import com.example.Scanner.Model.User;
import com.example.Scanner.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Vérifie si l'email existe déjà
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("L'email est déjà utilisé");
        }

        // Hash le mot de passe avant de sauvegarder (optionnel mais recommandé)
        user.setPassword(hashPassword(user.getPassword()));

        // Sauvegarde l'utilisateur
        return userRepository.save(user);
    }

    // Méthode pour hasher le mot de passe (utilisez BCrypt ou un autre algorithme sécurisé)
    private String hashPassword(String password) {
        // Exemple avec BCrypt
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // 🔐 Méthode d'authentification (correctement implémentée ici ✅)
    public User authenticateUser(String email, String password) {
        // Cherche l'utilisateur par email
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null; // Utilisateur non trouvé
        }

        // Vérifie si le mot de passe est correct (si tu utilises BCrypt)
        if (BCrypt.checkpw(password, user.getPassword())) {
            return user; // Authentification réussie
        } else {
            return null; // Mot de passe incorrect
        }
    }
}