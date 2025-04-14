import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './PersonneForm.scss';

const PersonneForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [formData, setFormData] = useState({
        nom: '',
        prenom: '',
        email: '',
        telephone: '',
        roles: [],
        statut: 'ACTIF',
        dateEmbauche: '',
        dateDepart: '',
        notes: ''
    });

    useEffect(() => {
        if (id) {
            fetchPersonne();
        }
    }, [id]);

    const fetchPersonne = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:8080/api/personnes/${id}`);
            setFormData(response.data);
        } catch (err) {
            setError('Erreur lors du chargement des données de la personne');
            console.error('Erreur:', err);
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        const { name, value, type } = e.target;
        if (type === 'checkbox') {
            const roles = formData.roles.includes(value)
                ? formData.roles.filter(role => role !== value)
                : [...formData.roles, value];
            setFormData(prev => ({ ...prev, roles }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            if (id) {
                await axios.put(`http://localhost:8080/api/personnes/${id}`, formData);
            } else {
                await axios.post('http://localhost:8080/api/personnes', formData);
            }
            navigate('/personnes');
        } catch (err) {
            setError('Erreur lors de l\'enregistrement de la personne');
            console.error('Erreur:', err);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    return (
        <div className="personne-form">
            <h1>{id ? 'Modifier la personne' : 'Nouvelle personne'}</h1>
            {error && <div className="error">{error}</div>}
            
            <form onSubmit={handleSubmit}>
                <div className="form-section">
                    <h2>Informations personnelles</h2>
                    <div className="form-group">
                        <label htmlFor="nom">Nom *</label>
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
                        <label htmlFor="prenom">Prénom *</label>
                        <input
                            type="text"
                            id="prenom"
                            name="prenom"
                            value={formData.prenom}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email *</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="telephone">Téléphone</label>
                        <input
                            type="tel"
                            id="telephone"
                            name="telephone"
                            value={formData.telephone}
                            onChange={handleChange}
                        />
                    </div>
                </div>

                <div className="form-section">
                    <h2>Rôles et statut</h2>
                    <div className="form-group">
                        <label>Rôles *</label>
                        <div className="checkbox-group">
                            <label>
                                <input
                                    type="checkbox"
                                    name="roles"
                                    value="ADMIN"
                                    checked={formData.roles.includes('ADMIN')}
                                    onChange={handleChange}
                                />
                                Administrateur
                            </label>
                            <label>
                                <input
                                    type="checkbox"
                                    name="roles"
                                    value="USER"
                                    checked={formData.roles.includes('USER')}
                                    onChange={handleChange}
                                />
                                Utilisateur
                            </label>
                            <label>
                                <input
                                    type="checkbox"
                                    name="roles"
                                    value="TECH"
                                    checked={formData.roles.includes('TECH')}
                                    onChange={handleChange}
                                />
                                Technicien
                            </label>
                        </div>
                    </div>
                    <div className="form-group">
                        <label htmlFor="statut">Statut *</label>
                        <select
                            id="statut"
                            name="statut"
                            value={formData.statut}
                            onChange={handleChange}
                            required
                        >
                            <option value="ACTIF">Actif</option>
                            <option value="INACTIF">Inactif</option>
                        </select>
                    </div>
                </div>

                <div className="form-section">
                    <h2>Informations administratives</h2>
                    <div className="form-group">
                        <label htmlFor="dateEmbauche">Date d'embauche</label>
                        <input
                            type="date"
                            id="dateEmbauche"
                            name="dateEmbauche"
                            value={formData.dateEmbauche}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="dateDepart">Date de départ</label>
                        <input
                            type="date"
                            id="dateDepart"
                            name="dateDepart"
                            value={formData.dateDepart}
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
                        />
                    </div>
                </div>

                <div className="form-actions">
                    <button type="button" className="btn-cancel" onClick={() => navigate('/personnes')}>
                        Annuler
                    </button>
                    <button type="submit" className="btn-submit">
                        {id ? 'Mettre à jour' : 'Créer'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default PersonneForm; 