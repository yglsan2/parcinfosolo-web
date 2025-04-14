import React from 'react';
import { NavLink } from 'react-router-dom';
import '../styles/sidebar.scss';

function Sidebar() {
  return (
    <aside className="sidebar">
      <nav className="sidebar-nav">
        <NavLink to="/" className={({ isActive }) => isActive ? 'active' : ''}>
          <i className="fas fa-home"></i>
          <span>Tableau de bord</span>
        </NavLink>
        <NavLink to="/appareils" className={({ isActive }) => isActive ? 'active' : ''}>
          <i className="fas fa-laptop"></i>
          <span>Appareils</span>
        </NavLink>
        <NavLink to="/peripheriques" className={({ isActive }) => isActive ? 'active' : ''}>
          <i className="fas fa-keyboard"></i>
          <span>Périphériques</span>
        </NavLink>
        <NavLink to="/personnes" className={({ isActive }) => isActive ? 'active' : ''}>
          <i className="fas fa-users"></i>
          <span>Personnes</span>
        </NavLink>
        <NavLink to="/maintenance" className={({ isActive }) => isActive ? 'active' : ''}>
          <i className="fas fa-tools"></i>
          <span>Maintenance</span>
        </NavLink>
        <NavLink to="/historique" className={({ isActive }) => isActive ? 'active' : ''}>
          <i className="fas fa-history"></i>
          <span>Historique</span>
        </NavLink>
      </nav>
    </aside>
  );
}

export default Sidebar; 