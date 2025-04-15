package com.parfinfo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.parfinfo.controller.*;
import com.parfinfo.service.*;

@Configuration
public class ControllerConfig {

    @Bean
    public UserController userController(UserService userService) {
        return new UserController(userService);
    }

    @Bean
    public AuthController authController(AuthService authService) {
        return new AuthController(authService);
    }

    @Bean
    public RoleController roleController(RoleService roleService) {
        return new RoleController(roleService);
    }

    @Bean
    public PermissionController permissionController(PermissionService permissionService) {
        return new PermissionController(permissionService);
    }

    @Bean
    public PersonneController personneController(PersonneService personneService) {
        return new PersonneController(personneService);
    }

    @Bean
    public StatistiqueController statistiqueController(StatistiqueService statistiqueService) {
        return new StatistiqueController(statistiqueService);
    }

    @Bean
    public AppareilController appareilController(AppareilService appareilService) {
        return new AppareilController(appareilService);
    }

    @Bean
    public PeripheriqueController peripheriqueController(PeripheriqueService peripheriqueService) {
        return new PeripheriqueController(peripheriqueService);
    }

    @Bean
    public OrdinateurController ordinateurController(OrdinateurService ordinateurService) {
        return new OrdinateurController(ordinateurService);
    }

    @Bean
    public ObjetNomadeController objetNomadeController(ObjetNomadeService objetNomadeService) {
        return new ObjetNomadeController(objetNomadeService);
    }

    @Bean
    public NotificationController notificationController(NotificationService notificationService) {
        return new NotificationController(notificationService);
    }

    @Bean
    public ExportController exportController(ExportService exportService) {
        return new ExportController(exportService);
    }

    @Bean
    public GeoController geoController(GeoService geoService) {
        return new GeoController(geoService);
    }

    @Bean
    public EnumerationController enumerationController(EnumerationService enumerationService) {
        return new EnumerationController(enumerationService);
    }

    @Bean
    public CookiePreferencesController cookiePreferencesController(CookiePreferencesService cookiePreferencesService) {
        return new CookiePreferencesController(cookiePreferencesService);
    }

    @Bean
    public ActiviteController activiteController(ActiviteService activiteService) {
        return new ActiviteController(activiteService);
    }
} 