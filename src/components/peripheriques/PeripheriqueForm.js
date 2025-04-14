import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './PeripheriqueForm.scss';

const PeripheriqueForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [formData, setFormData] = useState({
        nom: '',
        type: '',
        numeroSerie: '',
        etat: 'NEUF',
        dateAcquisition: '',
        dateFinGarantie: '',
        fabricant: '',
        modele: '',
        version: '',
        interfaceConnexion: '',
        coutAcquisition: '',
        notes: ''
    });

    useEffect(() => {
        if (id) {
            fetchPeripherique();
        }
    }, [id]);

    const fetchPeripherique = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:8080/api/peripheriques/${id}`);
            const peripherique = response.data;
            setFormData({
                nom: peripherique.nom,
                type: peripherique.type,
                numeroSerie: peripherique.numeroSerie,
                etat: peripherique.etat,
                dateAcquisition: peripherique.dateAcquisition ? peripherique.dateAcquisition.split('T')[0] : '',
                dateFinGarantie: peripherique.dateFinGarantie ? peripherique.dateFinGarantie.split('T')[0] : '',
                fabricant: peripherique.fabricant || '',
                modele: peripherique.modele || '',
                version: peripherique.version || '',
                interfaceConnexion: peripherique.interfaceConnexion || '',
                coutAcquisition: peripherique.coutAcquisition || '',
                notes: peripherique.notes || ''
            });
        } catch (err) {
            setError('Erreur lors du chargement du périphérique');
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
                await axios.put(`http://localhost:8080/api/peripheriques/${id}`, formData);
            } else {
                await axios.post('http://localhost:8080/api/peripheriques', formData);
            }
            navigate('/peripheriques');
        } catch (err) {
            setError('Erreur lors de l\'enregistrement du périphérique');
            console.error('Erreur:', err);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    return (
        <div className="peripherique-form">
            <h1>{id ? 'Modifier le périphérique' : 'Nouveau périphérique'}</h1>
            
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
                        <label htmlFor="type">Type</label>
                        <select
                            id="type"
                            name="type"
                            value={formData.type}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Sélectionnez un type</option>
                            <option value="IMPRIMANTE">Imprimante</option>
                            <option value="SCANNER">Scanner</option>
                            <option value="ECRAN">Écran</option>
                            <option value="CLAVIER">Clavier</option>
                            <option value="SOURIS">Souris</option>
                        </select>
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
                            <option value="NEUF">Neuf</option>
                            <option value="FONCTIONNEL">Fonctionnel</option>
                            <option value="EN_PANNE">En panne</option>
                            <option value="EN_MAINTENANCE">En maintenance</option>
                        </select>
                    </div>
                </div>

                <div className="form-section">
                    <h2>Spécifications techniques</h2>
                    
                    <div className="form-group">
                        <label htmlFor="fabricant">Fabricant</label>
                        <input
                            type="text"
                            id="fabricant"
                            name="fabricant"
                            value={formData.fabricant}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="modele">Modèle</label>
                        <input
                            type="text"
                            id="modele"
                            name="modele"
                            value={formData.modele}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="version">Version</label>
                        <input
                            type="text"
                            id="version"
                            name="version"
                            value={formData.version}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="interfaceConnexion">Interface de connexion</label>
                        <input
                            type="text"
                            id="interfaceConnexion"
                            name="interfaceConnexion"
                            value={formData.interfaceConnexion}
                            onChange={handleChange}
                        />
                    </div>
                </div>

                <div className="form-section">
                    <h2>Informations administratives</h2>
                    
                    <div className="form-group">
                        <label htmlFor="dateAcquisition">Date d'acquisition</label>
                        <input
                            type="date"
                            id="dateAcquisition"
                            name="dateAcquisition"
                            value={formData.dateAcquisition}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="dateFinGarantie">Date de fin de garantie</label>
                        <input
                            type="date"
                            id="dateFinGarantie"
                            name="dateFinGarantie"
                            value={formData.dateFinGarantie}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="coutAcquisition">Coût d'acquisition (€)</label>
                        <input
                            type="number"
                            id="coutAcquisition"
                            name="coutAcquisition"
                            value={formData.coutAcquisition}
                            onChange={handleChange}
                            min="0"
                            step="0.01"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="notes">Notes</label>
                        <textarea
                            id="notes"
                            name="notes"
                            value={formData.notes}
                            onChange={handleChange}
                        />
                    </div>
                </div>

                <div className="form-actions">
                    <button type="submit" className="btn-submit">
                        {id ? 'Mettre à jour' : 'Créer'}
                    </button>
                    <button 
                        type="button" 
                        className="btn-cancel"
                        onClick={() => navigate('/peripheriques')}
                    >
                        Annuler
                    </button>
                </div>
            </form>
        </div>
    );
};

export default PeripheriqueForm; 