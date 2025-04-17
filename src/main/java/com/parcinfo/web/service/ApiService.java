package com.parcinfo.web.service;

import com.parcinfo.web.model.Appareil;
import com.parcinfo.web.model.InterventionObjetNomade;
import com.parcinfo.web.model.ObjetNomade;
import com.parcinfo.web.model.Personne;
import com.parcinfo.web.model.Peripherique;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiService {
    @Value("${api.base-url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Methods for Personne
    public List<Personne> getAllPersonnes() {
        return restTemplate.exchange(
                apiBaseUrl + "/personnes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Personne>>() {}
        ).getBody();
    }

    public Personne getPersonneById(Long id) {
        return restTemplate.getForObject(apiBaseUrl + "/personnes/" + id, Personne.class);
    }

    public Personne createPersonne(Personne personne) {
        return restTemplate.postForObject(apiBaseUrl + "/personnes", personne, Personne.class);
    }

    public Personne updatePersonne(Long id, Personne personne) {
        restTemplate.put(apiBaseUrl + "/personnes/" + id, personne);
        return personne;
    }

    public void deletePersonne(Long id) {
        restTemplate.delete(apiBaseUrl + "/personnes/" + id);
    }

    // Methods for Appareil
    public List<Appareil> getAllAppareils() {
        return restTemplate.exchange(
                apiBaseUrl + "/appareils",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Appareil>>() {}
        ).getBody();
    }

    public Appareil getAppareilById(Long id) {
        return restTemplate.getForObject(apiBaseUrl + "/appareils/" + id, Appareil.class);
    }

    public Appareil createAppareil(Appareil appareil) {
        return restTemplate.postForObject(apiBaseUrl + "/appareils", appareil, Appareil.class);
    }

    public Appareil updateAppareil(Long id, Appareil appareil) {
        restTemplate.put(apiBaseUrl + "/appareils/" + id, appareil);
        return appareil;
    }

    public void deleteAppareil(Long id) {
        restTemplate.delete(apiBaseUrl + "/appareils/" + id);
    }

    // Methods for ObjetNomade
    public List<ObjetNomade> getAllObjetsNomades() {
        return restTemplate.exchange(
                apiBaseUrl + "/objets-nomades",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ObjetNomade>>() {}
        ).getBody();
    }

    public ObjetNomade getObjetNomadeById(Long id) {
        return restTemplate.getForObject(apiBaseUrl + "/objets-nomades/" + id, ObjetNomade.class);
    }

    public ObjetNomade createObjetNomade(ObjetNomade objetNomade) {
        return restTemplate.postForObject(apiBaseUrl + "/objets-nomades", objetNomade, ObjetNomade.class);
    }

    public ObjetNomade updateObjetNomade(Long id, ObjetNomade objetNomade) {
        restTemplate.put(apiBaseUrl + "/objets-nomades/" + id, objetNomade);
        return objetNomade;
    }

    public void deleteObjetNomade(Long id) {
        restTemplate.delete(apiBaseUrl + "/objets-nomades/" + id);
    }

    // Methods for Peripherique
    public List<Peripherique> getAllPeripheriques() {
        return restTemplate.exchange(
                apiBaseUrl + "/peripheriques",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Peripherique>>() {}
        ).getBody();
    }

    public Peripherique getPeripheriqueById(Long id) {
        return restTemplate.getForObject(apiBaseUrl + "/peripheriques/" + id, Peripherique.class);
    }

    public Peripherique createPeripherique(Peripherique peripherique) {
        return restTemplate.postForObject(apiBaseUrl + "/peripheriques", peripherique, Peripherique.class);
    }

    public Peripherique updatePeripherique(Long id, Peripherique peripherique) {
        restTemplate.put(apiBaseUrl + "/peripheriques/" + id, peripherique);
        return peripherique;
    }

    public void deletePeripherique(Long id) {
        restTemplate.delete(apiBaseUrl + "/peripheriques/" + id);
    }

    // Methods for InterventionObjetNomade
    public List<InterventionObjetNomade> getInterventionsByObjetNomadeId(Long objetNomadeId) {
        return restTemplate.exchange(
                apiBaseUrl + "/objets-nomades/" + objetNomadeId + "/interventions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InterventionObjetNomade>>() {}
        ).getBody();
    }

    public InterventionObjetNomade getInterventionObjetNomadeById(Long id) {
        return restTemplate.getForObject(apiBaseUrl + "/interventions-objets-nomades/" + id, InterventionObjetNomade.class);
    }

    public InterventionObjetNomade createInterventionObjetNomade(InterventionObjetNomade intervention) {
        return restTemplate.postForObject(apiBaseUrl + "/interventions-objets-nomades", intervention, InterventionObjetNomade.class);
    }

    public InterventionObjetNomade updateInterventionObjetNomade(Long id, InterventionObjetNomade intervention) {
        restTemplate.put(apiBaseUrl + "/interventions-objets-nomades/" + id, intervention);
        return intervention;
    }

    public void deleteInterventionObjetNomade(Long id) {
        restTemplate.delete(apiBaseUrl + "/interventions-objets-nomades/" + id);
    }
} 