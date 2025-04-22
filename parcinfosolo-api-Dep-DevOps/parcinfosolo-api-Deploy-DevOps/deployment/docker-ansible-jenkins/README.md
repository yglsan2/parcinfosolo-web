# Déploiement avec Docker, Ansible et Jenkins

## Prérequis
- Jenkins installé sur votre machine
- Docker installé sur votre machine
- Ansible installé sur votre machine
- Un compte GitHub

## Configuration

1. **Configuration de Jenkins**
   - Installez les plugins suivants dans Jenkins :
     - Docker Pipeline
     - Git
     - Credentials Plugin
     - Ansible Plugin

2. **Configuration des credentials Jenkins**
   - Allez dans "Manage Jenkins" > "Manage Credentials"
   - Ajoutez les credentials suivants :
     - GitHub credentials
     - Docker credentials
     - SSH credentials pour Ansible

3. **Configuration du pipeline Jenkins**
   - Créez un nouveau pipeline dans Jenkins
   - Configurez le pipeline pour utiliser le fichier `Jenkinsfile` :

```groovy
pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'parcinfo'
        DOCKER_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'docker-compose build'
            }
        }
        stage('Test') {
            steps {
                sh 'docker-compose run --rm api ./mvnw test'
            }
        }
        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook -i deployment/docker-ansible-jenkins/inventory.yml deployment/docker-ansible-jenkins/deploy.yml'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
```

4. **Configuration d'Ansible**
   - Créez le fichier `deployment/docker-ansible-jenkins/inventory.yml` :

```yaml
all:
  hosts:
    production:
      ansible_host: "votre_ip_serveur"
      ansible_user: "votre_utilisateur"
      ansible_ssh_private_key_file: "~/.ssh/id_rsa"
```

   - Créez le fichier `deployment/docker-ansible-jenkins/deploy.yml` :

```yaml
---
- name: Déploiement de ParcInfo
  hosts: production
  become: yes
  tasks:
    - name: Mise à jour des paquets
      apt:
        update_cache: yes
        cache_valid_time: 3600
      when: ansible_os_family == "Debian"

    - name: Installation des dépendances
      apt:
        name:
          - docker.io
          - docker-compose
        state: present
      when: ansible_os_family == "Debian"

    - name: Création des répertoires
      file:
        path: "{{ item }}"
        state: directory
        mode: '0755'
      with_items:
        - /opt/parcinfo
        - /opt/parcinfo/logs
        - /opt/parcinfo/config
        - /opt/parcinfo/ssl

    - name: Copie des fichiers de configuration
      copy:
        src: "{{ item.src }}"
        dest: "{{ item.dest }}"
        mode: '0644'
      with_items:
        - { src: '../../docker-compose.yml', dest: '/opt/parcinfo/docker-compose.yml' }
        - { src: '../../.env', dest: '/opt/parcinfo/.env' }

    - name: Déploiement des conteneurs
      docker_compose:
        project_src: "/opt/parcinfo"
        state: present
        pull: yes
```

## Déploiement

1. **Démarrage du pipeline Jenkins**
   - Cliquez sur "Build Now" dans Jenkins pour démarrer le pipeline

2. **Vérification du déploiement**
   - Vérifiez les logs du pipeline dans Jenkins
   - Vérifiez que les conteneurs sont en cours d'exécution :
     ```bash
     docker-compose ps
     ```

## Vérification

1. **Vérifiez l'application**
   - Accédez à votre application via le navigateur
   - Vérifiez les logs Docker :
     ```bash
     docker-compose logs -f
     ```

## Dépannage

1. **Problèmes de Jenkins**
   - Vérifiez les logs Jenkins pour plus de détails
   - Vérifiez que tous les plugins sont installés

2. **Problèmes d'Ansible**
   - Vérifiez les logs Ansible pour plus de détails
   - Vérifiez que tous les prérequis sont installés

3. **Problèmes de Docker**
   - Vérifiez l'espace disque disponible
   - Vérifiez les logs Docker pour plus de détails

4. **Problèmes de déploiement**
   - Vérifiez les logs du pipeline Jenkins
   - Vérifiez les logs de l'application 