import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/" className="navbar-logo">
          ParcInfo
        </Link>
      </div>
      <div className="navbar-menu">
        <Link to="/personnes" className="navbar-item">
          Personnes
        </Link>
        <div className="navbar-item has-dropdown">
          <span className="navbar-link">Appareils</span>
          <div className="navbar-dropdown">
            <Link to="/appareils/ordinateurs" className="dropdown-item">
              Ordinateurs
            </Link>
            <Link to="/appareils/peripheriques" className="dropdown-item">
              Périphériques
            </Link>
            <Link to="/appareils/objets-nomades" className="dropdown-item">
              Objets Nomades
            </Link>
            <Link to="/appareils/standards" className="dropdown-item">
              Appareils Standards
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar; 