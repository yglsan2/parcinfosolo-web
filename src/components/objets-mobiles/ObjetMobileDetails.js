import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './ObjetMobileDetails.scss';

const ObjetMobileDetails = () => {
    const [objetMobile, setObjetMobile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { id } = useParams();

    useEffect(() => {
        const fetchObjetMobile = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/objets-mobiles/${id}`);
                setObjetMobile(response.data);
                setLoading(false);
            } catch (err) {
                setError('Erreur lors du chargement des détails de l\'objet mobile');
                setLoading(false);
            }
        };

        fetchObjetMobile();
    }, [id]);

    if (loading) return <div className="loading">Chargement...</div>;
    if (error) return <div className="error">{error}</div>;
    if (!objetMobile) return <div className="error">Objet mobile non trouvé</div>;

    return (
        <div className="objet-mobile-details">
            <div className="header">
                <h1>Détails de l'objet mobile</h1>
                <div className="actions">
                    <Link to="/objets-mobiles" className="back-btn">Retour à la liste</Link>
                    <Link to={`/objets-mobiles/edit/${id}`} className="edit-btn">Modifier</Link>
                </div>
            </div>

            <div className="details-container">
                <div className="detail-group">
                    <h2>Informations générales</h2>
                    <div className="detail-row">
                        <span className="label">Nom:</span>
                        <span className="value">{objetMobile.nom}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">Type:</span>
                        <span className="value">{objetMobile.type}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">Numéro de série:</span>
                        <span className="value">{objetMobile.numeroSerie}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">État:</span>
                        <span className="value">{objetMobile.etat}</span>
                    </div>
                </div>

                <div className="detail-group">
                    <h2>Spécifications techniques</h2>
                    <div className="detail-row">
                        <span className="label">Fabricant:</span>
                        <span className="value">{objetMobile.fabricant}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">Modèle:</span>
                        <span className="value">{objetMobile.modele}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">Version:</span>
                        <span className="value">{objetMobile.version}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">Interface de connexion:</span>
                        <span className="value">{objetMobile.interfaceConnexion}</span>
                    </div>
                </div>

                <div className="detail-group">
                    <h2>Dates importantes</h2>
                    <div className="detail-row">
                        <span className="label">Date d'acquisition:</span>
                        <span className="value">{new Date(objetMobile.dateAcquisition).toLocaleDateString()}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">Fin de garantie:</span>
                        <span className="value">{new Date(objetMobile.dateFinGarantie).toLocaleDateString()}</span>
                    </div>
                    <div className="detail-row">
                        <span className="label">Date de dernière maintenance:</span>
                        <span className="value">{objetMobile.dateDerniereMaintenance ? new Date(objetMobile.dateDerniereMaintenance).toLocaleDateString() : 'Non renseignée'}</span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ObjetMobileDetails; 