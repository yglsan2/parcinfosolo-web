-- Suppression des données existantes
DELETE FROM personne;
DELETE FROM roles;

-- Insertion des rôles
INSERT INTO roles (name) VALUES ('USER'), ('ADMIN');

-- Insertion de l'utilisateur admin
INSERT INTO personne (firstname, lastname, email, password, role_id, date_naissance) 
VALUES ('Admin', 'Admin', 'admin@admin.com', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 
(SELECT id FROM roles WHERE name = 'ADMIN'), '1990-01-01');

-- Insertion des types d'objets nomades
DELETE FROM type_objet_nomade;
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
DELETE FROM type_peripherique;
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