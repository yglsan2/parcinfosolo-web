#!/bin/bash

# Configuration
BACKUP_DIR="/opt/parcinfo/backups"
DATE=$(date +%Y%m%d_%H%M%S)
RETENTION_DAYS=7
MYSQL_CONTAINER="parcinfo-mysql"
MYSQL_USER="root"
MYSQL_PASSWORD="root"
MYSQL_DATABASE="parcinfo"

# Création du répertoire de backup
mkdir -p $BACKUP_DIR

# Backup de la base de données
echo "Backup de la base de données..."
docker exec $MYSQL_CONTAINER mysqldump -u$MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE | gzip > $BACKUP_DIR/database_$DATE.sql.gz

# Backup des fichiers de configuration
echo "Backup des fichiers de configuration..."
tar -czf $BACKUP_DIR/config_$DATE.tar.gz /opt/parcinfo/config

# Backup des certificats SSL
echo "Backup des certificats SSL..."
tar -czf $BACKUP_DIR/ssl_$DATE.tar.gz /opt/parcinfo/ssl

# Nettoyage des vieux backups
echo "Nettoyage des vieux backups..."
find $BACKUP_DIR -type f -mtime +$RETENTION_DAYS -delete

# Vérification de l'espace disque
DISK_USAGE=$(df -h $BACKUP_DIR | awk 'NR==2 {print $5}' | sed 's/%//')
if [ $DISK_USAGE -gt 90 ]; then
    echo "ATTENTION: L'espace disque est presque plein ($DISK_USAGE%)"
    # Notification Slack
    curl -X POST -H 'Content-type: application/json' \
        --data "{\"text\":\"⚠️ ATTENTION: L'espace disque pour les backups est presque plein ($DISK_USAGE%)\"}" \
        $SLACK_WEBHOOK_URL
fi

# Vérification de la taille des backups
BACKUP_SIZE=$(du -sh $BACKUP_DIR | cut -f1)
echo "Taille totale des backups: $BACKUP_SIZE"

# Log de fin
echo "Backup terminé avec succès" 