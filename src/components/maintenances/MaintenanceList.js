import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './MaintenanceList.scss';

const MaintenanceList = () => {
    const [maintenances, setMaintenances] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [filterType, setFilterType] = useState('TOUS');
    const [filterStatut, setFilterStatut] = useState('TOUS');

    useEffect(() => {
        fetchMaintenances();
    }, []);

    const fetchMaintenances = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://localhost:8080/api/maintenances');
            setMaintenances(response.data);
            setError(null);
        } catch (err) {
            setError('Erreur lors du chargement des maintenances');
            console.error('Erreur:', err);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Êtes-vous sûr de vouloir supprimer cette maintenance ?')) {
            try {
                await axios.delete(`http://localhost:8080/api/maintenances/${id}`);
                setMaintenances(maintenances.filter(maintenance => maintenance.id !== id));
            } catch (err) {
                setError('Erreur lors de la suppression de la maintenance');
                console.error('Erreur:', err);
            }
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

    const filteredMaintenances = maintenances.filter(maintenance => {
        const matchesSearch = maintenance.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
                            maintenance.appareil.nom.toLowerCase().includes(searchTerm.toLowerCase());
        const matchesType = filterType === 'TOUS' || maintenance.type === filterType;
        const matchesStatut = filterStatut === 'TOUS' || maintenance.statut === filterStatut;
        return matchesSearch && matchesType && matchesStatut;
    });

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    return (
        <div className="maintenance-list">
            <div className="header">
                <h1>Gestion des Maintenances</h1>
                <Link to="/maintenances/new" className="btn-add">
                    Nouvelle Maintenance
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
                    <option value="TOUS">Tous les types</option>
                    <option value="PREVENTIVE">Préventive</option>
                    <option value="CORRECTIVE">Corrective</option>
                    <option value="INSPECTION">Inspection</option>
                </select>
                <select
                    value={filterStatut}
                    onChange={(e) => setFilterStatut(e.target.value)}
                    className="filter-select"
                >
                    <option value="TOUS">Tous les statuts</option>
                    <option value="PLANIFIEE">Planifiée</option>
                    <option value="EN_COURS">En cours</option>
                    <option value="TERMINEE">Terminée</option>
                    <option value="ANNULEE">Annulée</option>
                </select>
            </div>

            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Appareil</th>
                            <th>Type</th>
                            <th>Date prévue</th>
                            <th>Technicien</th>
                            <th>Statut</th>
                            <th>Coût</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredMaintenances.map(maintenance => (
                            <tr key={maintenance.id}>
                                <td>
                                    <Link to={`/appareils/${maintenance.appareil.id}`}>
                                        {maintenance.appareil.nom}
                                    </Link>
                                </td>
                                <td>{maintenance.type}</td>
                                <td>{formatDate(maintenance.datePrevue)}</td>
                                <td>{maintenance.technicien || 'Non assigné'}</td>
                                <td>
                                    <span className={`status-badge ${maintenance.statut.toLowerCase()}`}>
                                        {maintenance.statut}
                                    </span>
                                </td>
                                <td>{maintenance.cout ? `${maintenance.cout} €` : 'Non spécifié'}</td>
                                <td className="actions">
                                    <Link to={`/maintenances/${maintenance.id}`} className="btn-view">
                                        Voir
                                    </Link>
                                    <Link to={`/maintenances/${maintenance.id}/edit`} className="btn-edit">
                                        Modifier
                                    </Link>
                                    <button
                                        onClick={() => handleDelete(maintenance.id)}
                                        className="btn-delete"
                                    >
                                        Supprimer
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

export default MaintenanceList; 