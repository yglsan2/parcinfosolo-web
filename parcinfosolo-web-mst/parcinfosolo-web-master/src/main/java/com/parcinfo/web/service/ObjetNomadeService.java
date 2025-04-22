package com.parcinfo.web.service;

import com.parcinfo.web.model.ObjetNomade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjetNomadeService {

    @Autowired
    private ApiService apiService;

    public List<ObjetNomade> findAll() {
        return apiService.getAllObjetsNomades();
    }

    public ObjetNomade findById(Long id) {
        return apiService.getObjetNomadeById(id);
    }

    public ObjetNomade save(ObjetNomade objetNomade) {
        if (objetNomade.getIdObjetNomade() == null) {
            return apiService.createObjetNomade(objetNomade);
        } else {
            return apiService.updateObjetNomade(objetNomade.getIdObjetNomade(), objetNomade);
        }
    }

    public void deleteById(Long id) {
        apiService.deleteObjetNomade(id);
    }
} 