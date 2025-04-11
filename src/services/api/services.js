import { ApiService } from './ApiService';
import {
    Appareil,
    AppareilStandard,
    Ordinateur,
    Peripherique,
    ObjetNomade,
    Personne
} from '../../interfaces/models';

// Services pour chaque type d'entité
export class PersonneService extends ApiService<Personne> {
    constructor() {
        super('/personnes');
    }
}

export class AppareilStandardService extends ApiService<AppareilStandard> {
    constructor() {
        super('/appareils-standard');
    }
}

export class OrdinateurService extends ApiService<Ordinateur> {
    constructor() {
        super('/ordinateurs');
    }
}

export class PeripheriqueService extends ApiService<Peripherique> {
    constructor() {
        super('/peripheriques');
    }
}

export class ObjetNomadeService extends ApiService<ObjetNomade> {
    constructor() {
        super('/objets-nomades');
    }
}

// Instances des services
export const personneService = new PersonneService();
export const appareilStandardService = new AppareilStandardService();
export const ordinateurService = new OrdinateurService();
export const peripheriqueService = new PeripheriqueService();
export const objetNomadeService = new ObjetNomadeService(); 