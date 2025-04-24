#!/bin/bash

# Configuration
DISCORD_WEBHOOK_URL="${DISCORD_WEBHOOK_URL}"
APP_NAME="ParcInfo"
ENVIRONMENT="Production"
VERSION="${VERSION:-latest}"
DEPLOYER="${DEPLOYER:-$(whoami)}"
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')

# Couleurs pour le message
COLOR_SUCCESS="5763719"  # Vert
COLOR_ERROR="15548997"   # Rouge
COLOR_WARNING="16776960" # Orange

# Fonction pour envoyer la notification
send_notification() {
    local status=$1
    local message=$2
    local color=$3

    # Création du message JSON pour Discord
    json_message=$(cat <<EOF
{
    "embeds": [{
        "title": "🚀 Déploiement ${APP_NAME}",
        "description": "${message}",
        "color": ${color},
        "fields": [
            {
                "name": "Environnement",
                "value": "${ENVIRONMENT}",
                "inline": true
            },
            {
                "name": "Version",
                "value": "${VERSION}",
                "inline": true
            },
            {
                "name": "Déployé par",
                "value": "${DEPLOYER}",
                "inline": true
            },
            {
                "name": "Statut",
                "value": "${status}",
                "inline": true
            },
            {
                "name": "Date",
                "value": "${TIMESTAMP}",
                "inline": true
            }
        ]
    }]
}
EOF
)

    # Envoi de la notification
    curl -H "Content-Type: application/json" \
         -d "${json_message}" \
         "${DISCORD_WEBHOOK_URL}"

    if [ $? -eq 0 ]; then
        echo "Notification Discord envoyée avec succès"
    else
        echo "Erreur lors de l'envoi de la notification Discord"
        exit 1
    fi
}

# Vérification des arguments
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <status> <message>"
    echo "Status: success, error, warning"
    exit 1
fi

# Sélection de la couleur en fonction du statut
case "$1" in
    "success")
        color=$COLOR_SUCCESS
        ;;
    "error")
        color=$COLOR_ERROR
        ;;
    "warning")
        color=$COLOR_WARNING
        ;;
    *)
        echo "Statut invalide. Utilisez: success, error, warning"
        exit 1
        ;;
esac

# Envoi de la notification
send_notification "$1" "$2" "$color" 