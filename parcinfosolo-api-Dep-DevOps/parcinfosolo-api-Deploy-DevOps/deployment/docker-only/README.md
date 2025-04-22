# Déploiement avec Docker

## Prérequis
- Docker installé sur votre machine
- Docker Compose installé sur votre machine

## Configuration

1. **Configuration de Docker**
   - Assurez-vous que Docker est en cours d'exécution :
     ```bash
     sudo systemctl status docker
     ```
   - Si Docker n'est pas en cours d'exécution, démarrez-le :
     ```bash
     sudo systemctl start docker
     ```

2. **Configuration de l'environnement**
   - Copiez le fichier `.env.example` vers `.env` :
     ```bash
     cp .env.example .env
     ```
   - Éditez le fichier `.env` avec vos paramètres

## Déploiement

1. **Construction des images Docker**
   - Exécutez la commande suivante pour construire les images :
     ```bash
     docker-compose build
     ```

2. **Démarrage des conteneurs**
   - Exécutez la commande suivante pour démarrer les conteneurs :
     ```bash
     docker-compose up -d
     ```

3. **Vérification des conteneurs**
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

1. **Problèmes de Docker**
   - Vérifiez l'espace disque disponible
   - Vérifiez les logs Docker pour plus de détails

2. **Problèmes de déploiement**
   - Vérifiez les logs de l'application
   - Vérifiez les logs Docker pour plus de détails 