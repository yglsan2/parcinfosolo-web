package com.parfinfo;

import com.parfinfo.controller.api.*;
import com.parfinfo.dto.auth.LoginRequest;
import com.parfinfo.dto.auth.RegisterRequest;
import com.parfinfo.dto.peripherique.*;
import com.parfinfo.dto.personne.*;
import com.parfinfo.dto.role.RoleDTO;
import com.parfinfo.dto.permission.PermissionDTO;
import com.parfinfo.entity.*;
import com.parfinfo.repository.*;
import com.parfinfo.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IntegrationTest {

    // Repositories
    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private PermissionRepository permissionRepository;
    @Mock private PeripheriqueRepository peripheriqueRepository;
    @Mock private PersonneRepository personneRepository;
    @Mock private AppareilRepository appareilRepository;
    @Mock private ObjetNomadeRepository objetNomadeRepository;
    @Mock private OrdinateurRepository ordinateurRepository;
    @Mock private NotificationRepository notificationRepository;

    // Services
    @Mock private AuthService authService;
    @Mock private RoleService roleService;
    @Mock private PermissionService permissionService;
    @Mock private PeripheriqueService peripheriqueService;
    @Mock private PersonneService personneService;
    @Mock private AppareilService appareilService;
    @Mock private ObjetNomadeService objetNomadeService;
    @Mock private OrdinateurService ordinateurService;
    @Mock private NotificationService notificationService;

    // Controllers
    @Mock private AuthController authController;
    @Mock private RoleController roleController;
    @Mock private PermissionController permissionController;
    @Mock private PeripheriqueApiController peripheriqueController;
    @Mock private PersonneApiController personneController;
    @Mock private AppareilApiController appareilController;
    @Mock private ObjetNomadeController objetNomadeController;
    @Mock private OrdinateurController ordinateurController;
    @Mock private NotificationController notificationController;

    // Autres dépendances
    @Mock private PasswordEncoder passwordEncoder;

    // Entités de test
    private User user;
    private Role role;
    private Permission permission;
    private Peripherique peripherique;
    private Personne personne;
    private Appareil appareil;
    private ObjetNomade objetNomade;
    private Ordinateur ordinateur;
    private Notification notification;

    // DTOs de test
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private RoleDTO roleDTO;
    private PermissionDTO permissionDTO;
    private PeripheriqueRequest peripheriqueRequest;
    private PeripheriqueResponse peripheriqueResponse;
    private PersonneRequest personneRequest;
    private PersonneResponse personneResponse;
    private ObjetNomadeRequest objetNomadeRequest;
    private ObjetNomadeResponse objetNomadeResponse;
    private OrdinateurRequest ordinateurRequest;
    private OrdinateurResponse ordinateurResponse;

    @BeforeEach
    void setUp() {
        // Initialisation des entités
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setNom("Test");
        user.setPrenom("User");
        user.setEnabled(true);

        role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        role.setDescription("Administrator role");

        permission = new Permission();
        permission.setId(1L);
        permission.setName("READ");
        permission.setDescription("Read permission");

        peripherique = new Peripherique();
        peripherique.setId(1L);
        peripherique.setType("Clavier");
        peripherique.setMarque("Logitech");
        peripherique.setModele("K120");
        peripherique.setNumeroSerie("123456");
        peripherique.setStatut("Actif");

        personne = new Personne();
        personne.setId(1L);
        personne.setNom("Dupont");
        personne.setPrenom("Jean");
        personne.setEmail("jean.dupont@example.com");
        personne.setDepartement("IT");
        personne.setStatut("Actif");

        appareil = new Appareil();
        appareil.setId(1L);
        appareil.setType("Serveur");
        appareil.setMarque("Dell");
        appareil.setModele("PowerEdge");
        appareil.setNumeroSerie("789012");
        appareil.setStatut("En service");

        objetNomade = new ObjetNomade();
        objetNomade.setId(1L);
        objetNomade.setType("Smartphone");
        objetNomade.setMarque("Samsung");
        objetNomade.setModele("Galaxy S21");
        objetNomade.setNumeroSerie("345678");
        objetNomade.setStatut("En service");

        ordinateur = new Ordinateur();
        ordinateur.setId(1L);
        ordinateur.setType("Portable");
        ordinateur.setMarque("HP");
        ordinateur.setModele("ProBook");
        ordinateur.setNumeroSerie("901234");
        ordinateur.setStatut("En service");

        notification = new Notification();
        notification.setId(1L);
        notification.setType("ALERTE");
        notification.setMessage("Test notification");
        notification.setLu(false);

        // Initialisation des DTOs
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password");
        registerRequest.setNom("New");
        registerRequest.setPrenom("User");

        roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setName("ADMIN");
        roleDTO.setDescription("Administrator role");

        permissionDTO = new PermissionDTO();
        permissionDTO.setId(1L);
        permissionDTO.setName("READ");
        permissionDTO.setDescription("Read permission");

        peripheriqueRequest = new PeripheriqueRequest();
        peripheriqueRequest.setType("Clavier");
        peripheriqueRequest.setMarque("Logitech");
        peripheriqueRequest.setModele("K120");
        peripheriqueRequest.setNumeroSerie("123456");
        peripheriqueRequest.setStatut("Actif");
        peripheriqueRequest.setAppareilId(1L);

        peripheriqueResponse = new PeripheriqueResponse();
        peripheriqueResponse.setId(1L);
        peripheriqueResponse.setType("Clavier");
        peripheriqueResponse.setMarque("Logitech");
        peripheriqueResponse.setModele("K120");
        peripheriqueResponse.setNumeroSerie("123456");
        peripheriqueResponse.setStatut("Actif");

        personneRequest = new PersonneRequest();
        personneRequest.setNom("Dupont");
        personneRequest.setPrenom("Jean");
        personneRequest.setEmail("jean.dupont@example.com");
        personneRequest.setDepartement("IT");
        personneRequest.setStatut("Actif");

        personneResponse = new PersonneResponse();
        personneResponse.setId(1L);
        personneResponse.setNom("Dupont");
        personneResponse.setPrenom("Jean");
        personneResponse.setEmail("jean.dupont@example.com");
        personneResponse.setDepartement("IT");
        personneResponse.setStatut("Actif");

        objetNomadeRequest = new ObjetNomadeRequest();
        objetNomadeRequest.setType("Smartphone");
        objetNomadeRequest.setMarque("Samsung");
        objetNomadeRequest.setModele("Galaxy S21");
        objetNomadeRequest.setNumeroSerie("345678");
        objetNomadeRequest.setStatut("En service");
        objetNomadeRequest.setPersonneId(1L);

        objetNomadeResponse = new ObjetNomadeResponse();
        objetNomadeResponse.setId(1L);
        objetNomadeResponse.setType("Smartphone");
        objetNomadeResponse.setMarque("Samsung");
        objetNomadeResponse.setModele("Galaxy S21");
        objetNomadeResponse.setNumeroSerie("345678");
        objetNomadeResponse.setStatut("En service");

        ordinateurRequest = new OrdinateurRequest();
        ordinateurRequest.setType("Portable");
        ordinateurRequest.setMarque("HP");
        ordinateurRequest.setModele("ProBook");
        ordinateurRequest.setNumeroSerie("901234");
        ordinateurRequest.setStatut("En service");
        ordinateurRequest.setPersonneId(1L);

        ordinateurResponse = new OrdinateurResponse();
        ordinateurResponse.setId(1L);
        ordinateurResponse.setType("Portable");
        ordinateurResponse.setMarque("HP");
        ordinateurResponse.setModele("ProBook");
        ordinateurResponse.setNumeroSerie("901234");
        ordinateurResponse.setStatut("En service");
    }

    // Tests d'authentification
    @Test
    void testAuthFlow() {
        // Test de login
        when(authService.login(any(LoginRequest.class))).thenReturn("jwt-token");
        ResponseEntity<String> loginResponse = authController.login(loginRequest);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());

        // Test d'enregistrement
        when(authService.register(any(RegisterRequest.class))).thenReturn(user);
        ResponseEntity<User> registerResponse = authController.register(registerRequest);
        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        assertNotNull(registerResponse.getBody());
    }

    // Tests de gestion des rôles
    @Test
    void testRoleManagement() {
        // Test de création de rôle
        when(roleService.createRole(any(RoleDTO.class))).thenReturn(roleDTO);
        ResponseEntity<RoleDTO> createRoleResponse = roleController.createRole(roleDTO);
        assertEquals(HttpStatus.CREATED, createRoleResponse.getStatusCode());
        assertNotNull(createRoleResponse.getBody());

        // Test de récupération de rôle
        when(roleService.getRoleById(1L)).thenReturn(roleDTO);
        ResponseEntity<RoleDTO> getRoleResponse = roleController.getRoleById(1L);
        assertEquals(HttpStatus.OK, getRoleResponse.getStatusCode());
        assertNotNull(getRoleResponse.getBody());
    }

    // Tests de gestion des permissions
    @Test
    void testPermissionManagement() {
        // Test de création de permission
        when(permissionService.createPermission(any(PermissionDTO.class))).thenReturn(permissionDTO);
        ResponseEntity<PermissionDTO> createPermissionResponse = permissionController.createPermission(permissionDTO);
        assertEquals(HttpStatus.CREATED, createPermissionResponse.getStatusCode());
        assertNotNull(createPermissionResponse.getBody());

        // Test de récupération de permission
        when(permissionService.getPermissionById(1L)).thenReturn(permissionDTO);
        ResponseEntity<PermissionDTO> getPermissionResponse = permissionController.getPermissionById(1L);
        assertEquals(HttpStatus.OK, getPermissionResponse.getStatusCode());
        assertNotNull(getPermissionResponse.getBody());
    }

    // Tests de gestion des périphériques
    @Test
    void testPeripheriqueManagement() {
        // Test de création de périphérique
        when(peripheriqueService.createPeripherique(any(PeripheriqueRequest.class))).thenReturn(peripheriqueResponse);
        ResponseEntity<PeripheriqueResponse> createPeripheriqueResponse = peripheriqueController.createPeripherique(peripheriqueRequest);
        assertEquals(HttpStatus.CREATED, createPeripheriqueResponse.getStatusCode());
        assertNotNull(createPeripheriqueResponse.getBody());

        // Test de récupération de périphérique
        when(peripheriqueService.getPeripheriqueById(1L)).thenReturn(peripheriqueResponse);
        ResponseEntity<PeripheriqueResponse> getPeripheriqueResponse = peripheriqueController.getPeripheriqueById(1L);
        assertEquals(HttpStatus.OK, getPeripheriqueResponse.getStatusCode());
        assertNotNull(getPeripheriqueResponse.getBody());

        // Test de recherche de périphériques
        Page<PeripheriqueResponse> page = new PageImpl<>(Arrays.asList(peripheriqueResponse));
        when(peripheriqueService.searchPeripheriques(any(PeripheriqueSearchRequest.class), any(PageRequest.class))).thenReturn(page);
        ResponseEntity<Page<PeripheriqueResponse>> searchResponse = peripheriqueController.searchPeripheriques(
            new PeripheriqueSearchRequest(), PageRequest.of(0, 10)
        );
        assertEquals(HttpStatus.OK, searchResponse.getStatusCode());
        assertNotNull(searchResponse.getBody());
        assertEquals(1, searchResponse.getBody().getTotalElements());
    }

    // Tests de gestion des personnes
    @Test
    void testPersonneManagement() {
        // Test de création de personne
        when(personneService.createPersonne(any(PersonneRequest.class))).thenReturn(personneResponse);
        ResponseEntity<PersonneResponse> createPersonneResponse = personneController.createPersonne(personneRequest);
        assertEquals(HttpStatus.CREATED, createPersonneResponse.getStatusCode());
        assertNotNull(createPersonneResponse.getBody());

        // Test de récupération de personne
        when(personneService.getPersonneById(1L)).thenReturn(personneResponse);
        ResponseEntity<PersonneResponse> getPersonneResponse = personneController.getPersonneById(1L);
        assertEquals(HttpStatus.OK, getPersonneResponse.getStatusCode());
        assertNotNull(getPersonneResponse.getBody());
    }

    // Tests de gestion des appareils
    @Test
    void testAppareilManagement() {
        // Test de création d'appareil
        when(appareilService.createAppareil(any(AppareilRequest.class))).thenReturn(new AppareilResponse());
        ResponseEntity<AppareilResponse> createAppareilResponse = appareilController.createAppareil(new AppareilRequest());
        assertEquals(HttpStatus.CREATED, createAppareilResponse.getStatusCode());
        assertNotNull(createAppareilResponse.getBody());

        // Test de récupération d'appareil
        when(appareilService.getAppareilById(1L)).thenReturn(new AppareilResponse());
        ResponseEntity<AppareilResponse> getAppareilResponse = appareilController.getAppareilById(1L);
        assertEquals(HttpStatus.OK, getAppareilResponse.getStatusCode());
        assertNotNull(getAppareilResponse.getBody());
    }

    // Tests de gestion des objets nomades
    @Test
    void testObjetNomadeManagement() {
        // Test de création d'objet nomade
        when(objetNomadeService.createObjetNomade(any(ObjetNomadeRequest.class))).thenReturn(objetNomadeResponse);
        ResponseEntity<ObjetNomadeResponse> createObjetNomadeResponse = objetNomadeController.createObjetNomade(objetNomadeRequest);
        assertEquals(HttpStatus.CREATED, createObjetNomadeResponse.getStatusCode());
        assertNotNull(createObjetNomadeResponse.getBody());

        // Test de récupération d'objet nomade
        when(objetNomadeService.getObjetNomadeById(1L)).thenReturn(objetNomadeResponse);
        ResponseEntity<ObjetNomadeResponse> getObjetNomadeResponse = objetNomadeController.getObjetNomadeById(1L);
        assertEquals(HttpStatus.OK, getObjetNomadeResponse.getStatusCode());
        assertNotNull(getObjetNomadeResponse.getBody());
    }

    // Tests de gestion des ordinateurs
    @Test
    void testOrdinateurManagement() {
        // Test de création d'ordinateur
        when(ordinateurService.createOrdinateur(any(OrdinateurRequest.class))).thenReturn(ordinateurResponse);
        ResponseEntity<OrdinateurResponse> createOrdinateurResponse = ordinateurController.createOrdinateur(ordinateurRequest);
        assertEquals(HttpStatus.CREATED, createOrdinateurResponse.getStatusCode());
        assertNotNull(createOrdinateurResponse.getBody());

        // Test de récupération d'ordinateur
        when(ordinateurService.getOrdinateurById(1L)).thenReturn(ordinateurResponse);
        ResponseEntity<OrdinateurResponse> getOrdinateurResponse = ordinateurController.getOrdinateurById(1L);
        assertEquals(HttpStatus.OK, getOrdinateurResponse.getStatusCode());
        assertNotNull(getOrdinateurResponse.getBody());
    }

    // Tests de gestion des notifications
    @Test
    void testNotificationManagement() {
        // Test de création de notification
        when(notificationService.createNotification(any(NotificationRequest.class))).thenReturn(new NotificationResponse());
        ResponseEntity<NotificationResponse> createNotificationResponse = notificationController.createNotification(new NotificationRequest());
        assertEquals(HttpStatus.CREATED, createNotificationResponse.getStatusCode());
        assertNotNull(createNotificationResponse.getBody());

        // Test de récupération de notification
        when(notificationService.getNotificationById(1L)).thenReturn(new NotificationResponse());
        ResponseEntity<NotificationResponse> getNotificationResponse = notificationController.getNotificationById(1L);
        assertEquals(HttpStatus.OK, getNotificationResponse.getStatusCode());
        assertNotNull(getNotificationResponse.getBody());
    }
} 