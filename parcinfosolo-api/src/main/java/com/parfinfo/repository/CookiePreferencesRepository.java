package com.parfinfo.repository;

import com.parfinfo.model.CookiePreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookiePreferencesRepository extends JpaRepository<CookiePreferences, String> {
} 