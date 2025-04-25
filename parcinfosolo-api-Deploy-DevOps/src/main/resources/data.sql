-- Suppression des données existantes dans l'ordre des dépendances
DELETE FROM affectation;
DELETE FROM peripherique;
DELETE FROM appareil;
DELETE FROM objets_nomades;
DELETE FROM emplacement;
DELETE FROM parc;
DELETE FROM type_emplacement;
DELETE FROM type_peripherique;
DELETE FROM type_objet_nomade;
DELETE FROM personne;
DELETE FROM roles;

-- Insertion des rôles
INSERT INTO roles (name) VALUES ('USER'), ('ADMIN');

-- Insertion de l'utilisateur admin
INSERT INTO personne (firstname, lastname, email, password, role_id, date_naissance) 
VALUES ('Admin', 'Admin', 'admin@admin.com', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 
(SELECT id FROM roles WHERE name = 'ADMIN'), '1990-01-01');

-- Insertion des types d'objets nomades
INSERT INTO type_objet_nomade (libelle) VALUES 
('Smartphone'),
('Tablette'),
('Laptop'),
('Montre connectée'),
('Lunettes connectées'),
('Lecteur MP3'),
('Lecteur MP4'),
('Console portable'),
('Caméra d''action'),
('Drone'),
('Enceinte nomade'),
('Caisse enregistreuse'),
('Terminal de paiement'),
('Scanner de code-barres'),
('Imprimante thermique');

-- Insertion des types de périphériques
INSERT INTO type_peripherique (libelle) VALUES 
('Clavier'),
('Souris'),
('Écran'),
('Imprimante'),
('Scanner'),
('Webcam'),
('Casque'),
('Enceintes'),
('Microphone'),
('Clé USB');

-- Insertion des types d'emplacements
INSERT INTO type_emplacement (libelle, description) VALUES
('Bureau', 'Espace de travail individuel'),
('Salle de réunion', 'Espace pour les réunions d''équipe'),
('Zone commune', 'Espace partagé pour le travail collaboratif'),
('Stockage', 'Zone de stockage du matériel'),
('Laboratoire', 'Espace technique et de test');

-- Insertion des parcs
INSERT INTO parc (nom, description, adresse, code_postal, ville, pays) VALUES
('Parc Principal', 'Parc informatique principal de l''entreprise', '123 Rue de la Tech', '75001', 'Paris', 'France'),
('Parc Secondaire', 'Parc informatique secondaire', '456 Avenue des Développeurs', '69001', 'Lyon', 'France');

-- Insertion des emplacements
INSERT INTO emplacement (nom, description, parc_id, type_emplacement_id) VALUES
('Bureau 101', 'Bureau individuel au premier étage', 
 (SELECT id_parc FROM parc WHERE nom = 'Parc Principal'),
 (SELECT id_type_emplacement FROM type_emplacement WHERE libelle = 'Bureau')),
('Salle Réunion A', 'Salle de réunion principale',
 (SELECT id_parc FROM parc WHERE nom = 'Parc Principal'),
 (SELECT id_type_emplacement FROM type_emplacement WHERE libelle = 'Salle de réunion')),
('Zone Stockage 1', 'Zone de stockage principale',
 (SELECT id_parc FROM parc WHERE nom = 'Parc Principal'),
 (SELECT id_type_emplacement FROM type_emplacement WHERE libelle = 'Stockage')); 