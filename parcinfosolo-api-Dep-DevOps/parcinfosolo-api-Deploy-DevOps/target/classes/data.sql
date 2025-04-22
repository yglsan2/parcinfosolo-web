-- Insertion des rôles
MERGE INTO role (name) KEY(name) VALUES ('USER'), ('ADMIN');

-- Insertion des types d'objets nomades
MERGE INTO type_objet_nomade (libelle) KEY(libelle) VALUES 
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
MERGE INTO type_peripherique (libelle) KEY(libelle) VALUES 
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