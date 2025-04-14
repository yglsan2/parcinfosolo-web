import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './MaintenanceDetails.scss';

const MaintenanceDetails = () => {
  const { id } = useParams();
  const [maintenance, setMaintenance] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMaintenance = async () => {
      try {
        setLoading(true);
        const response = await axios.get(`http://localhost:8080/api/maintenances/${id}`);
        setMaintenance(response.data);
        setError(null);
      } catch (err) {
        setError('Erreur lors du chargement des détails de la maintenance');
        console.error('Erreur:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchMaintenance();
  }, [id]);

  const handleDelete = async () => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer cette maintenance ?')) {
      try {
        await axios.delete(`http://localhost:8080/api/maintenances/${id}`);
        window.location.href = '/maintenance';
      } catch (err) {
        setError('Erreur lors de la suppression de la maintenance');
        console.error('Erreur:', err);
      }
    }
  };

  if (loading) return <div className="loading">Chargement des détails...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!maintenance) return <div className="error">Maintenance non trouvée</div>;

  return (
    <div className="maintenance-details">
      <div className="header">
        <h2>Détails de la Maintenance</h2>
        <div className="actions">
          <Link to={`/maintenance/${id}/edit`} className="btn-edit">
            <i className="fas fa-edit"></i> Modifier
          </Link>
          <button onClick={handleDelete} className="btn-delete">
            <i className="fas fa-trash"></i> Supprimer
          </button>
          <Link to="/maintenance" className="btn-back">
            <i className="fas fa-arrow-left"></i> Retour
          </Link>
        </div>
      </div>

      <div className="details-card">
        <div className="section">
          <h3>Informations générales</h3>
          <div className="info-grid">
            <div className="info-item">
              <label>Type:</label>
              <span>{maintenance.type}</span>
            </div>
            <div className="info-item">
              <label>Description:</label>
              <span>{maintenance.description}</span>
            </div>
            <div className="info-item">
              <label>Date prévue:</label>
              <span>{new Date(maintenance.datePrevue).toLocaleDateString()}</span>
            </div>
            <div className="info-item">
              <label>Statut:</label>
              <span className={`status-badge ${maintenance.statut}`}>
                {maintenance.statut}
              </span>
            </div>
          </div>
        </div>

        <div className="section">
          <h3>Appareil concerné</h3>
          {maintenance.appareil ? (
            <div className="info-grid">
              <div className="info-item">
                <label>Nom:</label>
                <span>{maintenance.appareil.nom}</span>
              </div>
              <div className="info-item">
                <label>Type:</label>
                <span>{maintenance.appareil.type}</span>
              </div>
              <div className="info-item">
                <label>Numéro de série:</label>
                <span>{maintenance.appareil.numeroSerie}</span>
              </div>
            </div>
          ) : (
            <p>Aucun appareil associé</p>
          )}
        </div>

        <div className="section">
          <h3>Technicien assigné</h3>
          {maintenance.technicien ? (
            <div className="info-grid">
              <div className="info-item">
                <label>Nom:</label>
                <span>{maintenance.technicien.nom}</span>
              </div>
              <div className="info-item">
                <label>Email:</label>
                <span>{maintenance.technicien.email}</span>
              </div>
              <div className="info-item">
                <label>Téléphone:</label>
                <span>{maintenance.technicien.telephone}</span>
              </div>
            </div>
          ) : (
            <p>Aucun technicien assigné</p>
          )}
        </div>

        {maintenance.notes && (
          <div className="section">
            <h3>Notes</h3>
            <p>{maintenance.notes}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default MaintenanceDetails; 