import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './PersonneDetails.scss';

const PersonneDetails = () => {
    const { id } = useParams();
    const [personne, setPersonne] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchPersonne = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/personnes/${id}`);
                setPersonne(response.data);
                setLoading(false);
            } catch (err) {
                setError('Erreur lors du chargement des détails de la personne');
                setLoading(false);
            }
        };

        fetchPersonne();
    }, [id]);

    if (loading) return <div className="loading">Chargement...</div>;
    if (error) return <div className="error">{error}</div>;
    if (!personne) return <div className="error">Personne non trouvée</div>;

    return (
        <div className="personne-details">
            <h1>Détails de la Personne</h1>
            
            <div className="personne-info">
                <div className="info-group">
                    <label>Nom:</label>
                    <span>{personne.nom}</span>
                </div>
                <div className="info-group">
                    <label>Prénom:</label>
                    <span>{personne.prenom}</span>
                </div>
                <div className="info-group">
                    <label>Email:</label>
                    <span>{personne.email}</span>
                </div>
                <div className="info-group">
                    <label>Téléphone:</label>
                    <span>{personne.telephone}</span>
                </div>
                <div className="info-group">
                    <label>Date de naissance:</label>
                    <span>{new Date(personne.dateNaissance).toLocaleDateString()}</span>
                </div>
                <div className="info-group">
                    <label>Date d'embauche:</label>
                    <span>{new Date(personne.dateEmbauche).toLocaleDateString()}</span>
                </div>
                <div className="info-group">
                    <label>Service:</label>
                    <span>{personne.service}</span>
                </div>
                <div className="info-group">
                    <label>Fonction:</label>
                    <span>{personne.fonction}</span>
                </div>
            </div>

            <div className="actions">
                <Link to={`/personnes/${id}/edit`} className="edit-btn">
                    Modifier
                </Link>
                <Link to="/personnes" className="back-btn">
                    Retour à la liste
                </Link>
            </div>
        </div>
    );
};

export default PersonneDetails; 