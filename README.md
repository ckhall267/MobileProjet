# MobileProjet

# EcoTrack - Plateforme de Scan Écologique

##  Description
Solution full-stack pour l'analyse environnementale des produits :
- **Backend** : API REST Spring Boot (Java 17)
- **Frontend** : Application Android (Java)
- **Base de données** : MySQL 8.0

 Architecture Technique

Backend (Spring Boot)

src/
├── main/
│   ├── java/com/example/ecotrack/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java       # Configuration Spring Security
│   │   │   └── SwaggerConfig.java        # Documentation API
│   │   ├── controller/
│   │   │   ├── AuthController.java       # /api/auth/**
│   │   │   ├── ProductController.java    # /api/products/**
│   │   │   └── ScanController.java       # /api/scans/**
│   │   ├── model/
│   │   │   ├── Product.java              # @Entity
│   │   │   ├── Scan.java                 # Relation ManyToOne avec User
│   │   │   └── User.java                 # Implémente UserDetails
│   │   ├── repository/
│   │   │   ├── ProductRepository.java    # JpaRepository
│   │   │   └── ScanRepository.java       # Requêtes personnalisées
│   │   ├── service/
│   │   │   ├── ProductService.java       # Logique métier produits
│   │   │   └── UserService.java          # Gestion des utilisateurs
│   │   └── EcoTrackApplication.java      # @SpringBootApplication
│   └── resources/
│       ├── application.properties        # Config DB/Port
│       └── data.sql                      # Données initiales


Frontend (Android)

app/
├── src/
│   ├── main/
│   │   ├── java/com/example/ecotrack/
│   │   │   ├── api/
│   │   │   │   └── RetrofitClient.java   # Singleton Retrofit
│   │   │   ├── model/
│   │   │   │   ├── ApiResponse.java      # Modèle des réponses API
│   │   │   │   └── Product.java          # DTO Android
│   │   │   ├── ui/
│   │   │   │   ├── auth/
│   │   │   │   │   ├── LoginActivity.java
│   │   │   │   │   └── RegisterActivity.java
│   │   │   │   ├── products/
│   │   │   │   │   ├── ProductListActivity.java
│   │   │   │   │   └── ProductDetailActivity.java
│   │   │   │   └── scans/
│   │   │   │       ├── ScanHistoryActivity.java
│   │   │   │       └── ScannerActivity.java
│   │   │   └── utils/
│   │   │       └── AuthHelper.java       # Gestion du token JWT
│   │   └── res/
│   │       ├── layout/
│   │       │   ├── activity_login.xml
│   │       │   └── item_product.xml
│   │       └── values/
│   │           ├── strings.xml
│   │           └── colors.xml


Endpoints Clés

| Méthode | Endpoint                | Requête                          | Réponse                          |
|---------|-------------------------|----------------------------------|----------------------------------|
| POST    | /api/auth/login         | { email, password }             | { token, userId }               |
| GET     | /api/products/{code}    | -                                | ProductDTO                       |
| POST    | /api/scans/             | { userId, productCode, date }   | Scan                             |
| GET     | /api/users/{id}/scans   | -                                | List<ScanWithProduct>            |

 Déploiement

1. **Initialiser la base de données** :
bash
mysql -u root -p < database/schema.sql


2. **Lancer le backend** :
bash
cd backend && mvn spring-boot:run


3. **Compiler l'APK Android** :
bash
cd frontend && ./gradlew assembleDebug

 
