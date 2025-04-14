import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './AppareilDetails.scss';

const AppareilDetails = () => {
    const { id } = useParams();
    const [appareil, setAppareil] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchAppareilDetails();
    }, [id]);

    const fetchAppareilDetails = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:8080/api/appareils/${id}`);
            setAppareil(response.data);
            setError(null);
        } catch (err) {
            setError('Erreur lors du chargement des détails de l\'appareil');
            console.error('Erreur:', err);
        } finally {
            setLoading(false);
        }
    };

    const formatDate = (dateString) => {
        if (!dateString) return 'Non spécifié';
        const date = new Date(dateString);
        return date.toLocaleDateString('fr-FR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        });
    };

    const handleDelete = async () => {
        if (window.confirm('Êtes-vous sûr de vouloir supprimer cet appareil ?')) {
            try {
                await axios.delete(`http://localhost:8080/api/appareils/${id}`);
                window.location.href = '/appareils';
            } catch (err) {
                setError('Erreur lors de la suppression de l\'appareil');
                console.error('Erreur:', err);
            }
        }
    };

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    if (error) {
        return <div className="error">{error}</div>;
    }

    if (!appareil) {
        return <div className="error">Appareil non trouvé</div>;
    }

    return (
        <div className="appareil-details">
            <div className="header">
                <h1>Détails de l'appareil</h1>
                <div className="actions">
                    <Link to={`/appareils/${id}/edit`} className="btn-edit">
                        Modifier
                    </Link>
                    <button onClick={handleDelete} className="btn-delete">
                        Supprimer
                    </button>
                    <Link to="/appareils" className="btn-back">
                        Retour à la liste
                    </Link>
                </div>
            </div>

            <div className="details-container">
                <div className="details-section">
                    <h2>Informations générales</h2>
                    <div className="details-grid">
                        <div className="detail-item">
                            <span className="label">Nom:</span>
                            <span className="value">{appareil.nom}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Type:</span>
                            <span className="value">{appareil.type}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Numéro de série:</span>
                            <span className="value">{appareil.numeroSerie}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">État:</span>
                            <span className={`status-badge ${appareil.etat.toLowerCase()}`}>
                                {appareil.etat}
                            </span>
                        </div>
                        <div className="detail-item full-width">
                            <span className="label">Description:</span>
                            <span className="value">{appareil.description || 'Aucune description'}</span>
                        </div>
                    </div>
                </div>

                <div className="details-section">
                    <h2>Spécifications techniques</h2>
                    <div className="details-grid">
                        <div className="detail-item full-width">
                            <span className="label">Spécifications:</span>
                            <span className="value">{appareil.specificationsTechniques || 'Aucune spécification'}</span>
                        </div>
                    </div>
                </div>

                <div className="details-section">
                    <h2>Informations d'acquisition</h2>
                    <div className="details-grid">
                        <div className="detail-item">
                            <span className="label">Date d'acquisition:</span>
                            <span className="value">{formatDate(appareil.dateAcquisition)}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Date de fin de garantie:</span>
                            <span className="value">{formatDate(appareil.dateFinGarantie)}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Coût d'acquisition:</span>
                            <span className="value">{appareil.coutAcquisition ? `${appareil.coutAcquisition} €` : 'Non spécifié'}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Fournisseur:</span>
                            <span className="value">{appareil.fournisseur || 'Non spécifié'}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Numéro de commande:</span>
                            <span className="value">{appareil.numeroCommande || 'Non spécifié'}</span>
                        </div>
                    </div>
                </div>

                <div className="details-section">
                    <h2>Localisation et gestion</h2>
                    <div className="details-grid">
                        <div className="detail-item">
                            <span className="label">Emplacement:</span>
                            <span className="value">{appareil.emplacement || 'Non spécifié'}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Responsable:</span>
                            <span className="value">{appareil.responsable || 'Non assigné'}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Numéro d'inventaire:</span>
                            <span className="value">{appareil.numeroInventaire || 'Non spécifié'}</span>
                        </div>
                        <div className="detail-item">
                            <span className="label">Statut d'archivage:</span>
                            <span className="value">{appareil.estArchive ? 'Archivé' : 'Actif'}</span>
                        </div>
                        <div className="detail-item full-width">
                            <span className="label">Notes:</span>
                            <span className="value">{appareil.notes || 'Aucune note'}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AppareilDetails; 