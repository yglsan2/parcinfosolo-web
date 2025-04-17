-- Insertion des rôles
INSERT INTO role (name) VALUES 
('USER'),
('ADMIN')
ON DUPLICATE KEY UPDATE name = VALUES(name);

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
('Imprimante thermique')
ON DUPLICATE KEY UPDATE libelle = VALUES(libelle);

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
('Clé USB')
ON DUPLICATE KEY UPDATE libelle = VALUES(libelle); 