package com.parcinfo.service;

import com.parcinfo.model.Peripherique;
import com.parcinfo.repository.PeripheriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeripheriqueService {

    @Autowired
    private PeripheriqueRepository peripheriqueRepository;

    public List<Peripherique> findAll() {
        return peripheriqueRepository.findAll();
    }

    public Optional<Peripherique> findById(Long id) {
        return peripheriqueRepository.findById(id);
    }

    public Peripherique save(Peripherique peripherique) {
        return peripheriqueRepository.save(peripherique);
    }

    public void deleteById(Long id) {
        peripheriqueRepository.deleteById(id);
    }
} 