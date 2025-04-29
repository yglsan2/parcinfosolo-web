package com.parcinfo.web.model;

/**
 * Énumération représentant les différents types d'interventions techniques possibles.
 * Les types d'interventions permettent de catégoriser les actions techniques effectuées sur les appareils.
 *
 * @author [Votre nom]
 * @version 1.0
 */
public enum TypeIntervention {
    /**
     * Intervention préventive planifiée régulièrement.
     */
    PREVENTIVE,

    /**
     * Intervention corrective suite à un problème.
     */
    CORRECTIVE,

    /**
     * Intervention d'urgence pour résoudre un problème critique.
     */
    URGENCE,

    /**
     * Intervention de maintenance programmée.
     */
    MAINTENANCE,

    /**
     * Intervention d'installation ou de mise en service.
     */
    INSTALLATION,

    /**
     * Intervention de mise à niveau ou d'amélioration.
     */
    MISE_A_NIVEAU,

    /**
     * Autre type d'intervention non listé.
     */
    AUTRE
} 