import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './PeripheriqueDetails.css';

const PeripheriqueDetails = () => {
  const [peripherique, setPeripherique] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPeripherique = async () => {
      try {
        const response = await axios.get(`/api/peripheriques/${id}`);
        setPeripherique(response.data);
        setLoading(false);
      } catch (err) {
        setError('Erreur lors du chargement des détails du périphérique');
        setLoading(false);
      }
    };

    fetchPeripherique();
  }, [id]);

  if (loading) return <div className="loading">Chargement...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!peripherique) return <div className="error">Périphérique non trouvé</div>;

  return (
    <div className="peripherique-details">
      <h1>Détails du Périphérique</h1>
      <div className="peripherique-info">
        <div className="info-group">
          <label>Nom:</label>
          <span>{peripherique.nom}</span>
        </div>
        <div className="info-group">
          <label>Type:</label>
          <span>{peripherique.type}</span>
        </div>
        <div className="info-group">
          <label>Numéro de série:</label>
          <span>{peripherique.numeroSerie}</span>
        </div>
        <div className="info-group">
          <label>État:</label>
          <span>{peripherique.etat}</span>
        </div>
        <div className="info-group">
          <label>Date d'acquisition:</label>
          <span>{new Date(peripherique.dateAcquisition).toLocaleDateString()}</span>
        </div>
        <div className="info-group">
          <label>Date de fin de garantie:</label>
          <span>{new Date(peripherique.dateFinGarantie).toLocaleDateString()}</span>
        </div>
        <div className="info-group">
          <label>Fabricant:</label>
          <span>{peripherique.fabricant}</span>
        </div>
        <div className="info-group">
          <label>Modèle:</label>
          <span>{peripherique.modele}</span>
        </div>
        <div className="info-group">
          <label>Version:</label>
          <span>{peripherique.version}</span>
        </div>
        <div className="info-group">
          <label>Interface de connexion:</label>
          <span>{peripherique.interfaceConnexion}</span>
        </div>
      </div>
      <div className="actions">
        <button 
          className="edit-btn"
          onClick={() => navigate(`/peripheriques/edit/${id}`)}
        >
          Modifier
        </button>
        <button 
          className="back-btn"
          onClick={() => navigate('/peripheriques')}
        >
          Retour à la liste
        </button>
      </div>
    </div>
  );
};

export default PeripheriqueDetails; 