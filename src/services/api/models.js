// Énumérations
export const TypeAppareil = {
    ORDINATEUR: 'ORDINATEUR',
    PERIPHERIQUE: 'PERIPHERIQUE',
    OBJET_NOMADE: 'OBJET_NOMADE',
    APPAREIL_STANDARD: 'APPAREIL_STANDARD'
};

export const EtatAppareil = {
    EN_SERVICE: 'EN_SERVICE',
    HORS_SERVICE: 'HORS_SERVICE',
    EN_REPARATION: 'EN_REPARATION'
};

export const TypeObjetNomade = {
    TELEPHONE: 'TELEPHONE',
    TABLETTE: 'TABLETTE',
    LAPTOP: 'LAPTOP'
};

// Interfaces de base
export interface Appareil {
    id?: number;
    nom: string;
    description: string;
    dateAchat: string;
    prix: number;
    typeAppareil: TypeAppareil;
    etatAppareil: EtatAppareil;
}

export interface Personne {
    id?: number;
    nom: string;
    prenom: string;
    email: string;
    telephone: string;
    appareils?: Appareil[];
}

// Interfaces spécifiques
export interface AppareilStandard extends Appareil {
    reference: string;
    marque: string;
}

export interface Ordinateur extends Appareil {
    processeur: string;
    ram: number;
    stockage: number;
    systemeExploitation: string;
}

export interface Peripherique extends Appareil {
    type: string;
    connectique: string;
}

export interface ObjetNomade extends Appareil {
    typeObjetNomade: TypeObjetNomade;
    taille: number;
    autonomie: number;
}
