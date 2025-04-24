# Architecture du Projet ParcInfo

## Vue d'ensemble

Le projet ParcInfo est structuré en deux parties distinctes et complémentaires :

1. **API** (`parcinfosolo-api`) : Fournit les services backend et la gestion des données
2. **Application Web** (`parcinfosolo-web`) : Interface utilisateur avec Thymeleaf

## Architecture de l'API

### Caractéristiques principales
- Contrôleurs simples (`@Controller`)
- Endpoints standardisés (`/api/*`)
- Gestion des données via JPA/Hibernate
- Réponses JSON directes (sans wrapper)

### Exemple de contrôleur API
```java
@Controller
@RequestMapping("/api/peripheriques")
public class PeripheriqueController {
    
    private final PeripheriqueService peripheriqueService;
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Peripherique>> getAll() {
        List<Peripherique> peripheriques = peripheriqueService.findAll();
        return ResponseEntity.ok(peripheriques);
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Peripherique> getById(@PathVariable Long id) {
        return peripheriqueService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
```

## Architecture de l'Application Web

### Caractéristiques principales
- Contrôleurs MVC (`@Controller`)
- Vues Thymeleaf pour le rendu HTML
- Injection de données via `Model`
- Navigation traditionnelle (non-SPA)
- Communication avec l'API via `RestTemplate`

### Exemple de contrôleur Web
```java
@Controller
@RequestMapping("/peripheriques")
public class PeripheriqueWebController {
    
    private final RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:8081/api";
    
    @GetMapping
    public String list(Model model) {
        // Appel à l'API
        ResponseEntity<List<Peripherique>> response = restTemplate.exchange(
            apiUrl + "/peripheriques",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Peripherique>>() {}
        );
        
        // Récupération des données
        List<Peripherique> peripheriques = response.getBody();
        model.addAttribute("peripheriques", peripheriques);
        return "peripheriques/liste";
    }
    
    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        // Appel à l'API
        ResponseEntity<Peripherique> response = restTemplate.exchange(
            apiUrl + "/peripheriques/" + id,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Peripherique>() {}
        );
        
        // Récupération des données
        Peripherique peripherique = response.getBody();
        model.addAttribute("peripherique", peripherique);
        return "peripheriques/details";
    }
}
```

## Communication entre les composants

1. **API → Base de données**
   - Accès direct via JPA/Hibernate
   - Opérations CRUD standard
   - Réponses JSON directes

2. **Web → API**
   - Communication via `RestTemplate`
   - Appels API pour récupérer les données
   - Transformation des données pour l'affichage

3. **Web → Templates Thymeleaf**
   - Injection des données via `Model`
   - Rendu des vues avec les données

## Structure des vues Thymeleaf (dans l'application Web)

### Layout principal
- Template de base (`layout.html`)
- Navigation commune
- Zones de contenu injectables via `layout:fragment`

### Pages spécifiques
- Héritent du layout principal via `th:replace`
- Contenu spécifique injecté dans les zones définies
- Utilisation des expressions Thymeleaf pour l'affichage dynamique

### Exemple de template
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" 
      th:replace="~{layout :: html(title='Périphériques')}">
<body>
    <div layout:fragment="content">
        <div class="container">
            <h1>Liste des périphériques</h1>
            <table class="table">
                <tr th:each="peripherique : ${peripheriques}">
                    <td th:text="${peripherique.nom}">Nom</td>
                    <td th:text="${peripherique.type}">Type</td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
```

## Bonnes pratiques

1. **Séparation des responsabilités**
   - API : Gestion des données et logique métier
   - Web : Interface utilisateur et présentation
   - Communication claire entre les deux parties

2. **Contrôleurs**
   - API : `@Controller` avec `@ResponseBody` pour les endpoints
   - Web : `@Controller` pour les vues Thymeleaf

3. **Templates Thymeleaf**
   - Utilisation cohérente du layout
   - Fragments réutilisables
   - Expressions Thymeleaf appropriées

4. **Sécurité**
   - Authentification centralisée
   - Gestion des rôles et permissions
   - Protection CSRF

## Configuration

### API
```properties
# Configuration du serveur
server.port=8081

# Configuration de la base de données
spring.datasource.url=jdbc:mysql://localhost:3306/parcinfo_db
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuration CORS
spring.mvc.cors.allowed-origins=http://localhost:8080
spring.mvc.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.mvc.cors.allowed-headers=*
```

### Web
```properties
# Configuration du serveur
server.port=8080

# Configuration Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# URL de l'API
api.base-url=http://localhost:8081/api

# Configuration des ressources statiques
spring.web.resources.static-locations=classpath:/static/
```

## Structure des dossiers
```
parcinfosolo-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/parcinfo/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── model/
│   │   │       └── repository/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/parcinfo/
│               └── controller/

parcinfosolo-web/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/parcinfo/web/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       └── config/
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── layout.html
│   │       │   └── peripheriques/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/parcinfo/web/
│               └── controller/
``` 