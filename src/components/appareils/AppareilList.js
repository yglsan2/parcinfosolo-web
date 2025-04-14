import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import MeteoWidget from '../common/MeteoWidget';
import './AppareilList.scss';

const AppareilList = () => {
    const [appareils, setAppareils] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [filterType, setFilterType] = useState('');
    const [filterEtat, setFilterEtat] = useState('');
    const [selectedAppareil, setSelectedAppareil] = useState(null);

    useEffect(() => {
        fetchAppareils();
    }, []);

    const fetchAppareils = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://localhost:8080/api/appareils');
            setAppareils(response.data);
            setError(null);
        } catch (err) {
            console.error('Erreur lors de la récupération des appareils:', err);
            setError('Impossible de charger la liste des appareils');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Êtes-vous sûr de vouloir supprimer cet appareil ?')) {
            try {
                await axios.delete(`http://localhost:8080/api/appareils/${id}`);
                setAppareils(appareils.filter(appareil => appareil.id !== id));
            } catch (err) {
                console.error('Erreur lors de la suppression:', err);
                setError('Impossible de supprimer cet appareil');
            }
        }
    };

    const handleViewMeteo = (appareil) => {
        setSelectedAppareil(appareil);
    };

    const filteredAppareils = appareils.filter(appareil => {
        const matchesSearch = 
            appareil.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
            appareil.numeroSerie.toLowerCase().includes(searchTerm.toLowerCase());
        
        const matchesType = filterType ? appareil.type === filterType : true;
        const matchesEtat = filterEtat ? appareil.etat === filterEtat : true;
        
        return matchesSearch && matchesType && matchesEtat;
    });

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    return (
        <div className="appareil-list">
            <div className="header">
                <h1>Liste des appareils</h1>
                <Link to="/appareils/new" className="btn-add">
                    Ajouter un appareil
                </Link>
            </div>

            {error && <div className="error">{error}</div>}

            <div className="filters">
                <input
                    type="text"
                    placeholder="Rechercher..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="search-input"
                />
                <select
                    value={filterType}
                    onChange={(e) => setFilterType(e.target.value)}
                    className="filter-select"
                >
                    <option value="">Tous les types</option>
                    <option value="ORDINATEUR">Ordinateur</option>
                    <option value="SERVEUR">Serveur</option>
                    <option value="IMPRIMANTE">Imprimante</option>
                </select>
                <select
                    value={filterEtat}
                    onChange={(e) => setFilterEtat(e.target.value)}
                    className="filter-select"
                >
                    <option value="">Tous les états</option>
                    <option value="NEUF">Neuf</option>
                    <option value="FONCTIONNEL">Fonctionnel</option>
                    <option value="EN_PANNE">En panne</option>
                    <option value="EN_MAINTENANCE">En maintenance</option>
                </select>
            </div>

            {selectedAppareil && (
                <MeteoWidget 
                    latitude={selectedAppareil.latitude} 
                    longitude={selectedAppareil.longitude}
                    nom={selectedAppareil.nom}
                />
            )}

            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Type</th>
                            <th>Numéro de série</th>
                            <th>État</th>
                            <th>Localisation</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredAppareils.map(appareil => (
                            <tr key={appareil.id}>
                                <td>{appareil.nom}</td>
                                <td>{appareil.type}</td>
                                <td>{appareil.numeroSerie}</td>
                                <td>
                                    <span className={`status-badge ${appareil.etat.toLowerCase()}`}>
                                        {appareil.etat}
                                    </span>
                                </td>
                                <td>{appareil.localisation}</td>
                                <td className="actions">
                                    <button 
                                        className="btn-view"
                                        onClick={() => handleViewMeteo(appareil)}
                                        title="Afficher la météo"
                                    >
                                        <i className="fas fa-cloud-sun"></i>
                                    </button>
                                    <Link 
                                        to={`/appareils/${appareil.id}`} 
                                        className="btn-view"
                                        title="Voir les détails"
                                    >
                                        <i className="fas fa-eye"></i>
                                    </Link>
                                    <Link 
                                        to={`/appareils/${appareil.id}/edit`} 
                                        className="btn-edit"
                                        title="Modifier"
                                    >
                                        <i className="fas fa-edit"></i>
                                    </Link>
                                    <button 
                                        className="btn-delete"
                                        onClick={() => handleDelete(appareil.id)}
                                        title="Supprimer"
                                    >
                                        <i className="fas fa-trash"></i>
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default AppareilList; 