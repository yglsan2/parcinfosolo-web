-- Table des rôles
CREATE TABLE IF NOT EXISTS role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Table des personnes
CREATE TABLE IF NOT EXISTS personne (
    id_personne BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Table des types d'objets nomades
CREATE TABLE IF NOT EXISTS type_objet_nomade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

-- Table des objets nomades
CREATE TABLE IF NOT EXISTS objets_nomades (
    id_objet_nomade BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    numero_serie VARCHAR(100) NOT NULL UNIQUE,
    marque VARCHAR(100) NOT NULL,
    modele VARCHAR(100) NOT NULL,
    etat VARCHAR(50),
    date_acquisition DATETIME NOT NULL,
    date_mise_en_service DATETIME,
    date_derniere_maintenance DATETIME,
    systeme_exploitation VARCHAR(100),
    version_systeme VARCHAR(50),
    est_actif BOOLEAN DEFAULT TRUE,
    commentaire TEXT,
    personne_id BIGINT,
    FOREIGN KEY (personne_id) REFERENCES personne(id_personne)
);

-- Table des types de périphériques
CREATE TABLE IF NOT EXISTS type_peripherique (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

-- Table des appareils
CREATE TABLE IF NOT EXISTS appareil (
    id_appareil BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    marque VARCHAR(100) NOT NULL,
    modele VARCHAR(100) NOT NULL,
    numero_serie VARCHAR(100) NOT NULL UNIQUE,
    date_acquisition DATETIME NOT NULL,
    date_mise_en_service DATETIME,
    etat VARCHAR(50),
    commentaire TEXT,
    est_actif BOOLEAN DEFAULT TRUE
);

-- Table des périphériques
CREATE TABLE IF NOT EXISTS peripherique (
    id_peripherique BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    marque VARCHAR(100) NOT NULL,
    modele VARCHAR(100) NOT NULL,
    numero_serie VARCHAR(100) NOT NULL UNIQUE,
    date_acquisition DATETIME NOT NULL,
    date_mise_en_service DATETIME,
    etat VARCHAR(50),
    commentaire TEXT,
    est_actif BOOLEAN DEFAULT TRUE,
    ordinateur_id BIGINT,
    objet_nomade_id BIGINT,
    FOREIGN KEY (ordinateur_id) REFERENCES appareil(id_appareil),
    FOREIGN KEY (objet_nomade_id) REFERENCES objets_nomades(id_objet_nomade)
);

-- Table des affectations
CREATE TABLE IF NOT EXISTS affectation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    personne_id BIGINT NOT NULL,
    appareil_id BIGINT NOT NULL,
    date_affectation DATE NOT NULL,
    FOREIGN KEY (personne_id) REFERENCES personne(id_personne),
    FOREIGN KEY (appareil_id) REFERENCES appareil(id_appareil)
); 