# ParcInfoSolo Web Application

## 🚀 Vue d'ensemble

ParcInfoSolo est une application de gestion de parc informatique divisée en deux parties distinctes :
- **API** : Service Spring Boot MVC avec endpoints (projet séparé)
- **Web** : Interface utilisateur (ce projet)

Cette partie Web est une application Spring Boot qui consomme l'API via des endpoints et présente les données via une interface utilisateur Thymeleaf.

## 🛠 Technologies utilisées

- **Java 21** (OpenJDK)
- **Spring Boot 3.x**
- **Thymeleaf** pour le templating
- **Maven** pour la gestion des dépendances
- **Bootstrap** pour le frontend
- **Spring Security** pour l'authentification
- **Spring Data JPA** pour la persistance

## 📋 Prérequis

- Java 21 (OpenJDK)
- Maven 3.8+
- Un IDE (IntelliJ IDEA, Eclipse, VS Code)
- Accès à l'API ParcInfoSolo (projet séparé)

## 🚀 Installation et démarrage

1. **Cloner le repository**
```bash
git clone https://github.com/yglsan2/parcinfosolo-web.git
cd parcinfosolo-web
```

2. **Vérifier la version de Java**
```bash
java -version
# Doit afficher Java 21
```

3. **Compiler le projet**
```bash
./mvnw clean install
```

4. **Lancer l'application**
```bash
./mvnw spring-boot:run
```

L'application sera accessible à l'adresse : `http://localhost:8080`

## 🏗 Architecture

L'application suit une architecture MVC (Model-View-Controller) :

- **Controllers** : Gèrent les requêtes HTTP avec des endpoints Spring MVC
- **Services** : Implémentent la logique métier
- **Models** : Représentent les entités de l'application
- **Templates** : Vues Thymeleaf pour l'interface utilisateur

## 📁 Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── com/parcinfo/web/
│   │       ├── controller/    # Contrôleurs Spring MVC
│   │       ├── service/       # Services métier
│   │       ├── model/         # Entités
│   │       └── config/        # Configuration Spring
│   └── resources/
│       ├── templates/         # Templates Thymeleaf
│       ├── static/           # Ressources statiques (CSS)
│       └── application.properties
```

## 🔄 Communication avec l'API

L'application web communique avec l'API via des endpoints Spring MVC. Les endpoints principaux sont :

- Gestion des ordinateurs
- Gestion des périphériques
- Gestion des interventions
- Gestion des utilisateurs

## 🔒 Sécurité

- Authentification par formulaire
- Gestion des rôles (ADMIN, USER)
- Protection CSRF
- Sessions sécurisées

## 🧪 Tests

Pour lancer les tests :
```bash
./mvnw test
```

## 📝 Contribution

Ce projet est actuellement développé individuellement. Si vous souhaitez contribuer, n'hésitez pas à ouvrir une issue pour discuter des améliorations proposées.

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👤 Auteur

- **Benjamin (yglsan)** - Développeur Full Stack

## 🙏 Remerciements

- La communauté Spring Boot pour leur documentation exceptionnelle 