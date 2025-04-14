import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './HistoriqueList.scss';

const HistoriqueList = () => {
  const [historique, setHistorique] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterDate, setFilterDate] = useState('');
  const [filterType, setFilterType] = useState('');

  useEffect(() => {
    fetchHistorique();
  }, []);

  const fetchHistorique = async () => {
    try {
      setLoading(true);
      const response = await axios.get('http://localhost:8080/api/maintenances/historique');
      setHistorique(response.data);
      setError(null);
    } catch (err) {
      setError('Erreur lors du chargement de l\'historique');
      console.error('Erreur:', err);
    } finally {
      setLoading(false);
    }
  };

  const filteredHistorique = historique.filter(item => {
    const matchesDate = filterDate === '' || item.date.includes(filterDate);
    const matchesType = filterType === '' || item.type === filterType;
    return matchesDate && matchesType;
  });

  if (loading) return <div className="loading">Chargement de l'historique...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="historique-list">
      <div className="header">
        <h2>Historique des Maintenances</h2>
      </div>

      <div className="filters">
        <div className="filter-group">
          <label htmlFor="filterDate">Filtrer par date:</label>
          <input
            type="date"
            id="filterDate"
            value={filterDate}
            onChange={(e) => setFilterDate(e.target.value)}
          />
        </div>
        <div className="filter-group">
          <label htmlFor="filterType">Filtrer par type:</label>
          <select
            id="filterType"
            value={filterType}
            onChange={(e) => setFilterType(e.target.value)}
          >
            <option value="">Tous les types</option>
            <option value="PREVENTIVE">Préventive</option>
            <option value="CORRECTIVE">Corrective</option>
            <option value="INSPECTION">Inspection</option>
          </select>
        </div>
      </div>

      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Type</th>
              <th>Description</th>
              <th>Statut</th>
              <th>Technicien</th>
            </tr>
          </thead>
          <tbody>
            {filteredHistorique.length === 0 ? (
              <tr>
                <td colSpan="5" className="no-data">
                  Aucun historique trouvé
                </td>
              </tr>
            ) : (
              filteredHistorique.map(item => (
                <tr key={item.id}>
                  <td>{new Date(item.date).toLocaleDateString()}</td>
                  <td>{item.type}</td>
                  <td>{item.description}</td>
                  <td>
                    <span className={`status-badge ${item.statut.toLowerCase()}`}>
                      {item.statut === 'PLANIFIEE' ? 'Planifiée' :
                       item.statut === 'EN_COURS' ? 'En cours' :
                       item.statut === 'TERMINEE' ? 'Terminée' :
                       item.statut === 'ANNULEE' ? 'Annulée' :
                       item.statut}
                    </span>
                  </td>
                  <td>{item.technicien?.nom || 'Non assigné'}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default HistoriqueList; 