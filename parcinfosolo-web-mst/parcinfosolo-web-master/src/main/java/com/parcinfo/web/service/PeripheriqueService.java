package com.parcinfo.web.service;

import com.parcinfo.web.model.Peripherique;
import com.parcinfo.web.repository.PeripheriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

    public void delete(Peripherique peripherique) {
        peripheriqueRepository.delete(peripherique);
    }

    public void deleteById(Long id) {
        peripheriqueRepository.deleteById(id);
    }
} 