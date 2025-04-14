import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import MeteoWidget from '../common/MeteoWidget';
import './PeripheriqueList.scss';

const PeripheriqueList = () => {
    const [peripheriques, setPeripheriques] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [filterType, setFilterType] = useState('');
    const [filterEtat, setFilterEtat] = useState('');
    const [selectedPeripherique, setSelectedPeripherique] = useState(null);

    useEffect(() => {
        fetchPeripheriques();
    }, []);

    const fetchPeripheriques = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://localhost:8080/api/peripheriques');
            setPeripheriques(response.data);
            setError(null);
        } catch (err) {
            console.error('Erreur lors de la récupération des périphériques:', err);
            setError('Impossible de charger la liste des périphériques');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Êtes-vous sûr de vouloir supprimer ce périphérique ?')) {
            try {
                await axios.delete(`http://localhost:8080/api/peripheriques/${id}`);
                setPeripheriques(peripheriques.filter(peripherique => peripherique.id !== id));
            } catch (err) {
                console.error('Erreur lors de la suppression:', err);
                setError('Impossible de supprimer ce périphérique');
            }
        }
    };

    const handleViewMeteo = (peripherique) => {
        setSelectedPeripherique(peripherique);
    };

    const filteredPeripheriques = peripheriques.filter(peripherique => {
        const matchesSearch = 
            peripherique.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
            peripherique.numeroSerie.toLowerCase().includes(searchTerm.toLowerCase());
        
        const matchesType = filterType ? peripherique.type === filterType : true;
        const matchesEtat = filterEtat ? peripherique.etat === filterEtat : true;
        
        return matchesSearch && matchesType && matchesEtat;
    });

    if (loading) {
        return <div className="loading">Chargement...</div>;
    }

    return (
        <div className="peripherique-list">
            <div className="header">
                <h1>Liste des périphériques</h1>
                <Link to="/peripheriques/new" className="btn-add">
                    Ajouter un périphérique
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
                    <option value="IMPRIMANTE">Imprimante</option>
                    <option value="SCANNER">Scanner</option>
                    <option value="ECRAN">Écran</option>
                    <option value="CLAVIER">Clavier</option>
                    <option value="SOURIS">Souris</option>
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

            {selectedPeripherique && (
                <MeteoWidget 
                    latitude={selectedPeripherique.latitude} 
                    longitude={selectedPeripherique.longitude}
                    nom={selectedPeripherique.nom}
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
                        {filteredPeripheriques.map(peripherique => (
                            <tr key={peripherique.id}>
                                <td>{peripherique.nom}</td>
                                <td>{peripherique.type}</td>
                                <td>{peripherique.numeroSerie}</td>
                                <td>
                                    <span className={`status-badge ${peripherique.etat.toLowerCase()}`}>
                                        {peripherique.etat}
                                    </span>
                                </td>
                                <td>{peripherique.localisation}</td>
                                <td className="actions">
                                    <button 
                                        className="btn-view"
                                        onClick={() => handleViewMeteo(peripherique)}
                                        title="Afficher la météo"
                                    >
                                        <i className="fas fa-cloud-sun"></i>
                                    </button>
                                    <Link 
                                        to={`/peripheriques/${peripherique.id}`} 
                                        className="btn-view"
                                        title="Voir les détails"
                                    >
                                        <i className="fas fa-eye"></i>
                                    </Link>
                                    <Link 
                                        to={`/peripheriques/${peripherique.id}/edit`} 
                                        className="btn-edit"
                                        title="Modifier"
                                    >
                                        <i className="fas fa-edit"></i>
                                    </Link>
                                    <button 
                                        className="btn-delete"
                                        onClick={() => handleDelete(peripherique.id)}
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

export default PeripheriqueList; 