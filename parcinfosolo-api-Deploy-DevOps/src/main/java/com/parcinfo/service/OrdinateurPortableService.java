package com.parcinfo.service;

import com.parcinfo.model.OrdinateurPortable;
import java.util.List;

public interface OrdinateurPortableService {
    List<OrdinateurPortable> findAll();
    OrdinateurPortable findById(Long id);
    OrdinateurPortable save(OrdinateurPortable ordinateur);
    void deleteById(Long id);
    boolean existsById(Long id);
} 