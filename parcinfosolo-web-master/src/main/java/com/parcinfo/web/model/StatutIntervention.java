package com.parcinfo.web.model;

/**
 * Énumération représentant les différents statuts possibles d'une intervention.
 * Les statuts permettent de suivre l'état d'avancement d'une intervention technique.
 *
 * @author [Votre nom]
 * @version 1.0
 */
public enum StatutIntervention {
    /**
     * L'intervention est planifiée mais n'a pas encore commencé.
     */
    PLANIFIEE,

    /**
     * L'intervention est en cours d'exécution.
     */
    EN_COURS,

    /**
     * L'intervention a été suspendue temporairement.
     */
    SUSPENDUE,

    /**
     * L'intervention a été terminée avec succès.
     */
    TERMINEE,

    /**
     * L'intervention a été annulée.
     */
    ANNULEE,

    /**
     * L'intervention nécessite une attention particulière ou un suivi.
     */
    EN_ATTENTE
} 