# Plan de Tests - Projet ParcInfo

## 1. Tests Unitaires

### 1.1 Tests des Modèles

#### ObjetNomade
- **Test de la création d'un objet nomade**
  - Test avec des données valides
  - Test avec des données invalides (nom vide, numéro de série invalide)
  - Test des contraintes de validation (@NotNull, @Size, etc.)

- **Test des validations de base**
  - Test de la validation du nom (longueur, caractères spéciaux)
  - Test de la validation du numéro de série (format, unicité)
  - Test de la validation de la marque et du modèle

- **Test de l'héritage et du polymorphisme**
  - Test du comportement polymorphique avec Smartphone et OrdinateurPortable
  - Test de la conversion entre types d'objets nomades

#### Smartphone
- **Test de la création d'un smartphone valide**
  - Test avec toutes les propriétés valides
  - Test avec des valeurs limites (écran, RAM, stockage, batterie)

- **Test des validations spécifiques**
  - Test de la taille d'écran (4.0 - 7.0 pouces)
  - Test de la RAM (2 - 16 Go)
  - Test du stockage (16 - 512 Go)
  - Test de la batterie (2000 - 6000 mAh)

- **Test des fonctionnalités spécifiques**
  - Test de la fonctionnalité 5G
  - Test de la double SIM
  - Test du NFC
  - Test des combinaisons de fonctionnalités

#### OrdinateurPortable
- **Test de la création d'un ordinateur portable valide**
  - Test avec toutes les propriétés valides
  - Test avec des valeurs limites (écran, RAM, stockage, batterie)

- **Test des validations spécifiques**
  - Test de la taille d'écran (11 - 17 pouces)
  - Test de la RAM (4 - 32 Go)
  - Test du stockage (128 - 2000 Go)
  - Test de la batterie (3000 - 10000 mAh)

- **Test des fonctionnalités spécifiques**
  - Test de la carte graphique (présence, type)
  - Test du type de stockage (SSD, HDD, hybride)
  - Test des combinaisons de fonctionnalités

### 1.2 Tests des Validateurs

#### SmartphoneValidator
- **Test de la validation des limites**
  - Test des valeurs minimales (écran: 4.0, RAM: 2, stockage: 16, batterie: 2000)
  - Test des valeurs maximales (écran: 7.0, RAM: 16, stockage: 512, batterie: 6000)
  - Test des valeurs limites (écran: 4.0/7.0, RAM: 2/16, stockage: 16/512, batterie: 2000/6000)

- **Test de la validation des valeurs nulles**
  - Test avec tous les champs nuls
  - Test avec des champs partiellement nuls

- **Test de la validation des valeurs invalides**
  - Test avec des valeurs hors limites
  - Test avec des valeurs négatives
  - Test avec des valeurs non numériques

#### OrdinateurPortableValidator
- **Test de la validation des limites**
  - Test des valeurs minimales (écran: 11, RAM: 4, stockage: 128, batterie: 3000)
  - Test des valeurs maximales (écran: 17, RAM: 32, stockage: 2000, batterie: 10000)
  - Test des valeurs limites (écran: 11/17, RAM: 4/32, stockage: 128/2000, batterie: 3000/10000)

- **Test de la validation des valeurs nulles**
  - Test avec tous les champs nuls
  - Test avec des champs partiellement nuls

- **Test de la validation des valeurs invalides**
  - Test avec des valeurs hors limites
  - Test avec des valeurs négatives
  - Test avec des valeurs non numériques

### 1.3 Tests des Services

#### ObjetNomadeService
- **Test des opérations CRUD**
  - Test de la création d'un objet nomade
  - Test de la lecture d'un objet nomade
  - Test de la mise à jour d'un objet nomade
  - Test de la suppression d'un objet nomade

- **Test du filtrage par type**
  - Test du filtrage par type spécifique (SMARTPHONE, LAPTOP)
  - Test du filtrage avec plusieurs types
  - Test du filtrage avec des types invalides

- **Test de la gestion des erreurs**
  - Test de la gestion des objets non trouvés
  - Test de la gestion des données invalides
  - Test de la gestion des contraintes d'intégrité

#### AuthenticationService
- **Test de l'inscription d'un nouvel utilisateur**
  - Test avec des données valides
  - Test avec des données invalides
  - Test avec un email déjà existant

- **Test de l'authentification d'un utilisateur existant**
  - Test avec des identifiants valides
  - Test avec des identifiants invalides
  - Test avec un compte verrouillé

- **Test de la gestion des erreurs d'authentification**
  - Test de la gestion des tentatives échouées
  - Test de la gestion des tokens expirés
  - Test de la gestion des sessions invalides

#### JwtService
- **Test de la génération de token**
  - Test de la génération avec des claims valides
  - Test de la génération avec des claims invalides
  - Test de la durée de validité du token

- **Test de la validation de token**
  - Test avec un token valide
  - Test avec un token expiré
  - Test avec un token malformé

- **Test de l'extraction des claims**
  - Test de l'extraction des claims valides
  - Test de l'extraction des claims manquants
  - Test de l'extraction des claims invalides

### 1.4 Tests des Repositories

#### ObjetNomadeRepository
- **Test des opérations CRUD**
  - Test de la persistance d'un objet nomade
  - Test de la récupération d'un objet nomade
  - Test de la mise à jour d'un objet nomade
  - Test de la suppression d'un objet nomade

- **Test des requêtes personnalisées**
  - Test de la recherche par critères
  - Test de la recherche par type
  - Test de la recherche par état

- **Test du filtrage par type**
  - Test du filtrage par type spécifique
  - Test du filtrage avec plusieurs types
  - Test du filtrage avec des types invalides

#### UserRepository
- **Test de la recherche par email**
  - Test avec un email existant
  - Test avec un email inexistant
  - Test avec un email invalide

- **Test de la sauvegarde d'un utilisateur**
  - Test de la création d'un nouvel utilisateur
  - Test de la mise à jour d'un utilisateur existant
  - Test de la suppression d'un utilisateur

#### PersonneRepository
- **Test des opérations CRUD**
  - Test de la création d'une personne
  - Test de la lecture d'une personne
  - Test de la mise à jour d'une personne
  - Test de la suppression d'une personne

- **Test des requêtes personnalisées**
  - Test de la recherche par nom
  - Test de la recherche par rôle
  - Test de la recherche par critères multiples

## 2. Tests d'Intégration

### 2.1 Tests des Contrôleurs

#### ObjetNomadeController
- **Test des endpoints CRUD**
  - Test de la création d'un objet nomade (POST)
  - Test de la récupération d'un objet nomade (GET)
  - Test de la mise à jour d'un objet nomade (PUT)
  - Test de la suppression d'un objet nomade (DELETE)

- **Test de la validation des données**
  - Test de la validation des données d'entrée
  - Test de la validation des données de sortie
  - Test de la gestion des erreurs de validation

- **Test des autorisations**
  - Test de l'accès autorisé
  - Test de l'accès non autorisé
  - Test de l'accès avec différents rôles

#### SmartphoneController
- **Test des endpoints spécifiques aux smartphones**
  - Test de la création d'un smartphone
  - Test de la récupération d'un smartphone
  - Test de la mise à jour d'un smartphone
  - Test de la suppression d'un smartphone

- **Test de la validation des données**
  - Test de la validation des spécifications techniques
  - Test de la validation des fonctionnalités
  - Test de la gestion des erreurs de validation

- **Test des autorisations**
  - Test de l'accès autorisé
  - Test de l'accès non autorisé
  - Test de l'accès avec différents rôles

#### OrdinateurPortableController
- **Test des endpoints spécifiques aux ordinateurs portables**
  - Test de la création d'un ordinateur portable
  - Test de la récupération d'un ordinateur portable
  - Test de la mise à jour d'un ordinateur portable
  - Test de la suppression d'un ordinateur portable

- **Test de la validation des données**
  - Test de la validation des spécifications techniques
  - Test de la validation des fonctionnalités
  - Test de la gestion des erreurs de validation

- **Test des autorisations**
  - Test de l'accès autorisé
  - Test de l'accès non autorisé
  - Test de l'accès avec différents rôles

#### AuthenticationController
- **Test de l'endpoint d'inscription**
  - Test avec des données valides
  - Test avec des données invalides
  - Test avec un email déjà existant

- **Test de l'endpoint d'authentification**
  - Test avec des identifiants valides
  - Test avec des identifiants invalides
  - Test avec un compte verrouillé

- **Test de la gestion des erreurs**
  - Test de la gestion des erreurs de validation
  - Test de la gestion des erreurs d'authentification
  - Test de la gestion des erreurs serveur

#### PersonneController
- **Test des endpoints CRUD**
  - Test de la création d'une personne
  - Test de la récupération d'une personne
  - Test de la mise à jour d'une personne
  - Test de la suppression d'une personne

- **Test de la validation des données**
  - Test de la validation des données personnelles
  - Test de la validation des rôles
  - Test de la gestion des erreurs de validation

- **Test des autorisations**
  - Test de l'accès autorisé
  - Test de l'accès non autorisé
  - Test de l'accès avec différents rôles

### 2.2 Tests de l'API
- **Test des requêtes HTTP**
  - Test des méthodes HTTP (GET, POST, PUT, DELETE)
  - Test des en-têtes HTTP
  - Test des paramètres de requête
  - Test des corps de requête

- **Test de la sérialisation/désérialisation JSON**
  - Test de la sérialisation des objets en JSON
  - Test de la désérialisation du JSON en objets
  - Test de la gestion des formats de date
  - Test de la gestion des valeurs nulles

- **Test des codes de retour HTTP**
  - Test des codes 2xx (succès)
  - Test des codes 4xx (erreurs client)
  - Test des codes 5xx (erreurs serveur)

- **Test de la gestion des erreurs**
  - Test de la gestion des erreurs de validation
  - Test de la gestion des erreurs d'authentification
  - Test de la gestion des erreurs serveur

## 3. Tests de Sécurité

### 3.1 Tests d'Authentification
- **Test de l'accès aux endpoints protégés**
  - Test de l'accès sans authentification
  - Test de l'accès avec authentification invalide
  - Test de l'accès avec authentification valide

- **Test de la validation des tokens JWT**
  - Test de la validation des tokens valides
  - Test de la validation des tokens expirés
  - Test de la validation des tokens malformés

- **Test de la gestion des sessions**
  - Test de la création de session
  - Test de la fermeture de session
  - Test de la gestion des sessions multiples

### 3.2 Tests d'Autorisation
- **Test des rôles utilisateur**
  - Test de l'accès avec le rôle ADMIN
  - Test de l'accès avec le rôle USER
  - Test de l'accès avec le rôle GUEST

- **Test des restrictions d'accès**
  - Test des restrictions par endpoint
  - Test des restrictions par ressource
  - Test des restrictions par méthode HTTP

- **Test de la gestion des permissions**
  - Test de la création de permissions
  - Test de la modification de permissions
  - Test de la suppression de permissions

## 4. Tests de Performance

### 4.1 Tests de Charge
- **Test avec un nombre élevé d'objets nomades**
  - Test avec 1000 objets nomades
  - Test avec 10000 objets nomades
  - Test avec 100000 objets nomades

- **Test avec un nombre élevé d'utilisateurs simultanés**
  - Test avec 100 utilisateurs simultanés
  - Test avec 1000 utilisateurs simultanés
  - Test avec 10000 utilisateurs simultanés

- **Test de la réponse sous charge**
  - Test du temps de réponse sous charge
  - Test de la stabilité sous charge
  - Test de la récupération après charge

- **Test de la consommation des ressources**
  - Test de la consommation CPU
  - Test de la consommation mémoire
  - Test de la consommation réseau

### 4.2 Tests de Scalabilité
- **Test de la gestion de la base de données**
  - Test de la performance des requêtes
  - Test de la gestion des connexions
  - Test de la gestion des transactions

- **Test de la mise en cache**
  - Test de la performance du cache
  - Test de la cohérence du cache
  - Test de l'invalidation du cache

- **Test de la répartition de charge**
  - Test de la répartition des requêtes
  - Test de la répartition des ressources
  - Test de la répartition des sessions

## 5. Outils de Test

### 5.1 Tests Automatisés
- **JUnit 5 pour les tests unitaires**
  - Configuration des tests unitaires
  - Exécution des tests unitaires
  - Génération des rapports de tests

- **Spring Test pour les tests d'intégration**
  - Configuration des tests d'intégration
  - Exécution des tests d'intégration
  - Génération des rapports de tests

- **MockMvc pour les tests des contrôleurs**
  - Configuration de MockMvc
  - Exécution des tests de contrôleurs
  - Génération des rapports de tests

- **H2 Database pour les tests de base de données**
  - Configuration de la base de données de test
  - Exécution des tests de base de données
  - Génération des rapports de tests

### 5.2 Tests Manuels
- **Postman pour les tests d'API**
  - Configuration des collections de tests
  - Exécution des tests d'API
  - Génération des rapports de tests

- **Tests de navigation utilisateur**
  - Test des parcours utilisateur
  - Test des fonctionnalités utilisateur
  - Test de l'expérience utilisateur

- **Tests de compatibilité navigateur**
  - Test sur Chrome
  - Test sur Firefox
  - Test sur Safari
  - Test sur Edge

## 6. Critères de Validation

### 6.1 Critères Fonctionnels
- **Toutes les fonctionnalités sont opérationnelles**
  - Fonctionnalités de base (CRUD)
  - Fonctionnalités spécifiques (validation, filtrage)
  - Fonctionnalités avancées (authentification, autorisation)

- **Les données sont correctement persistées**
  - Intégrité des données
  - Cohérence des données
  - Récupération des données

- **Les validations sont respectées**
  - Validation des données d'entrée
  - Validation des données de sortie
  - Gestion des erreurs de validation

- **Les autorisations sont respectées**
  - Contrôle d'accès
  - Gestion des rôles
  - Gestion des permissions

### 6.2 Critères Non-Fonctionnels
- **Temps de réponse < 500ms**
  - Temps de réponse moyen
  - Temps de réponse maximum
  - Temps de réponse sous charge

- **Taux d'erreur < 1%**
  - Taux d'erreur global
  - Taux d'erreur par fonctionnalité
  - Taux d'erreur sous charge

- **Couverture de code > 80%**
  - Couverture des lignes de code
  - Couverture des branches
  - Couverture des méthodes 