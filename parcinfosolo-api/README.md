# ParcInfoSolo API

API REST pour la gestion des appareils et personnes.

## Configuration de la sécurité

### Variables d'environnement requises

Pour des raisons de sécurité, certaines variables d'environnement doivent être configurées avant de démarrer l'application :

```bash
# Poivre pour le hachage des mots de passe (générer avec une commande sécurisée)
export SECURITY_PEPPER="votre_poivre_securise"

# Clé de chiffrement pour Jasypt (chiffrement des propriétés sensibles)
export JASYPT_PASSWORD="votre_cle_jasypt"

# Clé secrète pour JWT (générer avec une commande sécurisée)
export JWT_SECRET="votre_cle_jwt"

# Clé API OpenWeather
export OPENWEATHER_API_KEY="votre_cle_api_openweather"

# Identifiants de la base de données
export DB_USERNAME="votre_utilisateur_db"
export DB_PASSWORD="votre_mot_de_passe_db"

# Mot de passe du keystore SSL
export KEYSTORE_PASSWORD="votre_mot_de_passe_keystore"
```

### Génération des clés de sécurité

Pour générer des clés de sécurité sécurisées, vous pouvez utiliser les commandes suivantes :

```bash
# Générer le poivre (32 octets en base64)
openssl rand -base64 32

# Générer la clé JWT (32 octets en base64)
openssl rand -base64 32

# Générer la clé Jasypt (16 octets en base64)
openssl rand -base64 16
```

### Configuration du keystore SSL

Pour générer un keystore SSL pour le développement :

```bash
keytool -genkeypair -alias parcinfosolo -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore keystore.p12 -validity 3650 \
  -storepass votre_mot_de_passe_keystore
```

## Sécurité

L'application implémente plusieurs mesures de sécurité :

1. **Hachage des mots de passe**
   - Utilisation d'Argon2id avec sel et poivre
   - Paramètres recommandés pour la résistance aux attaques

2. **Protection contre les attaques par force brute**
   - Limite de tentatives par utilisateur et par IP
   - Verrouillage progressif
   - Délai d'attente configurable

3. **Sécurité des sessions**
   - Sessions stateless avec JWT
   - Limitation à une session par utilisateur
   - Cookies sécurisés (HttpOnly, Secure, SameSite)

4. **Protection CSRF**
   - Tokens CSRF avec rotation
   - Double soumission des tokens

5. **En-têtes de sécurité**
   - Protection XSS
   - Politique de référence stricte
   - Content Security Policy
   - Protection contre le clickjacking

6. **SSL/TLS**
   - TLS 1.3 uniquement
   - Cipher suites sécurisées
   - Certificats PKCS12

## Développement

### Prérequis

- Java 21
- Maven 3.8+
- MySQL 8.0+

### Installation

1. Cloner le repository
2. Configurer les variables d'environnement
3. Compiler le projet : `mvn clean install`
4. Lancer l'application : `mvn spring-boot:run`

### Tests

```bash
# Exécuter tous les tests
mvn test

# Exécuter les tests avec couverture
mvn verify
```

## Documentation API

La documentation de l'API est disponible via Swagger UI à l'adresse :
http://localhost:8080/swagger-ui.html 