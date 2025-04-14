import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ObjetMobileForm.scss';

const ObjetMobileForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [objetMobile, setObjetMobile] = useState({
        nom: '',
        type: '',
        numeroSerie: '',
        etat: '',
        fabricant: '',
        modele: '',
        version: '',
        interfaceConnexion: '',
        dateAcquisition: '',
        dateFinGarantie: '',
        dateDerniereMaintenance: ''
    });

    useEffect(() => {
        if (id) {
            fetchObjetMobile();
        }
    }, [id]);

    const fetchObjetMobile = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:8080/api/objets-mobiles/${id}`);
            const data = response.data;
            setObjetMobile({
                ...data,
                dateAcquisition: data.dateAcquisition?.split('T')[0] || '',
                dateFinGarantie: data.dateFinGarantie?.split('T')[0] || '',
                dateDerniereMaintenance: data.dateDerniereMaintenance?.split('T')[0] || ''
            });
        } catch (err) {
            setError('Erreur lors du chargement des données de l\'objet mobile');
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setObjetMobile(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            if (id) {
                await axios.put(`http://localhost:8080/api/objets-mobiles/${id}`, objetMobile);
            } else {
                await axios.post('http://localhost:8080/api/objets-mobiles', objetMobile);
            }
            navigate('/objets-mobiles');
        } catch (err) {
            setError('Erreur lors de l\'enregistrement de l\'objet mobile');
            setLoading(false);
        }
    };

    if (loading) return <div className="loading">Chargement...</div>;

    return (
        <div className="objet-mobile-form">
            <h1>{id ? 'Modifier l\'objet mobile' : 'Nouvel objet mobile'}</h1>
            
            {error && <div className="error">{error}</div>}
            
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <h2>Informations générales</h2>
                    <div className="form-row">
                        <label>
                            Nom
                            <input
                                type="text"
                                name="nom"
                                value={objetMobile.nom}
                                onChange={handleChange}
                                required
                            />
                        </label>
                        <label>
                            Type
                            <input
                                type="text"
                                name="type"
                                value={objetMobile.type}
                                onChange={handleChange}
                                required
                            />
                        </label>
                    </div>
                    <div className="form-row">
                        <label>
                            Numéro de série
                            <input
                                type="text"
                                name="numeroSerie"
                                value={objetMobile.numeroSerie}
                                onChange={handleChange}
                                required
                            />
                        </label>
                        <label>
                            État
                            <input
                                type="text"
                                name="etat"
                                value={objetMobile.etat}
                                onChange={handleChange}
                                required
                            />
                        </label>
                    </div>
                </div>

                <div className="form-group">
                    <h2>Spécifications techniques</h2>
                    <div className="form-row">
                        <label>
                            Fabricant
                            <input
                                type="text"
                                name="fabricant"
                                value={objetMobile.fabricant}
                                onChange={handleChange}
                                required
                            />
                        </label>
                        <label>
                            Modèle
                            <input
                                type="text"
                                name="modele"
                                value={objetMobile.modele}
                                onChange={handleChange}
                                required
                            />
                        </label>
                    </div>
                    <div className="form-row">
                        <label>
                            Version
                            <input
                                type="text"
                                name="version"
                                value={objetMobile.version}
                                onChange={handleChange}
                            />
                        </label>
                        <label>
                            Interface de connexion
                            <input
                                type="text"
                                name="interfaceConnexion"
                                value={objetMobile.interfaceConnexion}
                                onChange={handleChange}
                            />
                        </label>
                    </div>
                </div>

                <div className="form-group">
                    <h2>Dates importantes</h2>
                    <div className="form-row">
                        <label>
                            Date d'acquisition
                            <input
                                type="date"
                                name="dateAcquisition"
                                value={objetMobile.dateAcquisition}
                                onChange={handleChange}
                                required
                            />
                        </label>
                        <label>
                            Date de fin de garantie
                            <input
                                type="date"
                                name="dateFinGarantie"
                                value={objetMobile.dateFinGarantie}
                                onChange={handleChange}
                                required
                            />
                        </label>
                    </div>
                    <div className="form-row">
                        <label>
                            Date de dernière maintenance
                            <input
                                type="date"
                                name="dateDerniereMaintenance"
                                value={objetMobile.dateDerniereMaintenance}
                                onChange={handleChange}
                            />
                        </label>
                    </div>
                </div>

                <div className="form-actions">
                    <button type="button" onClick={() => navigate('/objets-mobiles')} className="cancel-btn">
                        Annuler
                    </button>
                    <button type="submit" className="submit-btn" disabled={loading}>
                        {id ? 'Mettre à jour' : 'Créer'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default ObjetMobileForm; 