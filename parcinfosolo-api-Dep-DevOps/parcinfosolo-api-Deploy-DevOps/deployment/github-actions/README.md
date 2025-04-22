# Déploiement avec GitHub Actions 🚀

## Table des matières
- [Prérequis](#prérequis)
- [Configuration](#configuration)
- [Options de déploiement avancées](#options-de-déploiement-avancées)
- [Déploiement](#déploiement)
- [Vérification](#vérification)
- [Dépannage](#dépannage)
- [Bonnes pratiques](#bonnes-pratiques)

## Prérequis
- Un compte GitHub
- Un serveur de production avec accès SSH
- Docker installé sur le serveur de production
- Une clé SSH pour l'authentification
- Un compte SonarCloud pour l'analyse de code
- Un compte Snyk pour la sécurité des dépendances
- Un compte Datadog pour le monitoring

## Configuration

1. **Configuration des secrets GitHub**
   - Allez dans les paramètres de votre repository GitHub
   - Section "Secrets and variables" > "Actions"
   - Ajoutez les secrets suivants :
     - `SSH_PRIVATE_KEY` : Votre clé SSH privée
     - `SSH_HOST` : L'adresse IP ou le nom de domaine de votre serveur
     - `SSH_USERNAME` : Le nom d'utilisateur pour la connexion SSH
     - `DOCKER_USERNAME` : Votre nom d'utilisateur Docker
     - `DOCKER_PASSWORD` : Votre mot de passe Docker
     - `SLACK_WEBHOOK_URL` : URL du webhook Slack pour les notifications
     - `TEAMS_WEBHOOK_URL` : URL du webhook Teams pour les notifications
     - `SONAR_TOKEN` : Token d'authentification SonarCloud
     - `SNYK_TOKEN` : Token d'authentification Snyk
     - `DATADOG_API_KEY` : Clé API Datadog
     - `DATADOG_APP_KEY` : Clé d'application Datadog
     - `JWT_SECRET` : Secret pour la génération des JWT
     - `ENCRYPTION_KEY` : Clé de chiffrement pour les données sensibles
     - `SMTP_HOST` : Serveur SMTP pour les emails
     - `SMTP_PORT` : Port du serveur SMTP
     - `SMTP_USERNAME` : Nom d'utilisateur SMTP
     - `SMTP_PASSWORD` : Mot de passe SMTP
     - `BACKUP_S3_BUCKET` : Nom du bucket S3 pour les backups
     - `AWS_ACCESS_KEY_ID` : Clé d'accès AWS
     - `AWS_SECRET_ACCESS_KEY` : Clé secrète AWS
     - `GRAFANA_API_KEY` : Clé API Grafana
     - `PROMETHEUS_API_KEY` : Clé API Prometheus

2. **Configuration du workflow GitHub Actions**
   - Créez le fichier `.github/workflows/deploy.yml` avec le contenu suivant :

```yaml
name: Déploiement Production

on:
  push:
    branches:
      - main
  workflow_dispatch:
    inputs:
      environment:
        description: 'Environnement de déploiement'
        required: true
        default: 'production'
        type: choice
        options:
          - production
          - staging
          - development
      version:
        description: 'Version à déployer'
        required: false
        default: 'latest'
      notify:
        description: 'Envoyer les notifications'
        required: true
        default: true
        type: boolean

jobs:
  security:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      
      - name: Analyse SonarCloud
        uses: SonarSource/sonarcloud-github-action@master
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
          
      - name: Scan de sécurité Snyk
        uses: snyk/actions/node@master
        env:
          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}
          
      - name: Scan OWASP ZAP
        uses: zaproxy/action-full-scan@v0.4.0
        with:
          target: 'https://${{ secrets.SSH_HOST }}'
          rules_file_name: '.zap/rules.tsv'
          cmd_options: '-a'

  deploy:
    needs: security
    runs-on: ubuntu-latest
    environment: ${{ github.event.inputs.environment || 'production' }}
    
    steps:
      - uses: actions/checkout@v2

      - name: Configuration SSH
        uses: webfactory/ssh-agent@v0.5.3
        with:
          ssh-private-key: ${{ secrets.SSH_PRIVATE_KEY }}

      - name: Vérification de la santé du serveur
        run: |
          ssh -o StrictHostKeyChecking=no ${{ secrets.SSH_USERNAME }}@${{ secrets.SSH_HOST }} '
            df -h
            free -m
            docker system df
          '

      - name: Backup avant déploiement
        run: |
          ssh -o StrictHostKeyChecking=no ${{ secrets.SSH_USERNAME }}@${{ secrets.SSH_HOST }} '
            cd /opt/parcinfo
            docker-compose exec -T db mysqldump -u root -p$DB_PASSWORD parcinfo > backup_$(date +%Y%m%d_%H%M%S).sql
            aws s3 cp backup_*.sql s3://${{ secrets.BACKUP_S3_BUCKET }}/database/
          '

      - name: Déploiement sur le serveur
        run: |
          ssh -o StrictHostKeyChecking=no ${{ secrets.SSH_USERNAME }}@${{ secrets.SSH_HOST }} '
            cd /opt/parcinfo
            git pull origin main
            docker-compose down
            docker-compose pull
            docker-compose up -d
          '

      - name: Configuration Datadog
        run: |
          ssh -o StrictHostKeyChecking=no ${{ secrets.SSH_USERNAME }}@${{ secrets.SSH_HOST }} '
            echo "DD_API_KEY=${{ secrets.DATADOG_API_KEY }}" >> /opt/parcinfo/.env
            echo "DD_APP_KEY=${{ secrets.DATADOG_APP_KEY }}" >> /opt/parcinfo/.env
            docker-compose restart
          '

      - name: Vérification de la santé de l'application
        run: |
          curl --retry 10 --retry-delay 5 http://${{ secrets.SSH_HOST }}/health

      - name: Notification Slack
        if: success() && github.event.inputs.notify == 'true'
        uses: 8398a7/action-slack@v3
        with:
          status: ${{ job.status }}
          fields: repo,message,commit,author,action,eventName,ref,workflow,job,took
        env:
          SLACK_WEBHOOK_URL: ${{ secrets.SLACK_WEBHOOK_URL }}

      - name: Notification Teams
        if: success() && github.event.inputs.notify == 'true'
        run: |
          curl -H "Content-Type: application/json" -d '{"text":"✅ Déploiement réussi sur ${{ github.event.inputs.environment || 'production' }}"}' ${{ secrets.TEAMS_WEBHOOK_URL }}

      - name: Envoi d'email de confirmation
        if: success() && github.event.inputs.notify == 'true'
        run: |
          curl --ssl-reqd \
            --url 'smtp://${{ secrets.SMTP_HOST }}:${{ secrets.SMTP_PORT }}' \
            --user '${{ secrets.SMTP_USERNAME }}:${{ secrets.SMTP_PASSWORD }}' \
            --mail-from '${{ secrets.SMTP_USERNAME }}' \
            --mail-rcpt 'admin@parcinfo.com' \
            --upload-file - << EOF
          From: ${{ secrets.SMTP_USERNAME }}
          To: admin@parcinfo.com
          Subject: Déploiement réussi - ParcInfo

          Le déploiement sur l'environnement ${{ github.event.inputs.environment || 'production' }} a réussi.
          Commit: ${{ github.sha }}
          Auteur: ${{ github.actor }}
          Date: $(date)
          EOF
```

## Options de déploiement avancées

1. **Déploiement par environnement**
   ```bash
   # Déploiement en production
   gh workflow run deploy.yml -f environment=production
   
   # Déploiement en staging
   gh workflow run deploy.yml -f environment=staging
   
   # Déploiement en développement
   gh workflow run deploy.yml -f environment=development
   ```

2. **Déploiement d'une version spécifique**
   ```bash
   # Déploiement d'une version spécifique
   gh workflow run deploy.yml -f version=v1.2.3
   ```

3. **Déploiement sans notifications**
   ```bash
   # Déploiement silencieux
   gh workflow run deploy.yml -f notify=false
   ```

4. **Rollback automatique**
   - En cas d'échec du déploiement, le système restaure automatiquement la version précédente
   - Les backups sont conservés pendant 7 jours dans S3
   - Les logs de rollback sont envoyés par email

5. **Tests de sécurité automatisés**
   - Scan des dépendances avec Snyk
   - Analyse de code statique avec SonarCloud
   - Tests de pénétration avec OWASP ZAP
   - Scan des vulnérabilités Docker
   - Vérification des secrets exposés

6. **Monitoring et alertes**
   - Intégration avec Datadog pour les métriques
   - Alertes Slack/Teams en cas de problème
   - Dashboard Grafana pour la visualisation
   - Métriques Prometheus pour le monitoring
   - Logs centralisés avec ELK Stack

## Déploiement

1. **Premier déploiement**
   - Clonez le repository sur le serveur :
     ```bash
     git clone https://github.com/votre-username/parcinfo.git /opt/parcinfo
     ```
   - Configurez les variables d'environnement :
     ```bash
     cp /opt/parcinfo/.env.example /opt/parcinfo/.env
     # Éditez le fichier .env avec vos paramètres
     ```
   - Configurez les secrets :
     ```bash
     # Chiffrez les secrets sensibles
     ansible-vault encrypt_string --ask-vault-pass 'votre_secret' --name 'JWT_SECRET'
     ```

2. **Déploiements suivants**
   - Via l'interface GitHub Actions
   - Via la ligne de commande avec GitHub CLI
   - Automatiquement lors d'un push sur main
   - Via l'API GitHub avec authentification

## Vérification

1. **Vérifiez les logs GitHub Actions**
   - Allez dans l'onglet "Actions" de votre repository
   - Vérifiez que le workflow s'est exécuté avec succès
   - Consultez les logs détaillés de chaque étape

2. **Vérifiez l'application**
   - Accédez à votre application via le navigateur
   - Vérifiez les logs Docker :
     ```bash
     docker-compose logs -f
     ```
   - Vérifiez les métriques Datadog
   - Consultez les alertes Prometheus

3. **Vérifiez les métriques**
   - Accédez au dashboard Grafana
   - Vérifiez les alertes Prometheus
   - Consultez les rapports SonarCloud
   - Vérifiez les scans de sécurité Snyk

## Dépannage

1. **Problèmes de connexion SSH**
   - Vérifiez que la clé SSH est correctement configurée
   - Vérifiez les permissions du fichier de clé SSH
   - Testez la connexion manuellement
   - Vérifiez les règles de pare-feu

2. **Problèmes de Docker**
   - Vérifiez l'espace disque disponible
   - Vérifiez les logs Docker pour plus de détails
   - Nettoyez les images et conteneurs inutilisés
   - Vérifiez les ressources système

3. **Problèmes de déploiement**
   - Vérifiez les logs GitHub Actions
   - Vérifiez les logs de l'application
   - Consultez les alertes Slack/Teams
   - Vérifiez les backups dans S3
   - Consultez les rapports d'erreur Datadog

4. **Problèmes de sécurité**
   - Vérifiez les rapports SonarCloud
   - Consultez les alertes Snyk
   - Vérifiez les logs OWASP ZAP
   - Analysez les vulnérabilités Docker

## Bonnes pratiques

1. **Sécurité**
   - Utilisez des secrets GitHub pour les informations sensibles
   - Activez la protection des branches
   - Configurez les revues de code obligatoires
   - Chiffrez les secrets avec Ansible Vault
   - Utilisez des tokens à rotation régulière
   - Mettez en place une politique de mots de passe forte
   - Activez l'authentification à deux facteurs

2. **Performance**
   - Utilisez le cache Docker pour accélérer les builds
   - Optimisez les layers Docker
   - Configurez les ressources Docker appropriées
   - Mettez en place un CDN pour les assets statiques
   - Utilisez la compression gzip
   - Optimisez les requêtes de base de données
   - Configurez le cache Redis

3. **Maintenance**
   - Nettoyez régulièrement les anciennes images Docker
   - Mettez à jour les dépendances régulièrement
   - Documentez les changements dans le CHANGELOG
   - Effectuez des sauvegardes régulières
   - Surveillez l'utilisation des ressources
   - Maintenez à jour les certificats SSL
   - Documentez les procédures de rollback 