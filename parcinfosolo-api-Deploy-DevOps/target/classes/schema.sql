-- Table des rôles
CREATE TABLE IF NOT EXISTS roles (
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
    date_naissance DATE NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
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
    id_personne BIGINT NOT NULL,
    id_appareil BIGINT NOT NULL,
    date_affectation DATE NOT NULL,
    FOREIGN KEY (id_personne) REFERENCES personne(id_personne),
    FOREIGN KEY (id_appareil) REFERENCES appareil(id_appareil)
);

-- Table des parcs
CREATE TABLE IF NOT EXISTS parc (
    id_parc BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    adresse VARCHAR(255),
    code_postal VARCHAR(10),
    ville VARCHAR(100),
    pays VARCHAR(100),
    est_actif BOOLEAN DEFAULT TRUE
);

-- Table des types d'emplacements
CREATE TABLE IF NOT EXISTS type_emplacement (
    id_type_emplacement BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Table des emplacements
CREATE TABLE IF NOT EXISTS emplacement (
    id_emplacement BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    parc_id BIGINT NOT NULL,
    type_emplacement_id BIGINT NOT NULL,
    est_actif BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (parc_id) REFERENCES parc(id_parc),
    FOREIGN KEY (type_emplacement_id) REFERENCES type_emplacement(id_type_emplacement)
); 