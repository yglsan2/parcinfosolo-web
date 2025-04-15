// Attendre que le DOM soit complètement chargé
document.addEventListener('DOMContentLoaded', function() {
    // Initialiser les tooltips Bootstrap
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Initialiser les popovers Bootstrap
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // Gérer la fermeture automatique des alertes
    var alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Activer la validation des formulaires Bootstrap
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

    // Gérer le menu déroulant de la navbar sur mobile
    var navbarToggler = document.querySelector('.navbar-toggler');
    if (navbarToggler) {
        navbarToggler.addEventListener('click', function() {
            document.body.classList.toggle('navbar-open');
        });
    }

    // Fermer le menu déroulant lors du clic sur un lien
    var navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    navLinks.forEach(function(link) {
        link.addEventListener('click', function() {
            if (window.innerWidth < 992) {
                document.body.classList.remove('navbar-open');
            }
        });
    });

    // Ajouter une classe active au lien de navigation actuel
    var currentLocation = window.location.pathname;
    navLinks.forEach(function(link) {
        if (link.getAttribute('href') === currentLocation) {
            link.classList.add('active');
        }
    });

    // Fonction pour formater les dates
    window.formatDate = function(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString('fr-FR', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    };

    // Fonction pour formater les nombres
    window.formatNumber = function(number) {
        if (!number) return '0';
        return new Intl.NumberFormat('fr-FR').format(number);
    };

    // Fonction pour afficher les messages de confirmation
    window.showConfirmDialog = function(message, callback) {
        if (confirm(message)) {
            callback();
        }
    };

    // Gérer les formulaires de recherche
    var searchForms = document.querySelectorAll('.search-form');
    searchForms.forEach(function(form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            const searchInput = form.querySelector('input[type="search"]');
            if (searchInput.value.trim() === '') {
                searchInput.classList.add('is-invalid');
                return;
            }
            form.submit();
        });
    });
}); 