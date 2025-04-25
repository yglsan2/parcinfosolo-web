# Notifications Discord pour Jenkins

Ce dossier contient les fichiers nécessaires pour configurer les notifications Discord dans Jenkins pour l'application ParcInfo.

## Structure des fichiers

- `discord-webhooks.properties` : Configuration des webhooks Discord pour différents environnements
- `notify-discord.sh` : Script shell pour envoyer des notifications Discord
- `Jenkinsfile` : Pipeline Jenkins avec intégration des notifications Discord

## Configuration

### 1. Créer un Webhook Discord

1. Allez sur votre serveur Discord et ouvrez les paramètres du canal où vous souhaitez envoyer les messages
2. Allez dans la section "Intégrations" et sélectionnez "Webhooks"
3. Créez un nouveau Webhook et copiez l'URL du Webhook
4. Remplacez les URLs dans le fichier `discord-webhooks.properties` par vos URLs réelles

### 2. Configurer Jenkins

1. Dans Jenkins, allez dans "Manage Jenkins" > "Manage Credentials"
2. Ajoutez une nouvelle credential de type "Secret text"
3. Nommez-la "discord-webhook-url"
4. Collez l'URL de votre webhook Discord

### 3. Configurer les outils dans Jenkins

1. Dans Jenkins, allez dans "Manage Jenkins" > "Global Tool Configuration"
2. Configurez Maven et JDK21 si ce n'est pas déjà fait

## Utilisation

Pour utiliser ce pipeline :

1. Copiez le contenu du fichier `Jenkinsfile` dans votre pipeline Jenkins
2. Assurez-vous que le script `notify-discord.sh` est exécutable :
   ```
   chmod +x jenkinsDiscord/notify-discord.sh
   ```
3. Lancez le pipeline

## Personnalisation

Vous pouvez personnaliser les messages Discord en modifiant le script `notify-discord.sh`. Les paramètres disponibles sont :

- `WEBHOOK_URL` : URL du webhook Discord
- `ENVIRONMENT` : Environnement (PRODUCTION, STAGING, DEV)
- `STATUS` : Statut du build (SUCCESS, FAILURE, UNSTABLE)
- `BUILD_NUMBER` : Numéro du build
- `COMMIT_MESSAGE` : Message du commit
- `DURATION` : Durée du build

## Exemple de notification

Une notification Discord ressemblera à ceci :

![Exemple de notification Discord](https://i.imgur.com/example.png)

## Dépannage

Si les notifications ne fonctionnent pas :

1. Vérifiez que l'URL du webhook est correcte
2. Assurez-vous que le script est exécutable
3. Vérifiez les logs Jenkins pour plus de détails 