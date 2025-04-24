# Déploiement avec Ansible et GitHub Actions 🚀

## Table des matières
- [Prérequis](#prérequis)
- [Configuration](#configuration)
- [Options de déploiement avancées](#options-de-déploiement-avancées)
- [Déploiement](#déploiement)
- [Vérification](#vérification)
- [Dépannage](#dépannage)
- [Bonnes pratiques](#bonnes-pratiques)
- [Méthodes DevOps Avancées avec Ansible ��️](#méthodes-devops-avancées-avec-ansible-)

## Prérequis
- Un compte GitHub
- Un serveur de production avec accès SSH
- Ansible installé sur votre machine locale
- Docker installé sur le serveur de production
- Une clé SSH pour l'authentification
- Un compte SonarCloud pour l'analyse de code
- Un compte Snyk pour la sécurité des dépendances
- Un compte Datadog pour le monitoring
- Un compte AWS pour les backups S3

## Configuration

1. **Configuration des secrets GitHub**
   - Allez dans les paramètres de votre repository GitHub
   - Section "Secrets and variables" > "Actions"
   - Ajoutez les secrets suivants :
     - `SSH_PRIVATE_KEY` : Votre clé SSH privée
     - `SSH_HOST` : L'adresse IP ou le nom de domaine de votre serveur
     - `SSH_USERNAME` : Le nom d'utilisateur pour la connexion SSH
     - `ANSIBLE_HOST_KEY_CHECKING` : "False"
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
     - `ANSIBLE_VAULT_PASSWORD` : Mot de passe pour Ansible Vault
     - `DB_PASSWORD` : Mot de passe de la base de données
     - `REDIS_PASSWORD` : Mot de passe Redis
     - `ELASTICSEARCH_PASSWORD` : Mot de passe Elasticsearch
     - `KIBANA_PASSWORD` : Mot de passe Kibana
     - `LOGSTASH_PASSWORD` : Mot de passe Logstash

2. **Configuration du workflow GitHub Actions**
   - Créez le fichier `.github/workflows/deploy.yml` avec le contenu suivant :

```yaml
name: Déploiement Ansible

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

      - name: Installation d'Ansible
        run: |
          sudo apt-get update
          sudo apt-get install -y ansible

      - name: Configuration Ansible Vault
        run: |
          echo "${{ secrets.ANSIBLE_VAULT_PASSWORD }}" > .vault_pass
          chmod 600 .vault_pass
          ansible-vault decrypt --vault-password-file .vault_pass ansible/group_vars/all/vault.yml

      - name: Vérification de la santé du serveur
        run: |
          ansible-playbook -i ansible/inventory.yml ansible/health-check.yml --vault-password-file .vault_pass

      - name: Backup avant déploiement
        run: |
          ansible-playbook -i ansible/inventory.yml ansible/backup.yml --vault-password-file .vault_pass

      - name: Déploiement avec Ansible
        run: |
          ansible-playbook -i ansible/inventory.yml ansible/deploy.yml --vault-password-file .vault_pass

      - name: Configuration Datadog
        run: |
          ansible-playbook -i ansible/inventory.yml ansible/configure-monitoring.yml --vault-password-file .vault_pass

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
          ansible-playbook -i ansible/inventory.yml ansible/send-notification.yml --vault-password-file .vault_pass -e "deploy_environment=${{ github.event.inputs.environment || 'production' }}"

      - name: Nettoyage
        if: always()
        run: |
          rm -f .vault_pass

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
   - En cas d'échec du déploiement, Ansible restaure automatiquement la version précédente
   - Les backups sont conservés pendant 7 jours dans S3
   - Les logs de rollback sont envoyés par email
   - Les métriques de rollback sont enregistrées dans Datadog

5. **Tests de sécurité automatisés**
   - Scan des dépendances avec Snyk
   - Analyse de code statique avec SonarCloud
   - Tests de pénétration avec OWASP ZAP
   - Scan des vulnérabilités Docker
   - Vérification des secrets exposés
   - Audit de sécurité avec Ansible

6. **Monitoring et alertes**
   - Intégration avec Datadog pour les métriques
   - Alertes Slack/Teams en cas de problème
   - Dashboard Grafana pour la visualisation
   - Métriques Prometheus pour le monitoring
   - Logs centralisés avec ELK Stack
   - Alertes personnalisées par environnement

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
     # Créez le fichier vault.yml
     ansible-vault create ansible/group_vars/all/vault.yml
     
     # Ajoutez les secrets
     ansible-vault edit ansible/group_vars/all/vault.yml
     
     # Exemple de contenu :
     vault_db_password: "votre_mot_de_passe_db"
     vault_redis_password: "votre_mot_de_passe_redis"
     vault_jwt_secret: "votre_secret_jwt"
     vault_encryption_key: "votre_cle_chiffrement"
     ```

2. **Déploiements suivants**
   - Via l'interface GitHub Actions
   - Via la ligne de commande avec GitHub CLI
   - Automatiquement lors d'un push sur main
   - Via l'API GitHub avec authentification
   - Via Ansible en local avec vault

## Vérification

1. **Vérifiez les logs GitHub Actions**
   - Allez dans l'onglet "Actions" de votre repository
   - Vérifiez que le workflow s'est exécuté avec succès
   - Consultez les logs détaillés de chaque étape
   - Vérifiez les logs Ansible

2. **Vérifiez l'application**
   - Accédez à votre application via le navigateur
   - Vérifiez les logs Docker :
     ```bash
     docker-compose logs -f
     ```
   - Vérifiez les métriques Datadog
   - Consultez les alertes Prometheus
   - Vérifiez les logs ELK

3. **Vérifiez les métriques**
   - Accédez au dashboard Grafana
   - Vérifiez les alertes Prometheus
   - Consultez les rapports SonarCloud
   - Vérifiez les scans de sécurité Snyk
   - Analysez les métriques Datadog

## Dépannage

1. **Problèmes de connexion SSH**
   - Vérifiez que la clé SSH est correctement configurée
   - Vérifiez les permissions du fichier de clé SSH
   - Testez la connexion manuellement
   - Vérifiez les règles de pare-feu
   - Consultez les logs SSH

2. **Problèmes avec Ansible**
   - Vérifiez la syntaxe des playbooks
   - Utilisez l'option `-vvv` pour plus de détails
   - Vérifiez les variables d'inventaire
   - Vérifiez les fichiers vault
   - Testez les playbooks en mode check
   - Consultez les logs Ansible

3. **Problèmes de Docker**
   - Vérifiez l'espace disque disponible
   - Vérifiez les logs Docker pour plus de détails
   - Nettoyez les images et conteneurs inutilisés
   - Vérifiez les ressources système
   - Testez les conteneurs individuellement
   - Vérifiez les réseaux Docker

4. **Problèmes de déploiement**
   - Vérifiez les logs GitHub Actions
   - Vérifiez les logs de l'application
   - Consultez les alertes Slack/Teams
   - Vérifiez les backups dans S3
   - Consultez les rapports d'erreur Datadog
   - Vérifiez les logs ELK
   - Testez le rollback manuellement

5. **Problèmes de sécurité**
   - Vérifiez les rapports SonarCloud
   - Consultez les alertes Snyk
   - Vérifiez les logs OWASP ZAP
   - Analysez les vulnérabilités Docker
   - Vérifiez les secrets exposés
   - Testez les permissions
   - Auditez les accès

## Bonnes pratiques

1. **Sécurité**
   - Utilisez des secrets GitHub pour les informations sensibles
   - Activez la protection des branches
   - Configurez les revues de code obligatoires
   - Chiffrez les secrets avec Ansible Vault
   - Utilisez des tokens à rotation régulière
   - Mettez en place une politique de mots de passe forte
   - Activez l'authentification à deux facteurs
   - Limitez les accès SSH
   - Utilisez des rôles Ansible sécurisés
   - Auditez régulièrement les accès

2. **Performance**
   - Utilisez le cache Docker pour accélérer les builds
   - Optimisez les layers Docker
   - Configurez les ressources Docker appropriées
   - Mettez en place un CDN pour les assets statiques
   - Utilisez la compression gzip
   - Optimisez les requêtes de base de données
   - Configurez le cache Redis
   - Utilisez des workers Ansible
   - Optimisez les playbooks
   - Mettez en cache les dépendances

3. **Maintenance**
   - Nettoyez régulièrement les anciennes images Docker
   - Mettez à jour les dépendances régulièrement
   - Documentez les changements dans le CHANGELOG
   - Effectuez des sauvegardes régulières
   - Surveillez l'utilisation des ressources
   - Maintenez à jour les certificats SSL
   - Documentez les procédures de rollback
   - Testez régulièrement les backups
   - Mettez à jour Ansible et ses modules
   - Maintenez à jour l'inventaire
   - Documentez les procédures de récupération 

## Méthodes DevOps Avancées avec Ansible 🛠️

### 1. Stratégies de Déploiement Avancées

1. **Blue-Green Deployment avec Ansible**
   ```yaml
   - name: Configuration Blue-Green
     hosts: all
     vars:
       active_env: "{{ 'blue' if active_env == 'green' else 'green' }}"
     tasks:
       - name: Détection de l'environnement actif
         shell: "docker ps | grep -q '{{ active_env }}' && echo '{{ active_env }}' || echo '{{ 'green' if active_env == 'blue' else 'blue' }}'"
         register: current_env
         
       - name: Déploiement sur l'environnement inactif
         include_tasks: deploy.yml
         vars:
           target_env: "{{ 'green' if current_env.stdout == 'blue' else 'blue' }}"
           
       - name: Tests de santé
         include_tasks: health_check.yml
         vars:
           target_env: "{{ target_env }}"
           
       - name: Bascule du trafic
         include_tasks: switch_traffic.yml
         when: health_check.healthy
   ```

2. **Canary Deployments**
   ```yaml
   - name: Canary Deployment
     hosts: all
     vars:
       canary_percentage: 10
     tasks:
       - name: Déploiement Canary
         include_tasks: deploy_canary.yml
         vars:
           target_hosts: "{{ groups['app'] | sample(canary_percentage) }}"
           
       - name: Monitoring Canary
         include_tasks: monitor_canary.yml
         register: canary_health
         
       - name: Rollback si nécessaire
         include_tasks: rollback.yml
         when: canary_health.failed
   ```

3. **Feature Flags avec Ansible**
   ```yaml
   - name: Gestion des Feature Flags
     hosts: all
     vars:
       feature_flags:
         new_ui: true
         beta_features: false
     tasks:
       - name: Mise à jour des Feature Flags
         template:
           src: feature_flags.yml.j2
           dest: /etc/app/feature_flags.yml
         notify: reload application
   ```

### 2. Optimisations de Performance

1. **Parallélisation Intelligente**
   ```yaml
   - name: Déploiement Parallèle
     hosts: all
     strategy: free
     serial: "{{ ansible_forks | default(10) }}"
     tasks:
       - name: Optimisation des ressources
         set_fact:
           ansible_ssh_pipelining: true
           ansible_ssh_args: "-o ControlMaster=auto -o ControlPersist=60s"
   ```

2. **Cache de Facts**
   ```yaml
   - name: Cache des Facts
     hosts: all
     gather_facts: yes
     fact_caching: jsonfile
     fact_caching_connection: /path/to/cache
     fact_caching_timeout: 86400
   ```

3. **Optimisation des Playbooks**
   ```yaml
   - name: Playbook Optimisé
     hosts: all
     vars:
       ansible_ssh_common_args: '-o StrictHostKeyChecking=no'
     tasks:
       - name: Vérification des prérequis
         include_tasks: preflight.yml
         tags: always
         
       - name: Déploiement conditionnel
         include_tasks: deploy.yml
         when: preflight_check.changed
   ```

### 3. Astuces Avancées

1. **Gestion des Secrets avec Vault et AWS KMS**
   ```yaml
   - name: Gestion des Secrets
     hosts: all
     vars:
       vault_password_file: .vault_pass
     tasks:
       - name: Récupération de la clé KMS
         shell: aws kms decrypt --ciphertext-blob fileb://encrypted.key --output text
         register: kms_key
         
       - name: Déchiffrement des secrets
         shell: "echo '{{ kms_key.stdout }}' > {{ vault_password_file }}"
         no_log: true
   ```

2. **Rollback Intelligent**
   ```yaml
   - name: Rollback Avancé
     hosts: all
     vars:
       backup_dir: /opt/backups
     tasks:
       - name: Vérification des points de restauration
         find:
           paths: "{{ backup_dir }}"
           patterns: "backup_*.tar.gz"
         register: backup_files
         
       - name: Sélection du dernier backup valide
         set_fact:
           restore_point: "{{ backup_files.files | sort(attribute='mtime') | last }}"
           
       - name: Restauration sélective
         include_tasks: restore.yml
         vars:
           backup_file: "{{ restore_point.path }}"
   ```

3. **Monitoring Avancé**
   ```yaml
   - name: Monitoring Proactif
     hosts: all
     tasks:
       - name: Collecte des métriques
         include_tasks: collect_metrics.yml
         register: metrics
         
       - name: Analyse des tendances
         include_tasks: analyze_trends.yml
         vars:
           metrics_data: "{{ metrics }}"
           
       - name: Alertes prédictives
         include_tasks: predictive_alerts.yml
         when: metrics.anomalies | length > 0
   ```

### 4. Tips DevOps Peu Connus

1. **Détection des Drifts de Configuration**
   ```yaml
   - name: Détection des Drifts
     hosts: all
     tasks:
       - name: Capture de l'état actuel
         shell: "systemctl show {{ item }} --property=ActiveState,SubState"
         loop: "{{ services }}"
         register: current_state
         
       - name: Comparaison avec l'état attendu
         include_tasks: compare_states.yml
         vars:
           expected_state: "{{ lookup('template', 'templates/expected_state.yml.j2') }}"
   ```

2. **Gestion des Dépendances Circulaires**
   ```yaml
   - name: Résolution des Dépendances
     hosts: all
     tasks:
       - name: Analyse des dépendances
         include_tasks: analyze_deps.yml
         register: deps_graph
         
       - name: Optimisation de l'ordre
         include_tasks: optimize_order.yml
         vars:
           dependency_graph: "{{ deps_graph }}"
   ```

3. **Tests de Résilience**
   ```yaml
   - name: Tests de Résilience
     hosts: all
     tasks:
       - name: Simulation de pannes
         include_tasks: chaos_testing.yml
         vars:
           failure_scenarios:
             - type: network
               duration: 30s
             - type: disk
               duration: 1m
             
       - name: Vérification de la récupération
         include_tasks: recovery_check.yml
         register: recovery_status
   ```

### 5. Bonnes Pratiques Avancées

1. **Gestion des Versions**
   ```yaml
   - name: Versioning Avancé
     hosts: all
     vars:
       version_schema: "{{ lookup('template', 'templates/version_schema.yml.j2') }}"
     tasks:
       - name: Validation de version
         include_tasks: validate_version.yml
         
       - name: Mise à jour des versions
         include_tasks: update_versions.yml
         when: version_check.changed
   ```

2. **Documentation Automatique**
   ```yaml
   - name: Génération de Documentation
     hosts: localhost
     tasks:
       - name: Extraction des variables
         include_tasks: extract_vars.yml
         
       - name: Génération de la doc
         template:
           src: documentation.yml.j2
           dest: docs/ansible_docs.yml
   ```

3. **Tests d'Intégration**
   ```yaml
   - name: Tests d'Intégration
     hosts: all
     tasks:
       - name: Préparation de l'environnement
         include_tasks: prepare_test_env.yml
         
       - name: Exécution des tests
         include_tasks: run_integration_tests.yml
         register: test_results
         
       - name: Rapport de tests
         include_tasks: generate_test_report.yml
         when: test_results.changed
   ```

### 6. Orchestration avec Docker Swarm

1. **Configuration du Swarm**
   ```yaml
   - name: Configuration Docker Swarm
     hosts: swarm_managers
     tasks:
       - name: Initialisation du Swarm
         shell: docker swarm init --advertise-addr {{ ansible_default_ipv4.address }}
         register: swarm_init
         when: not swarm_initialized.stat.exists
         
       - name: Récupération du token worker
         shell: docker swarm join-token worker -q
         register: worker_token
         
       - name: Configuration des workers
         hosts: swarm_workers
         tasks:
           - name: Rejoindre le Swarm
             shell: "docker swarm join --token {{ worker_token.stdout }} {{ swarm_manager_ip }}:2377"
             when: not swarm_joined.stat.exists
   ```

2. **Déploiement de Services**
   ```yaml
   - name: Déploiement Swarm
     hosts: swarm_managers
     tasks:
       - name: Création du réseau overlay
         shell: docker network create --driver overlay --attachable app_network
         when: not network_exists.stat.exists
         
       - name: Déploiement du stack
         shell: |
           docker stack deploy \
             --compose-file docker-compose.swarm.yml \
             --with-registry-auth \
             --prune \
             app_stack
         register: stack_deploy
         
       - name: Vérification des services
         shell: docker service ls --filter name=app_stack
         register: services
         until: services.rc == 0
         retries: 30
         delay: 10
   ```

3. **Mise à l'échelle et Rolling Updates**
   ```yaml
   - name: Gestion des Services Swarm
     hosts: swarm_managers
     tasks:
       - name: Mise à l'échelle des services
         shell: |
           docker service update \
             --replicas {{ desired_replicas }} \
             --update-parallelism {{ update_parallelism }} \
             --update-delay {{ update_delay }} \
             --update-failure-action rollback \
             app_stack_web
         
       - name: Configuration des contraintes
         shell: |
           docker service update \
             --constraint-add 'node.labels.zone=={{ zone }}' \
             --constraint-add 'node.role==worker' \
             app_stack_web
   ```

4. **Monitoring Swarm**
   ```yaml
   - name: Monitoring Swarm
     hosts: swarm_managers
     tasks:
       - name: Installation des outils de monitoring
         shell: |
           docker service create \
             --name prometheus \
             --network app_network \
             --mount type=volume,source=prometheus_data,target=/prometheus \
             prom/prometheus
             
       - name: Configuration des alertes
         template:
           src: prometheus/alerts.yml.j2
           dest: /etc/prometheus/alerts.yml
         notify: reload prometheus
   ```

### 7. Orchestration avec Kubernetes

1. **Configuration du Cluster**
   ```yaml
   - name: Configuration Kubernetes
     hosts: k8s_masters
     tasks:
       - name: Installation des prérequis
         include_tasks: k8s/prerequisites.yml
         
       - name: Initialisation du cluster
         shell: |
           kubeadm init \
             --pod-network-cidr=10.244.0.0/16 \
             --apiserver-advertise-address={{ ansible_default_ipv4.address }}
         register: kubeadm_init
         
       - name: Configuration de kubectl
         shell: |
           mkdir -p $HOME/.kube
           cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
           chown $(id -u):$(id -g) $HOME/.kube/config
   ```

2. **Déploiement d'Applications**
   ```yaml
   - name: Déploiement Kubernetes
     hosts: k8s_masters
     tasks:
       - name: Création des namespaces
         kubernetes.core.k8s:
           name: "{{ item }}"
           api_version: v1
           kind: Namespace
           state: present
         loop:
           - production
           - staging
           - monitoring
           
       - name: Déploiement des applications
         kubernetes.core.k8s:
           state: present
           src: "k8s/{{ item }}.yml"
         loop:
           - app-deployment
           - app-service
           - app-ingress
   ```

3. **Gestion des Secrets et ConfigMaps**
   ```yaml
   - name: Gestion des Secrets
     hosts: k8s_masters
     tasks:
       - name: Création des secrets
         kubernetes.core.k8s:
           state: present
           src: "{{ item }}"
           namespace: "{{ namespace }}"
         loop:
           - secrets/database.yml
           - secrets/redis.yml
           - secrets/jwt.yml
           
       - name: Mise à jour des ConfigMaps
         kubernetes.core.k8s:
           state: present
           src: "configmaps/{{ item }}.yml"
         loop:
           - app-config
           - nginx-config
   ```

4. **Autoscaling et HPA**
   ```yaml
   - name: Configuration Autoscaling
     hosts: k8s_masters
     tasks:
       - name: Création des HPA
         kubernetes.core.k8s:
           state: present
           src: "autoscaling/{{ item }}.yml"
         loop:
           - web-hpa
           - api-hpa
           
       - name: Configuration des métriques personnalisées
         kubernetes.core.k8s:
           state: present
           src: "monitoring/custom-metrics.yml"
   ```

5. **Monitoring et Logging**
   ```yaml
   - name: Stack de Monitoring
     hosts: k8s_masters
     tasks:
       - name: Déploiement Prometheus
         kubernetes.core.k8s:
           state: present
           src: "monitoring/prometheus.yml"
           
       - name: Déploiement Grafana
         kubernetes.core.k8s:
           state: present
           src: "monitoring/grafana.yml"
           
       - name: Configuration ELK Stack
         kubernetes.core.k8s:
           state: present
           src: "logging/{{ item }}.yml"
         loop:
           - elasticsearch
           - logstash
           - kibana
   ```

6. **Backup et Restauration**
   ```yaml
   - name: Gestion des Backups
     hosts: k8s_masters
     tasks:
       - name: Backup des ressources
         shell: |
           kubectl get all -o yaml --all-namespaces > backup_$(date +%Y%m%d).yml
           aws s3 cp backup_*.yml s3://{{ backup_bucket }}/kubernetes/
           
       - name: Backup des PV
         shell: |
           velero backup create {{ backup_name }} \
             --include-namespaces {{ namespaces }} \
             --storage-location {{ backup_location }}
   ```

7. **Sécurité Avancée**
   ```yaml
   - name: Sécurité Kubernetes
     hosts: k8s_masters
     tasks:
       - name: Configuration des Network Policies
         kubernetes.core.k8s:
           state: present
           src: "security/network-policies.yml"
           
       - name: Configuration des RBAC
         kubernetes.core.k8s:
           state: present
           src: "security/rbac.yml"
           
       - name: Activation de l'audit
         template:
           src: security/audit-policy.yml.j2
           dest: /etc/kubernetes/audit-policy.yml
         notify: restart kube-apiserver
   ``` 