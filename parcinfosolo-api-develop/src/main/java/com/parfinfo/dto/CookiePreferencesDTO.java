package com.parfinfo.dto;

import com.parfinfo.model.CookiePreferences;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookiePreferencesDTO {
    
    private String userId;
    private boolean analytics;
    private boolean marketing;
    private boolean functional;
    private boolean necessary;
    private String lastUpdated;
    
    public static CookiePreferencesDTO fromEntity(CookiePreferences entity) {
        if (entity == null) {
            return null;
        }
        
        CookiePreferencesDTO dto = new CookiePreferencesDTO();
        dto.setUserId(entity.getUserId());
        dto.setAnalytics(entity.isAnalytics());
        dto.setMarketing(entity.isMarketing());
        dto.setFunctional(entity.isFunctional());
        dto.setNecessary(entity.isNecessary());
        dto.setLastUpdated(entity.getLastUpdated());
        
        return dto;
    }
    
    public CookiePreferences toEntity() {
        CookiePreferences entity = new CookiePreferences();
        entity.setUserId(this.userId);
        entity.setAnalytics(this.analytics);
        entity.setMarketing(this.marketing);
        entity.setFunctional(this.functional);
        entity.setNecessary(this.necessary);
        entity.setLastUpdated(this.lastUpdated);
        
        return entity;
    }
} 