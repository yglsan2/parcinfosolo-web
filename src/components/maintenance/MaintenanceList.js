import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './MaintenanceList.scss';

const MaintenanceList = () => {
  const [maintenances, setMaintenances] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterStatus, setFilterStatus] = useState('');

  useEffect(() => {
    fetchMaintenances();
  }, []);

  const fetchMaintenances = async () => {
    try {
      setLoading(true);
      const response = await axios.get('http://localhost:8080/api/maintenances');
      setMaintenances(response.data);
      setError(null);
    } catch (err) {
      setError('Erreur lors du chargement des maintenances');
      console.error('Erreur:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer cette maintenance ?')) {
      try {
        await axios.delete(`http://localhost:8080/api/maintenances/${id}`);
        setMaintenances(maintenances.filter(maintenance => maintenance.id !== id));
      } catch (err) {
        setError('Erreur lors de la suppression de la maintenance');
        console.error('Erreur:', err);
      }
    }
  };

  const filteredMaintenances = maintenances.filter(maintenance => {
    const matchesSearch = maintenance.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         maintenance.type.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = filterStatus === '' || maintenance.statut === filterStatus;
    return matchesSearch && matchesStatus;
  });

  if (loading) return <div className="loading">Chargement des maintenances...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="maintenance-list">
      <div className="header">
        <h2>Liste des Maintenances</h2>
        <Link to="/maintenance/new" className="btn-add">
          <i className="fas fa-plus"></i>
          Nouvelle Maintenance
        </Link>
      </div>

      <div className="filters">
        <div className="search-box">
          <i className="fas fa-search"></i>
          <input
            type="text"
            placeholder="Rechercher une maintenance..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <select
          value={filterStatus}
          onChange={(e) => setFilterStatus(e.target.value)}
        >
          <option value="">Tous les statuts</option>
          <option value="PLANIFIEE">Planifiée</option>
          <option value="EN_COURS">En cours</option>
          <option value="TERMINEE">Terminée</option>
          <option value="ANNULEE">Annulée</option>
        </select>
      </div>

      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>Type</th>
              <th>Description</th>
              <th>Date prévue</th>
              <th>Statut</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredMaintenances.length === 0 ? (
              <tr>
                <td colSpan="5" className="no-data">
                  Aucune maintenance trouvée
                </td>
              </tr>
            ) : (
              filteredMaintenances.map(maintenance => (
                <tr key={maintenance.id}>
                  <td>{maintenance.type}</td>
                  <td>{maintenance.description}</td>
                  <td>{new Date(maintenance.datePrevue).toLocaleDateString()}</td>
                  <td>
                    <span className={`status-badge ${maintenance.statut.toLowerCase()}`}>
                      {maintenance.statut === 'PLANIFIEE' ? 'Planifiée' :
                       maintenance.statut === 'EN_COURS' ? 'En cours' :
                       maintenance.statut === 'TERMINEE' ? 'Terminée' :
                       maintenance.statut === 'ANNULEE' ? 'Annulée' :
                       maintenance.statut}
                    </span>
                  </td>
                  <td className="actions">
                    <Link to={`/maintenance/${maintenance.id}`} className="btn-view">
                      <i className="fas fa-eye"></i>
                    </Link>
                    <Link to={`/maintenance/${maintenance.id}/edit`} className="btn-edit">
                      <i className="fas fa-edit"></i>
                    </Link>
                    <button
                      onClick={() => handleDelete(maintenance.id)}
                      className="btn-delete"
                    >
                      <i className="fas fa-trash"></i>
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default MaintenanceList; 