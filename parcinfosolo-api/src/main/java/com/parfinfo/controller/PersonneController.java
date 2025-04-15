package com.parfinfo.controller;

import com.parfinfo.dto.personne.PersonneDTO;
import com.parfinfo.service.PersonneService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class PersonneController {

    private final PersonneService personneService;

    public List<PersonneDTO> getAllPersonnes() {
        return personneService.findAll();
    }

    public PersonneDTO getPersonne(Long id) {
        return personneService.findById(id);
    }

    public PersonneDTO createPersonne(PersonneDTO personneDTO) {
        return personneService.save(personneDTO);
    }

    public PersonneDTO updatePersonne(Long id, PersonneDTO personneDTO) {
        return personneService.update(id, personneDTO);
    }

    public void deletePersonne(Long id) {
        personneService.delete(id);
    }

    public List<PersonneDTO> searchPersonnes(String nom, String prenom, String email) {
        return personneService.search(nom, prenom, email);
    }

    public Long countPersonnes() {
        return personneService.count();
    }
} 