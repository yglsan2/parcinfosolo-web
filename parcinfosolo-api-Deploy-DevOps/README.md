# ParcInfo - Application de Gestion de Parc Informatique

## Vue d'ensemble

ParcInfo est une application web de gestion de parc informatique développée avec Spring Boot et Thymeleaf. Elle permet de gérer les équipements informatiques, les utilisateurs, les affectations et les interventions.

## Architecture

L'application suit une architecture MVC (Modèle-Vue-Contrôleur) classique avec Spring Boot et Thymeleaf :

### Composants principaux
- **Controllers** : Gèrent les requêtes HTTP et retournent les vues Thymeleaf
- **Services** : Contiennent la logique métier
- **Repositories** : Gèrent l'accès aux données
- **Models** : Représentent les entités de la base de données
- **Vues** : Templates Thymeleaf pour l'affichage

### Communication entre les composants
1. Le contrôleur reçoit une requête HTTP
2. Il utilise le service approprié pour récupérer ou modifier les données
3. Le service utilise le repository pour accéder à la base de données
4. Le contrôleur passe les données au modèle et retourne la vue correspondante
5. Thymeleaf génère la page HTML finale

## Fonctionnalités principales

### Gestion des équipements
- Ordinateurs
- Périphériques
- État et maintenance

### Gestion des utilisateurs
- Authentification
- Gestion des rôles
- Profils utilisateurs

### Gestion des affectations
- Attribution d'équipements
- Suivi des affectations
- Historique des mouvements

### Gestion des interventions
- Suivi des maintenances
- Historique des interventions
- Planning des maintenances

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── com/parcinfo/
│   │       ├── controller/
│   │       │   ├── PersonneController.java
│   │       │   ├── OrdinateurController.java
│   │       │   ├── PeripheriqueController.java
│   │       │   └── AuthController.java
│   │       ├── service/
│   │       │   ├── PersonneService.java
│   │       │   ├── OrdinateurService.java
│   │       │   └── PeripheriqueService.java
│   │       ├── model/
│   │       │   ├── Personne.java
│   │       │   ├── Ordinateur.java
│   │       │   └── Peripherique.java
│   │       └── repository/
│   │           ├── PersonneRepository.java
│   │           ├── OrdinateurRepository.java
│   │           └── PeripheriqueRepository.java
│   └── resources/
│       ├── templates/
│       │   ├── layout.html
│       │   ├── personnes/
│       │   ├── ordinateurs/
│       │   └── peripheriques/
│       ├── static/
│       │   ├── css/
│       │   └── js/
│       └── application.properties
└── test/
    └── java/
        └── com/parcinfo/
            └── controller/
                ├── PersonneControllerTest.java
                └── PeripheriqueControllerTest.java
```

## Configuration

### Properties
```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/parcinfodb
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Static Resources
spring.web.resources.static-locations=classpath:/static/
spring.mvc.static-path-pattern=/static/**

# Logging
logging.level.com.parcinfo=DEBUG
logging.level.org.springframework.web=INFO
```

### Dépendances Maven
```xml
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Thymeleaf -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    
    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- MySQL -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Bonnes pratiques

### Contrôleurs
- Utiliser les annotations `@Controller` et `@RequestMapping`
- Retourner des noms de vues Thymeleaf
- Utiliser `Model` pour passer les données aux vues
- Gérer les erreurs avec `@ExceptionHandler`

### Services
- Implémenter la logique métier
- Utiliser les repositories pour l'accès aux données
- Gérer les transactions avec `@Transactional`

### Vues Thymeleaf
- Utiliser les fragments pour le code réutilisable
- Suivre une structure cohérente pour les templates
- Utiliser les layouts pour maintenir une apparence uniforme

### Sécurité
- Utiliser Spring Security pour l'authentification
- Configurer les rôles et permissions
- Protéger les endpoints sensibles

## Installation et démarrage

1. Cloner le repository
```bash
git clone https://github.com/votre-username/parcinfo.git
```

2. Configurer la base de données
- Créer une base de données MySQL nommée "parcinfo"
- Mettre à jour les informations de connexion dans `application.properties`

3. Compiler le projet
```bash
./mvnw clean package
```

4. Lancer l'application
```bash
java -jar target/parcinfo3-0.0.1-SNAPSHOT.jar
```

L'application sera accessible à l'adresse : http://localhost:8080

## Tests

Pour exécuter les tests :
```bash
./mvnw test
```

## Contribution

1. Fork le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Créer une Pull Request

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails. 