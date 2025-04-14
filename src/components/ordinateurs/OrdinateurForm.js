import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './OrdinateurForm.scss';

const OrdinateurForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [formData, setFormData] = useState({
        nom: '',
        type: 'ORDINATEUR',
        numeroSerie: '',
        etat: 'FONCTIONNEL',
        dateAcquisition: '',
        dateFinGarantie: '',
        fabricant: '',
        modele: '',
        version: '',
        interfaceConnexion: 'USB',
        processeur: '',
        memoireRam: '',
        capaciteDisque: '',
        systemeExploitation: '',
        antivirus: '',
        derniereMaintenance: '',
        prochaineMaintenance: '',
        notes: ''
    });

    useEffect(() => {
        if (id) {
            fetchOrdinateur();
        }
    }, [id]);

    const fetchOrdinateur = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:8080/api/ordinateurs/${id}`);
            setFormData(response.data);
        } catch (err) {
            setError('Erreur lors du chargement des données de l\'ordinateur');
            console.error('Erreur:', err);
        } finally {
            setLoading(false);
        }
    };

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
            if (id) {
                await axios.put(`http://localhost:8080/api/ordinateurs/${id}`, formData);
            } else {
                await axios.post('http://localhost:8080/api/ordinateurs', formData);
            }
            navigate('/ordinateurs');
        } catch (err) {
            setError('Erreur lors de l\'enregistrement de l\'ordinateur');
            console.error('Erreur:', err);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    return (
        <div className="ordinateur-form">
            <h1>{id ? 'Modifier l\'ordinateur' : 'Nouvel ordinateur'}</h1>
            
            {error && <div className="error">{error}</div>}
            
            <form onSubmit={handleSubmit}>
                <div className="form-section">
                    <h2>Informations générales</h2>
                    <div className="form-group">
                        <label htmlFor="nom">Nom</label>
                        <input
                            type="text"
                            id="nom"
                            name="nom"
                            value={formData.nom}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="numeroSerie">Numéro de série</label>
                        <input
                            type="text"
                            id="numeroSerie"
                            name="numeroSerie"
                            value={formData.numeroSerie}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="etat">État</label>
                        <select
                            id="etat"
                            name="etat"
                            value={formData.etat}
                            onChange={handleChange}
                            required
                        >
                            <option value="FONCTIONNEL">Fonctionnel</option>
                            <option value="EN_PANNE">En panne</option>
                            <option value="EN_MAINTENANCE">En maintenance</option>
                            <option value="HORS_SERVICE">Hors service</option>
                        </select>
                    </div>
                </div>

                <div className="form-section">
                    <h2>Spécifications techniques</h2>
                    <div className="form-group">
                        <label htmlFor="processeur">Processeur</label>
                        <input
                            type="text"
                            id="processeur"
                            name="processeur"
                            value={formData.processeur}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="memoireRam">Mémoire RAM</label>
                        <input
                            type="text"
                            id="memoireRam"
                            name="memoireRam"
                            value={formData.memoireRam}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="capaciteDisque">Capacité du disque</label>
                        <input
                            type="text"
                            id="capaciteDisque"
                            name="capaciteDisque"
                            value={formData.capaciteDisque}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="systemeExploitation">Système d'exploitation</label>
                        <input
                            type="text"
                            id="systemeExploitation"
                            name="systemeExploitation"
                            value={formData.systemeExploitation}
                            onChange={handleChange}
                        />
                    </div>
                </div>

                <div className="form-section">
                    <h2>Maintenance</h2>
                    <div className="form-group">
                        <label htmlFor="derniereMaintenance">Dernière maintenance</label>
                        <input
                            type="date"
                            id="derniereMaintenance"
                            name="derniereMaintenance"
                            value={formData.derniereMaintenance}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="prochaineMaintenance">Prochaine maintenance</label>
                        <input
                            type="date"
                            id="prochaineMaintenance"
                            name="prochaineMaintenance"
                            value={formData.prochaineMaintenance}
                            onChange={handleChange}
                        />
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
                </div>

                <div className="form-actions">
                    <button type="button" onClick={() => navigate('/ordinateurs')} className="cancel-btn">
                        Annuler
                    </button>
                    <button type="submit" className="submit-btn" disabled={loading}>
                        {loading ? 'Enregistrement...' : 'Enregistrer'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default OrdinateurForm; 