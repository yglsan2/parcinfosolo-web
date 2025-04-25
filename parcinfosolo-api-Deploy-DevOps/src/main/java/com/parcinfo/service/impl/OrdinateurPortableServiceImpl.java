package com.parcinfo.service.impl;

import com.parcinfo.model.OrdinateurPortable;
import com.parcinfo.repository.OrdinateurPortableRepository;
import com.parcinfo.service.OrdinateurPortableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdinateurPortableServiceImpl implements OrdinateurPortableService {

    @Autowired
    private OrdinateurPortableRepository ordinateurPortableRepository;

    @Override
    public List<OrdinateurPortable> findAll() {
        return ordinateurPortableRepository.findAll();
    }

    @Override
    public OrdinateurPortable findById(Long id) {
        return ordinateurPortableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordinateur portable non trouvé avec l'id : " + id));
    }

    @Override
    public OrdinateurPortable save(OrdinateurPortable ordinateur) {
        return ordinateurPortableRepository.save(ordinateur);
    }

    @Override
    public void deleteById(Long id) {
        ordinateurPortableRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return ordinateurPortableRepository.existsById(id);
    }
} 