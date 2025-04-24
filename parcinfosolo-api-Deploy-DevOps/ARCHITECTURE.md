# Architecture MVC de l'Application ParcInfo

## Principes Fondamentaux

1. **Architecture MVC Traditionnelle**
   - Utilisation de Spring MVC (pas de REST)
   - Contrôleurs annotés avec `@Controller` (pas `@RestController`)
   - Vues Thymeleaf pour le rendu côté serveur
   - Pas d'API REST, pas de `RestTemplate` ni `WebClient`

2. **Contrôleurs**
   - Retournent des noms de vues Thymeleaf
   - Utilisent `Model` pour injecter les données dans les vues
   - Gèrent les formulaires avec `@Valid` et `BindingResult`
   - Utilisent `RedirectAttributes` pour les messages flash
   - Pas de `ResponseEntity` ni de `ApiResponse`

3. **Services**
   - Logique métier pure
   - Accès direct aux repositories JPA
   - Pas de logique API ou REST
   - Méthodes CRUD standard : `findAll()`, `findById()`, `save()`, `delete()`

4. **Modèles**
   - Entités JPA avec validation
   - Pas de DTOs ni de wrappers de réponse
   - Relations directes entre entités

5. **Vues**
   - Templates Thymeleaf
   - Formulaires avec validation côté serveur
   - Messages d'erreur et de succès via flash attributes
   - Navigation standard (pas de SPA)

## Règles de Développement

1. **Contrôleurs**
   ```java
   @Controller
   @RequestMapping("/peripheriques")
   public class PeripheriqueController {
       @GetMapping
       public String list(Model model) {
           model.addAttribute("peripheriques", service.findAll());
           return "peripheriques/list";
       }
   }
   ```

2. **Services**
   ```java
   @Service
   @Transactional
   public class PeripheriqueService {
       @Autowired
       private PeripheriqueRepository repository;
       
       public List<Peripherique> findAll() {
           return repository.findAll();
       }
   }
   ```

3. **Tests**
   ```java
   @Test
   void list_ShouldReturnListView() {
       when(service.findAll()).thenReturn(testPeripheriques);
       String viewName = controller.list(model);
       assertEquals("peripheriques/list", viewName);
   }
   ```

## Interdictions

1. ❌ Pas de `@RestController`
2. ❌ Pas de `RestTemplate` ou `WebClient`
3. ❌ Pas de `ApiResponse` ou wrappers de réponse
4. ❌ Pas de endpoints REST
5. ❌ Pas de DTOs pour les réponses API
6. ❌ Pas de `ResponseEntity` dans les contrôleurs

## Bonnes Pratiques

1. ✅ Validation des formulaires avec `@Valid`
2. ✅ Gestion des erreurs avec try/catch
3. ✅ Messages utilisateur via `RedirectAttributes`
4. ✅ Vues Thymeleaf pour le rendu
5. ✅ Tests unitaires des contrôleurs
6. ✅ Tests d'intégration des services 