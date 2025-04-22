# Guide d'Utilisation du Script de Déploiement GitHub Actions 🚀

Ce guide vous aidera à configurer et utiliser le script de déploiement GitHub Actions pour votre API ParcInfo.

## Prérequis

Avant d'utiliser le script, assurez-vous d'avoir :

- macOS ou Linux avec zsh installé
- Accès à un compte GitHub avec les droits d'administration sur le repository
- Un serveur de production configuré avec Docker et Docker Compose
- Les comptes suivants (optionnels mais recommandés) :
  - SonarCloud
  - Snyk
  - Datadog
  - AWS (pour les backups)
  - Slack/Teams (pour les notifications)

## Installation

1. Clonez le repository ou téléchargez les fichiers :
```bash
git clone <votre-repo>
cd <votre-repo>/GTA-scriptfull
```

2. Rendez le script exécutable :
```bash
chmod +x setup-github-actions.sh
```

## Utilisation

1. Lancez le script :
```bash
./setup-github-actions.sh
```

2. Le script va :
   - Vérifier et installer les prérequis si nécessaire
   - Vous guider pour l'authentification GitHub
   - Générer les secrets nécessaires
   - Configurer les secrets GitHub
   - Créer le workflow GitHub Actions
   - Configurer la protection des branches

3. Suivez les instructions interactives pour fournir les informations requises :
   - Informations de base (repository, SSH, etc.)
   - Informations Docker
   - URLs des webhooks
   - Tokens d'API
   - Configuration AWS
   - Configuration SMTP

## Commandes Utiles

### Déploiement Manuel

Pour déclencher un déploiement manuel :
```bash
gh workflow run deploy.yml
```

### Déploiement sur un Environnement Spécifique

```bash
gh workflow run deploy.yml -f environment=staging
```

### Déploiement d'une Version Spécifique

```bash
gh workflow run deploy.yml -f version=v1.2.3
```

### Déploiement Sans Notifications

```bash
gh workflow run deploy.yml -f notify=false
```

## Structure du Workflow

Le workflow GitHub Actions comprend :

1. **Job de Sécurité** :
   - Analyse SonarCloud
   - Scan Snyk
   - Test OWASP ZAP

2. **Job de Déploiement** :
   - Vérification de la santé du serveur
   - Backup de la base de données
   - Déploiement avec Docker Compose
   - Configuration de Datadog
   - Vérification de la santé de l'application
   - Notifications (Slack, Teams, Email)

## Gestion des Secrets

Les secrets sont stockés de manière sécurisée dans GitHub et incluent :
- Credentials SSH
- Tokens d'API
- Mots de passe des services
- Clés de chiffrement
- Configuration SMTP
- Credentials AWS

## Protection des Branches

La branche `main` est protégée avec :
- Revues de code obligatoires
- Vérifications de statut requises
- Protection contre les suppressions
- Protection contre les force-push

## Dépannage

### Problèmes Courants

1. **Erreur d'authentification GitHub** :
```bash
gh auth login
```

2. **Erreur de permissions** :
```bash
gh auth status
```

3. **Problèmes de déploiement** :
```bash
gh run list
gh run view <run-id>
```

### Logs et Monitoring

- Consultez les logs GitHub Actions dans l'onglet "Actions"
- Vérifiez les métriques Datadog
- Consultez les logs Docker sur le serveur

## Bonnes Pratiques

1. **Sécurité** :
   - Changez régulièrement les secrets
   - Utilisez des tokens à rotation
   - Activez l'authentification à deux facteurs

2. **Déploiement** :
   - Testez d'abord en staging
   - Vérifiez les backups avant chaque déploiement
   - Surveillez les métriques après le déploiement

3. **Maintenance** :
   - Nettoyez régulièrement les anciennes images Docker
   - Mettez à jour les dépendances
   - Vérifiez les logs d'erreur

## Support

Pour toute question ou problème :
1. Consultez les logs GitHub Actions
2. Vérifiez la documentation GitHub
3. Contactez l'équipe DevOps

## Licence

Ce script est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails. 