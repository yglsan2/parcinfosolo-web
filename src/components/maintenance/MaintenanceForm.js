import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './MaintenanceForm.scss';

const MaintenanceForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEditMode = Boolean(id);

  const [formData, setFormData] = useState({
    type: '',
    description: '',
    datePrevue: '',
    statut: 'planifiee',
    appareilId: '',
    technicienId: '',
    notes: ''
  });

  const [appareils, setAppareils] = useState([]);
  const [techniciens, setTechniciens] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [appareilsRes, techniciensRes] = await Promise.all([
          axios.get('http://localhost:8080/api/appareils'),
          axios.get('http://localhost:8080/api/personnes?role=technicien')
        ]);

        setAppareils(appareilsRes.data);
        setTechniciens(techniciensRes.data);

        if (isEditMode) {
          const maintenanceRes = await axios.get(`http://localhost:8080/api/maintenances/${id}`);
          const maintenance = maintenanceRes.data;
          setFormData({
            type: maintenance.type,
            description: maintenance.description,
            datePrevue: maintenance.datePrevue.split('T')[0],
            statut: maintenance.statut,
            appareilId: maintenance.appareil?.id || '',
            technicienId: maintenance.technicien?.id || '',
            notes: maintenance.notes || ''
          });
        }
      } catch (err) {
        setError('Erreur lors du chargement des données');
        console.error('Erreur:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id, isEditMode]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      if (isEditMode) {
        await axios.put(`http://localhost:8080/api/maintenances/${id}`, formData);
      } else {
        await axios.post('http://localhost:8080/api/maintenances', formData);
      }
      navigate('/maintenance');
    } catch (err) {
      setError('Erreur lors de l\'enregistrement de la maintenance');
      console.error('Erreur:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="loading">Chargement...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="maintenance-form">
      <h2>{isEditMode ? 'Modifier la Maintenance' : 'Nouvelle Maintenance'}</h2>
      
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="type">Type de maintenance</label>
          <select
            id="type"
            name="type"
            value={formData.type}
            onChange={handleChange}
            required
          >
            <option value="">Sélectionnez un type</option>
            <option value="preventive">Préventive</option>
            <option value="corrective">Corrective</option>
            <option value="calibration">Calibration</option>
            <option value="verification">Vérification</option>
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="description">Description</label>
          <textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            required
            rows="4"
          />
        </div>

        <div className="form-group">
          <label htmlFor="datePrevue">Date prévue</label>
          <input
            type="date"
            id="datePrevue"
            name="datePrevue"
            value={formData.datePrevue}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="statut">Statut</label>
          <select
            id="statut"
            name="statut"
            value={formData.statut}
            onChange={handleChange}
            required
          >
            <option value="planifiee">Planifiée</option>
            <option value="en_cours">En cours</option>
            <option value="terminee">Terminée</option>
            <option value="annulee">Annulée</option>
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="appareilId">Appareil</label>
          <select
            id="appareilId"
            name="appareilId"
            value={formData.appareilId}
            onChange={handleChange}
          >
            <option value="">Sélectionnez un appareil</option>
            {appareils.map(appareil => (
              <option key={appareil.id} value={appareil.id}>
                {appareil.nom} - {appareil.type}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="technicienId">Technicien</label>
          <select
            id="technicienId"
            name="technicienId"
            value={formData.technicienId}
            onChange={handleChange}
          >
            <option value="">Sélectionnez un technicien</option>
            {techniciens.map(technicien => (
              <option key={technicien.id} value={technicien.id}>
                {technicien.nom}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="notes">Notes</label>
          <textarea
            id="notes"
            name="notes"
            value={formData.notes}
            onChange={handleChange}
            rows="4"
          />
        </div>

        <div className="form-actions">
          <button type="button" onClick={() => navigate('/maintenance')} className="btn-cancel">
            Annuler
          </button>
          <button type="submit" className="btn-submit" disabled={loading}>
            {loading ? 'Enregistrement...' : (isEditMode ? 'Modifier' : 'Créer')}
          </button>
        </div>
      </form>
    </div>
  );
};

export default MaintenanceForm; 