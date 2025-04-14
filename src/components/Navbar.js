import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../store/slices/authSlice';
import '../styles/navbar.scss';

function Navbar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user } = useSelector(state => state.auth);

  const handleLogout = () => {
    dispatch(logout());
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/">ParcInfo</Link>
      </div>
      <div className="navbar-menu">
        {user ? (
          <>
            <span className="user-info">
              {user.nom} {user.prenom}
            </span>
            <button onClick={handleLogout} className="btn-logout">
              Déconnexion
            </button>
          </>
        ) : (
          <div className="auth-links">
            <Link to="/login" className="btn-login">Connexion</Link>
            <Link to="/register" className="btn-register">Inscription</Link>
          </div>
        )}
      </div>
    </nav>
  );
}

export default Navbar; 