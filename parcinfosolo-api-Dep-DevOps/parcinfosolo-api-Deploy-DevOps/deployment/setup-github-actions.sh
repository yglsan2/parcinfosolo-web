#!/bin/zsh

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Fonction pour afficher les messages
print_message() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Vérification des prérequis
check_prerequisites() {
    print_message "Vérification des prérequis..."
    
    # Vérifier si gh CLI est installé
    if ! command -v gh &> /dev/null; then
        print_error "GitHub CLI (gh) n'est pas installé"
        print_message "Installation de GitHub CLI..."
        brew install gh
    fi
    
    # Vérifier si jq est installé
    if ! command -v jq &> /dev/null; then
        print_message "Installation de jq..."
        brew install jq
    fi
    
    # Vérifier si openssl est installé
    if ! command -v openssl &> /dev/null; then
        print_message "Installation de openssl..."
        brew install openssl
    fi
}

# Configuration de GitHub CLI
setup_github_cli() {
    print_message "Configuration de GitHub CLI..."
    
    # Vérifier si déjà authentifié
    if ! gh auth status &> /dev/null; then
        print_message "Authentification GitHub requise..."
        gh auth login
    fi
}

# Génération des secrets
generate_secrets() {
    print_message "Génération des secrets..."
    
    # Générer JWT secret
    JWT_SECRET=$(openssl rand -base64 32)
    
    # Générer clé de chiffrement
    ENCRYPTION_KEY=$(openssl rand -base64 32)
    
    # Générer mot de passe Redis
    REDIS_PASSWORD=$(openssl rand -base64 24)
    
    # Générer mot de passe Elasticsearch
    ELASTICSEARCH_PASSWORD=$(openssl rand -base64 24)
    
    # Générer mot de passe Kibana
    KIBANA_PASSWORD=$(openssl rand -base64 24)
    
    # Générer mot de passe Logstash
    LOGSTASH_PASSWORD=$(openssl rand -base64 24)
    
    # Générer clé API Datadog
    DATADOG_API_KEY=$(openssl rand -hex 32)
    
    # Générer clé d'application Datadog
    DATADOG_APP_KEY=$(openssl rand -hex 32)
    
    # Générer clé API Grafana
    GRAFANA_API_KEY=$(openssl rand -hex 32)
    
    # Générer clé API Prometheus
    PROMETHEUS_API_KEY=$(openssl rand -hex 32)
}

# Configuration des secrets GitHub
setup_github_secrets() {
    print_message "Configuration des secrets GitHub..."
    
    # Demander les informations nécessaires
    read -p "Nom du repository GitHub (format: owner/repo): " REPO
    read -p "Nom d'utilisateur SSH: " SSH_USERNAME
    read -p "Adresse IP ou nom de domaine du serveur: " SSH_HOST
    read -p "Nom d'utilisateur Docker: " DOCKER_USERNAME
    read -p "Mot de passe Docker: " DOCKER_PASSWORD
    read -p "URL du webhook Slack: " SLACK_WEBHOOK_URL
    read -p "URL du webhook Teams: " TEAMS_WEBHOOK_URL
    read -p "Token SonarCloud: " SONAR_TOKEN
    read -p "Token Snyk: " SNYK_TOKEN
    read -p "Nom du bucket S3 pour les backups: " BACKUP_S3_BUCKET
    read -p "Clé d'accès AWS: " AWS_ACCESS_KEY_ID
    read -p "Clé secrète AWS: " AWS_SECRET_ACCESS_KEY
    read -p "Serveur SMTP: " SMTP_HOST
    read -p "Port SMTP: " SMTP_PORT
    read -p "Nom d'utilisateur SMTP: " SMTP_USERNAME
    read -p "Mot de passe SMTP: " SMTP_PASSWORD
    
    # Créer le fichier temporaire des secrets
    cat > github_secrets.json << EOF
{
    "SSH_USERNAME": "$SSH_USERNAME",
    "SSH_HOST": "$SSH_HOST",
    "DOCKER_USERNAME": "$DOCKER_USERNAME",
    "DOCKER_PASSWORD": "$DOCKER_PASSWORD",
    "SLACK_WEBHOOK_URL": "$SLACK_WEBHOOK_URL",
    "TEAMS_WEBHOOK_URL": "$TEAMS_WEBHOOK_URL",
    "SONAR_TOKEN": "$SONAR_TOKEN",
    "SNYK_TOKEN": "$SNYK_TOKEN",
    "DATADOG_API_KEY": "$DATADOG_API_KEY",
    "DATADOG_APP_KEY": "$DATADOG_APP_KEY",
    "JWT_SECRET": "$JWT_SECRET",
    "ENCRYPTION_KEY": "$ENCRYPTION_KEY",
    "SMTP_HOST": "$SMTP_HOST",
    "SMTP_PORT": "$SMTP_PORT",
    "SMTP_USERNAME": "$SMTP_USERNAME",
    "SMTP_PASSWORD": "$SMTP_PASSWORD",
    "BACKUP_S3_BUCKET": "$BACKUP_S3_BUCKET",
    "AWS_ACCESS_KEY_ID": "$AWS_ACCESS_KEY_ID",
    "AWS_SECRET_ACCESS_KEY": "$AWS_SECRET_ACCESS_KEY",
    "GRAFANA_API_KEY": "$GRAFANA_API_KEY",
    "PROMETHEUS_API_KEY": "$PROMETHEUS_API_KEY",
    "REDIS_PASSWORD": "$REDIS_PASSWORD",
    "ELASTICSEARCH_PASSWORD": "$ELASTICSEARCH_PASSWORD",
    "KIBANA_PASSWORD": "$KIBANA_PASSWORD",
    "LOGSTASH_PASSWORD": "$LOGSTASH_PASSWORD"
}
EOF
    
    # Ajouter les secrets à GitHub
    print_message "Ajout des secrets à GitHub..."
    while IFS='=' read -r key value; do
        if [[ $key != "" && $value != "" ]]; then
            print_message "Ajout du secret: $key"
            gh secret set "$key" -b"$value" -R "$REPO"
        fi
    done < <(jq -r 'to_entries | .[] | "\(.key)=\(.value)"' github_secrets.json)
    
    # Nettoyer le fichier temporaire
    rm github_secrets.json
}

# Configuration du workflow GitHub Actions
setup_github_workflow() {
    print_message "Configuration du workflow GitHub Actions..."
    
    # Créer le répertoire .github/workflows s'il n'existe pas
    mkdir -p .github/workflows
    
    # Créer le fichier de workflow
    cat > .github/workflows/deploy.yml << 'EOF'
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
EOF
    
    print_success "Workflow GitHub Actions créé avec succès"
}

# Configuration de la protection des branches
setup_branch_protection() {
    print_message "Configuration de la protection des branches..."
    
    # Activer la protection de la branche main
    gh api \
        --method PUT \
        -H "Accept: application/vnd.github.v3+json" \
        "/repos/$REPO/branches/main/protection" \
        -f required_status_checks='{"strict":true,"contexts":["security","deploy"]}' \
        -f enforce_admins=true \
        -f required_pull_request_reviews='{"dismissal_restrictions":{},"dismiss_stale_reviews":true,"require_code_owner_reviews":true,"required_approving_review_count":1}' \
        -f restrictions=null
}

# Fonction principale
main() {
    print_message "Démarrage de la configuration du déploiement GitHub Actions..."
    
    # Vérifier les prérequis
    check_prerequisites
    
    # Configurer GitHub CLI
    setup_github_cli
    
    # Générer les secrets
    generate_secrets
    
    # Configurer les secrets GitHub
    setup_github_secrets
    
    # Configurer le workflow GitHub Actions
    setup_github_workflow
    
    # Configurer la protection des branches
    setup_branch_protection
    
    print_success "Configuration terminée avec succès !"
    print_message "Vous pouvez maintenant déployer votre application avec GitHub Actions."
    print_message "Pour déployer manuellement, utilisez : gh workflow run deploy.yml"
}

# Exécuter le script
main 