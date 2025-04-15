package com.parfinfo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class EnumerationService {
    
    public List<String> getEnumerationValues(String enumType) {
        List<String> values = new ArrayList<>();
        
        switch (enumType.toLowerCase()) {
            case "status":
                values.add("ACTIVE");
                values.add("INACTIVE");
                values.add("PENDING");
                break;
            case "role":
                values.add("ADMIN");
                values.add("USER");
                values.add("GUEST");
                break;
            default:
                throw new IllegalArgumentException("Type d'énumération non supporté: " + enumType);
        }
        
        return values;
    }
} 