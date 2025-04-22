# Déploiement avec Vagrant

## Prérequis
- Vagrant installé sur votre machine
- VirtualBox installé sur votre machine

## Configuration

1. **Configuration de Vagrant**
   - Créez le fichier `deployment/vagrant/Vagrantfile` :

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"
  config.vm.network "private_network", ip: "192.168.56.10"
  config.vm.provider "virtualbox" do |vb|
    vb.memory = "2048"
    vb.cpus = 2
  end
  config.vm.provision "shell", inline: <<-SHELL
    apt-get update
    apt-get install -y docker.io docker-compose
    usermod -aG docker vagrant
  SHELL
end
```

## Déploiement

1. **Démarrage de la machine virtuelle**
   - Exécutez la commande suivante pour démarrer la machine virtuelle :
     ```bash
     cd deployment/vagrant
     vagrant up
     ```

2. **Connexion à la machine virtuelle**
   - Exécutez la commande suivante pour vous connecter à la machine virtuelle :
     ```bash
     vagrant ssh
     ```

3. **Configuration de l'environnement**
   - Copiez le fichier `.env.example` vers `.env` :
     ```bash
     cp /vagrant/.env.example /vagrant/.env
     ```
   - Éditez le fichier `.env` avec vos paramètres

4. **Construction des images Docker**
   - Exécutez la commande suivante pour construire les images :
     ```bash
     cd /vagrant
     docker-compose build
     ```

5. **Démarrage des conteneurs**
   - Exécutez la commande suivante pour démarrer les conteneurs :
     ```bash
     docker-compose up -d
     ```

## Vérification

1. **Vérifiez l'application**
   - Accédez à votre application via le navigateur à l'adresse `http://192.168.56.10`
   - Vérifiez les logs Docker :
     ```bash
     docker-compose logs -f
     ```

## Dépannage

1. **Problèmes de Vagrant**
   - Vérifiez que VirtualBox est en cours d'exécution
   - Vérifiez les logs Vagrant pour plus de détails

2. **Problèmes de Docker**
   - Vérifiez l'espace disque disponible
   - Vérifiez les logs Docker pour plus de détails

3. **Problèmes de déploiement**
   - Vérifiez les logs de l'application
   - Vérifiez les logs Docker pour plus de détails 