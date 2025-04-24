package com.parcinfo.web.service;

import com.parcinfo.model.Peripherique;
import com.parcinfo.repository.PeripheriqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeripheriqueService {

    private final PeripheriqueRepository peripheriqueRepository;

    public PeripheriqueService(PeripheriqueRepository peripheriqueRepository) {
        this.peripheriqueRepository = peripheriqueRepository;
    }

    public List<Peripherique> findAll() {
        return peripheriqueRepository.findAll();
    }

    public Optional<Peripherique> findById(Long id) {
        return peripheriqueRepository.findById(id);
    }

    public Peripherique save(Peripherique peripherique) {
        return peripheriqueRepository.save(peripherique);
    }

    public void deletePeripherique(Long id) {
        peripheriqueRepository.deleteById(id);
    }
} 