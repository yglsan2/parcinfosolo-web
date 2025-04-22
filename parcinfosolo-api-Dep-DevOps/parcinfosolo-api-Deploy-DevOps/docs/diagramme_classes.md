# Diagramme de Classes - ParcInfo

## Entités principales

### User
- **Attributs**:
  - id: Long (PK)
  - firstname: String
  - lastname: String
  - email: String (unique)
  - password: String (encrypted)
  - role: Role (enum)
- **Relations**:
  - Aucune relation directe avec d'autres entités

### Role (Enum)
- **Valeurs**:
  - USER
  - ADMIN

### Personne
- **Attributs**:
  - id: Long (PK)
  - nom: String
  - prenom: String
  - email: String
  - telephone: String
  - adresse: String
- **Relations**:
  - Aucune relation directe avec d'autres entités

## Diagramme au format PlantUML

```plantuml
@startuml

enum Role {
  USER
  ADMIN
}

class User {
  -id: Long
  -firstname: String
  -lastname: String
  -email: String
  -password: String
  -role: Role
  +getAuthorities(): Collection<GrantedAuthority>
  +getPassword(): String
  +getUsername(): String
  +isAccountNonExpired(): boolean
  +isAccountNonLocked(): boolean
  +isCredentialsNonExpired(): boolean
  +isEnabled(): boolean
}

class Personne {
  -id: Long
  -nom: String
  -prenom: String
  -email: String
  -telephone: String
  -adresse: String
}

User "1" -- "1" Role : a

@enduml
```

## Notes
- Le système utilise Spring Security pour l'authentification et l'autorisation
- Les mots de passe sont chiffrés avec BCrypt
- L'authentification se fait via JWT (JSON Web Token)
- Les utilisateurs peuvent avoir deux rôles: USER ou ADMIN 