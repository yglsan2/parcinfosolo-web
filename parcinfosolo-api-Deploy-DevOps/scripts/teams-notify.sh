#!/bin/bash

# Configuration
TEAMS_WEBHOOK_URL="${TEAMS_WEBHOOK_URL}"
APP_NAME="ParcInfo"
ENVIRONMENT="Production"
VERSION="${VERSION:-latest}"
DEPLOYER="${DEPLOYER:-$(whoami)}"
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')

# Couleurs pour le message
COLOR_SUCCESS="00FF00"  # Vert
COLOR_ERROR="FF0000"    # Rouge
COLOR_WARNING="FFA500"  # Orange

# Fonction pour envoyer la notification
send_notification() {
    local status=$1
    local message=$2
    local color=$3

    # Création du message JSON pour Teams
    json_message=$(cat <<EOF
{
    "@type": "MessageCard",
    "@context": "http://schema.org/extensions",
    "themeColor": "${color}",
    "summary": "Déploiement ${APP_NAME}",
    "sections": [{
        "activityTitle": "🚀 Déploiement ${APP_NAME}",
        "activitySubtitle": "${TIMESTAMP}",
        "facts": [
            {
                "name": "Environnement",
                "value": "${ENVIRONMENT}"
            },
            {
                "name": "Version",
                "value": "${VERSION}"
            },
            {
                "name": "Déployé par",
                "value": "${DEPLOYER}"
            },
            {
                "name": "Statut",
                "value": "${status}"
            }
        ],
        "text": "${message}"
    }]
}
EOF
)

    # Envoi de la notification
    curl -H "Content-Type: application/json" \
         -d "${json_message}" \
         "${TEAMS_WEBHOOK_URL}"

    if [ $? -eq 0 ]; then
        echo "Notification envoyée avec succès"
    else
        echo "Erreur lors de l'envoi de la notification"
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