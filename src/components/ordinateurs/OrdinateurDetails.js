import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './OrdinateurDetails.scss';

const OrdinateurDetails = () => {
    const [ordinateur, setOrdinateur] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { id } = useParams();

    useEffect(() => {
        const fetchOrdinateur = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/ordinateurs/${id}`);
                setOrdinateur(response.data);
                setLoading(false);
            } catch (err) {
                setError('Erreur lors du chargement des détails de l\'ordinateur');
                setLoading(false);
            }
        };

        fetchOrdinateur();
    }, [id]);

    if (loading) return <div className="loading">Chargement...</div>;
    if (error) return <div className="error">{error}</div>;
    if (!ordinateur) return <div className="error">Ordinateur non trouvé</div>;

    return (
        <div className="ordinateur-details">
            <div className="header">
                <h1>Détails de l'ordinateur</h1>
                <div className="actions">
                    <Link to="/ordinateurs" className="back-btn">Retour à la liste</Link>
                    <Link to={`/ordinateurs/edit/${id}`} className="edit-btn">Modifier</Link>
                </div>
            </div>

            <div className="details-container">
                <div className="info-group">
                    <h2>Informations générales</h2>
                    <div className="info-row">
                        <span className="label">Nom:</span>
                        <span className="value">{ordinateur.nom}</span>
                    </div>
                    <div className="info-row">
                        <span className="label">Numéro de série:</span>
                        <span className="value">{ordinateur.numeroSerie}</span>
                    </div>
                    <div className="info-row">
                        <span className="label">État:</span>
                        <span className="value">{ordinateur.etat}</span>
                    </div>
                </div>

                <div className="info-group">
                    <h2>Spécifications techniques</h2>
                    <div className="info-row">
                        <span className="label">Processeur:</span>
                        <span className="value">{ordinateur.processeur}</span>
                    </div>
                    <div className="info-row">
                        <span className="label">Mémoire RAM:</span>
                        <span className="value">{ordinateur.memoireRam} Go</span>
                    </div>
                    <div className="info-row">
                        <span className="label">Capacité disque:</span>
                        <span className="value">{ordinateur.capaciteDisque} Go</span>
                    </div>
                    <div className="info-row">
                        <span className="label">Système d'exploitation:</span>
                        <span className="value">{ordinateur.systemeExploitation}</span>
                    </div>
                </div>

                <div className="info-group">
                    <h2>Informations de maintenance</h2>
                    <div className="info-row">
                        <span className="label">Date d'acquisition:</span>
                        <span className="value">{new Date(ordinateur.dateAcquisition).toLocaleDateString()}</span>
                    </div>
                    <div className="info-row">
                        <span className="label">Date de fin de garantie:</span>
                        <span className="value">{new Date(ordinateur.dateFinGarantie).toLocaleDateString()}</span>
                    </div>
                    <div className="info-row">
                        <span className="label">Dernière maintenance:</span>
                        <span className="value">
                            {ordinateur.derniereMaintenance 
                                ? new Date(ordinateur.derniereMaintenance).toLocaleDateString()
                                : 'Aucune maintenance enregistrée'}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrdinateurDetails; 