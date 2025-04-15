package com.parfinfo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cookie_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookiePreferences {
    
    @Id
    private String userId;
    
    private boolean analytics;
    private boolean marketing;
    private boolean functional;
    private boolean necessary;
    private String lastUpdated;
} 