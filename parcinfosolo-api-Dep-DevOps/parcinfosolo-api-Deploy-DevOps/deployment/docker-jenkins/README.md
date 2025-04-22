# Déploiement avec Docker et Jenkins

## Prérequis
- Jenkins installé sur votre machine
- Docker installé sur votre machine
- Un compte GitHub

## Configuration

1. **Configuration de Jenkins**
   - Installez les plugins suivants dans Jenkins :
     - Docker Pipeline
     - Git
     - Credentials Plugin

2. **Configuration des credentials Jenkins**
   - Allez dans "Manage Jenkins" > "Manage Credentials"
   - Ajoutez les credentials suivants :
     - GitHub credentials
     - Docker credentials

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
        stage('Deploy') {
            steps {
                sh 'docker-compose up -d'
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

2. **Problèmes de Docker**
   - Vérifiez l'espace disque disponible
   - Vérifiez les logs Docker pour plus de détails

3. **Problèmes de déploiement**
   - Vérifiez les logs du pipeline Jenkins
   - Vérifiez les logs de l'application 