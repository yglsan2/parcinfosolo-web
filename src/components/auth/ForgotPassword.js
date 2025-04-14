import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './ForgotPassword.scss';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess(false);

    try {
      await axios.post('http://localhost:8080/api/auth/forgot-password', { email });
      setSuccess(true);
    } catch (err) {
      setError(err.response?.data?.message || 'Une erreur est survenue lors de la réinitialisation du mot de passe.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="forgot-password-container">
      <div className="forgot-password-card">
        <h2>Mot de passe oublié</h2>
        {error && <div className="error">{error}</div>}
        {success ? (
          <div className="success">
            <p>Un email de réinitialisation a été envoyé à votre adresse email.</p>
            <Link to="/login" className="btn-back">Retour à la connexion</Link>
          </div>
        ) : (
          <>
            <p>Entrez votre adresse email pour recevoir un lien de réinitialisation.</p>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="email">Email</label>
                <input
                  type="email"
                  id="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  placeholder="Votre adresse email"
                />
              </div>
              <button type="submit" className="btn-submit" disabled={loading}>
                {loading ? 'Envoi en cours...' : 'Envoyer le lien'}
              </button>
            </form>
            <div className="login-link">
              <Link to="/login">Retour à la connexion</Link>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default ForgotPassword; 