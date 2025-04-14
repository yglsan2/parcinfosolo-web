import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './OrdinateurList.scss';

const OrdinateurList = () => {
    const [ordinateurs, setOrdinateurs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrdinateurs = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/ordinateurs');
                setOrdinateurs(response.data);
                setLoading(false);
            } catch (err) {
                setError('Erreur lors du chargement des ordinateurs');
                setLoading(false);
            }
        };

        fetchOrdinateurs();
    }, []);

    if (loading) return <div className="loading">Chargement...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="ordinateur-list">
            <div className="header">
                <h1>Ordinateurs</h1>
                <Link to="/ordinateurs/new" className="add-btn">
                    Ajouter un ordinateur
                </Link>
            </div>

            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Type</th>
                            <th>Numéro de série</th>
                            <th>État</th>
                            <th>Processeur</th>
                            <th>RAM</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {ordinateurs.map(ordinateur => (
                            <tr key={ordinateur.id}>
                                <td>{ordinateur.nom}</td>
                                <td>{ordinateur.type}</td>
                                <td>{ordinateur.numeroSerie}</td>
                                <td>{ordinateur.etat}</td>
                                <td>{ordinateur.processeur}</td>
                                <td>{ordinateur.ram} Go</td>
                                <td>
                                    <Link to={`/ordinateurs/${ordinateur.id}`} className="view-btn">
                                        Voir
                                    </Link>
                                    <Link to={`/ordinateurs/edit/${ordinateur.id}`} className="edit-btn">
                                        Modifier
                                    </Link>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default OrdinateurList; 