import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import MeteoWidget from '../common/MeteoWidget';
import './PersonneList.scss';

const PersonneList = () => {
    const [personnes, setPersonnes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [filterRole, setFilterRole] = useState('');
    const [selectedPersonne, setSelectedPersonne] = useState(null);

    useEffect(() => {
        fetchPersonnes();
    }, []);

    const fetchPersonnes = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://localhost:8080/api/personnes');
            setPersonnes(response.data);
            setError(null);
        } catch (err) {
            console.error('Erreur lors de la récupération des personnes:', err);
            setError('Impossible de charger la liste des personnes');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Êtes-vous sûr de vouloir supprimer cette personne ?')) {
            try {
                await axios.delete(`http://localhost:8080/api/personnes/${id}`);
                setPersonnes(personnes.filter(personne => personne.id !== id));
            } catch (err) {
                console.error('Erreur lors de la suppression:', err);
                setError('Impossible de supprimer cette personne');
            }
        }
    };

    const handleViewMeteo = (personne) => {
        setSelectedPersonne(personne);
    };

    const filteredPersonnes = personnes.filter(personne => {
        const matchesSearch = 
            personne.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
            personne.prenom.toLowerCase().includes(searchTerm.toLowerCase()) ||
            personne.email.toLowerCase().includes(searchTerm.toLowerCase());
        
        const matchesRole = filterRole ? personne.roles.includes(filterRole) : true;
        
        return matchesSearch && matchesRole;
    });

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    return (
        <div className="personne-list">
            <div className="header">
                <h1>Liste des personnes</h1>
                <Link to="/personnes/new" className="btn-add">
                    Ajouter une personne
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
                    value={filterRole}
                    onChange={(e) => setFilterRole(e.target.value)}
                    className="filter-select"
                >
                    <option value="">Tous les rôles</option>
                    <option value="ADMIN">Administrateur</option>
                    <option value="USER">Utilisateur</option>
                    <option value="TECH">Technicien</option>
                </select>
            </div>

            {selectedPersonne && (
                <MeteoWidget 
                    latitude={selectedPersonne.latitude} 
                    longitude={selectedPersonne.longitude}
                    nom={`${selectedPersonne.prenom} ${selectedPersonne.nom}`}
                />
            )}

            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Prénom</th>
                            <th>Email</th>
                            <th>Rôles</th>
                            <th>Statut</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredPersonnes.map(personne => (
                            <tr key={personne.id}>
                                <td>{personne.nom}</td>
                                <td>{personne.prenom}</td>
                                <td>{personne.email}</td>
                                <td>
                                    {personne.roles.map(role => (
                                        <span key={role} className="role-badge">
                                            {role}
                                        </span>
                                    ))}
                                </td>
                                <td>
                                    <span className={`status-badge ${personne.statut.toLowerCase()}`}>
                                        {personne.statut}
                                    </span>
                                </td>
                                <td className="actions">
                                    <button 
                                        className="btn-view"
                                        onClick={() => handleViewMeteo(personne)}
                                        title="Afficher la météo"
                                    >
                                        <i className="fas fa-cloud-sun"></i>
                                    </button>
                                    <Link 
                                        to={`/personnes/${personne.id}`} 
                                        className="btn-view"
                                        title="Voir les détails"
                                    >
                                        <i className="fas fa-eye"></i>
                                    </Link>
                                    <Link 
                                        to={`/personnes/${personne.id}/edit`} 
                                        className="btn-edit"
                                        title="Modifier"
                                    >
                                        <i className="fas fa-edit"></i>
                                    </Link>
                                    <button 
                                        className="btn-delete"
                                        onClick={() => handleDelete(personne.id)}
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

export default PersonneList; 