#!/bin/bash

# Fonction pour envoyer une notification Discord
send_discord_notification() {
    local WEBHOOK_URL=$1
    local ENVIRONMENT=$2
    local STATUS=$3
    local BUILD_NUMBER=$4
    local COMMIT_MESSAGE=$5
    local DURATION=$6

    # Définir la couleur en fonction du statut
    local COLOR
    case $STATUS in
        "SUCCESS") COLOR="3066993" ;; # Vert
        "FAILURE") COLOR="15158332" ;; # Rouge
        "UNSTABLE") COLOR="16776960" ;; # Jaune
        *) COLOR="7506394" ;; # Bleu
    esac

    # Créer le message JSON avec un embed plus détaillé
    local JSON_DATA=$(cat <<EOF
{
    "embeds": [{
        "title": "ParcInfo - Déploiement ${ENVIRONMENT}",
        "description": "Build #${BUILD_NUMBER}",
        "color": ${COLOR},
        "fields": [
            {
                "name": "Statut",
                "value": "${STATUS}",
                "inline": true
            },
            {
                "name": "Durée",
                "value": "${DURATION}",
                "inline": true
            },
            {
                "name": "Commit",
                "value": "${COMMIT_MESSAGE}"
            },
            {
                "name": "Application",
                "value": "ParcInfo API",
                "inline": true
            },
            {
                "name": "Base de données",
                "value": "MySQL",
                "inline": true
            }
        ],
        "footer": {
            "text": "ParcInfo - Gestion de parc informatique"
        },
        "timestamp": "$(date -u +"%Y-%m-%dT%H:%M:%SZ")"
    }]
}
EOF
)

    # Envoyer la notification
    curl -H "Content-Type: application/json" \
         -X POST \
         -d "$JSON_DATA" \
         "$WEBHOOK_URL"

    # Vérifier si la requête a réussi
    if [ $? -eq 0 ]; then
        echo "Notification Discord envoyée avec succès"
    else
        echo "Erreur lors de l'envoi de la notification Discord"
        exit 1
    fi
}

# Vérifier les arguments requis
if [ "$#" -lt 4 ]; then
    echo "Usage: $0 <webhook_url> <environment> <status> <build_number> [commit_message] [duration]"
    exit 1
fi

# Appeler la fonction avec les arguments
send_discord_notification "$1" "$2" "$3" "$4" "${5:-No commit message}" "${6:-Duration unknown}" 