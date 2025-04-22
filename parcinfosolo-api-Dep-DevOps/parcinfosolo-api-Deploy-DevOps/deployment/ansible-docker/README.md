# Déploiement avec Ansible et Docker

## Prérequis
- Un serveur de production avec accès SSH
- Ansible installé sur votre machine locale
- Docker installé sur le serveur de production
- Une clé SSH pour l'authentification

## Configuration

1. **Configuration d'Ansible**
   - Créez le fichier `deployment/ansible-docker/inventory.yml` :

```yaml
all:
  hosts:
    production:
      ansible_host: "votre_ip_serveur"
      ansible_user: "votre_utilisateur"
      ansible_ssh_private_key_file: "~/.ssh/id_rsa"
```

   - Créez le fichier `deployment/ansible-docker/deploy.yml` :

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

2. **Déploiement avec Ansible**
   - Exécutez la commande suivante depuis votre machine locale :
     ```bash
     ansible-playbook -i deployment/ansible-docker/inventory.yml deployment/ansible-docker/deploy.yml
     ```

## Vérification

1. **Vérifiez l'application**
   - Accédez à votre application via le navigateur
   - Vérifiez les logs Docker :
     ```bash
     docker-compose logs -f
     ```

## Dépannage

1. **Problèmes de connexion SSH**
   - Vérifiez que la clé SSH est correctement configurée
   - Vérifiez les permissions du fichier de clé SSH

2. **Problèmes d'Ansible**
   - Vérifiez les logs Ansible pour plus de détails
   - Vérifiez que tous les prérequis sont installés

3. **Problèmes de Docker**
   - Vérifiez l'espace disque disponible
   - Vérifiez les logs Docker pour plus de détails

4. **Problèmes de déploiement**
   - Vérifiez les logs Ansible
   - Vérifiez les logs de l'application 