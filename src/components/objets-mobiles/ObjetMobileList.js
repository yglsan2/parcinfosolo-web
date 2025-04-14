import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './ObjetMobileList.scss';

const ObjetMobileList = () => {
    const [objetsMobiles, setObjetsMobiles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchObjetsMobiles = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/objets-mobiles');
                setObjetsMobiles(response.data);
                setLoading(false);
            } catch (err) {
                setError('Erreur lors du chargement des objets mobiles');
                setLoading(false);
            }
        };

        fetchObjetsMobiles();
    }, []);

    if (loading) return <div className="loading">Chargement...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="objet-mobile-list">
            <div className="header">
                <h1>Objets Mobiles</h1>
                <Link to="/objets-mobiles/new" className="add-btn">
                    Ajouter un objet mobile
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
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {objetsMobiles.map(objet => (
                            <tr key={objet.id}>
                                <td>{objet.nom}</td>
                                <td>{objet.type}</td>
                                <td>{objet.numeroSerie}</td>
                                <td>{objet.etat}</td>
                                <td>
                                    <Link to={`/objets-mobiles/${objet.id}`} className="view-btn">
                                        Voir
                                    </Link>
                                    <Link to={`/objets-mobiles/edit/${objet.id}`} className="edit-btn">
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

export default ObjetMobileList; 