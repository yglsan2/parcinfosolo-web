import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar: React.FC = () => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/" className="navbar-logo">
          ParcInfo
        </Link>
      </div>
      <div className="navbar-menu">
        <Link to="/" className="navbar-item">
          Accueil
        </Link>
        <div className="navbar-item has-dropdown">
          <span className="navbar-item">Appareils</span>
          <div className="navbar-dropdown">
            <Link to="/appareils" className="dropdown-item">
              Tous les appareils
            </Link>
            <Link to="/appareils/ordinateurs" className="dropdown-item">
              Ordinateurs
            </Link>
            <Link to="/appareils/smartphones" className="dropdown-item">
              Smartphones
            </Link>
            <Link to="/appareils/tablettes" className="dropdown-item">
              Tablettes
            </Link>
          </div>
        </div>
        <Link to="/personnes" className="navbar-item">
          Personnes
        </Link>
        <Link to="/peripheriques" className="navbar-item">
          Périphériques
        </Link>
      </div>
    </nav>
  );
};

export default Navbar; 