import React, { useState } from 'react';
import { Table, Form, InputGroup, Button, Badge } from 'react-bootstrap';
import { FaSearch, FaFilter, FaPlus } from 'react-icons/fa';

interface Appareil {
  id: number;
  nom: string;
  type: string;
  etat: string;
  utilisateur: string;
  dateAcquisition: string;
}

const AppareilsList: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filterType, setFilterType] = useState('all');
  
  const appareils: Appareil[] = [
    {
      id: 1,
      nom: 'PC-001',
      type: 'Ordinateur',
      etat: 'En service',
      utilisateur: 'John Doe',
      dateAcquisition: '2024-01-15'
    },
    // Ajoutez plus d'appareils ici
  ];
  
  const getEtatBadge = (etat: string) => {
    switch (etat.toLowerCase()) {
      case 'en service':
        return <Badge bg="success">En service</Badge>;
      case 'en panne':
        return <Badge bg="danger">En panne</Badge>;
      case 'en maintenance':
        return <Badge bg="warning">En maintenance</Badge>;
      default:
        return <Badge bg="secondary">{etat}</Badge>;
    }
  };
  
  const filteredAppareils = appareils.filter(appareil => {
    const matchesSearch = appareil.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         appareil.utilisateur.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesFilter = filterType === 'all' || appareil.type === filterType;
    return matchesSearch && matchesFilter;
  });
  
  return (
    <div className="appareils-list">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1>Appareils</h1>
        <Button variant="primary">
          <FaPlus className="me-2" />
          Ajouter un appareil
        </Button>
      </div>
      
      <div className="filters mb-4">
        <Row className="g-3">
          <Col md={6}>
            <InputGroup>
              <InputGroup.Text>
                <FaSearch />
              </InputGroup.Text>
              <Form.Control
                type="text"
                placeholder="Rechercher un appareil..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </InputGroup>
          </Col>
          
          <Col md={6}>
            <InputGroup>
              <InputGroup.Text>
                <FaFilter />
              </InputGroup.Text>
              <Form.Select
                value={filterType}
                onChange={(e) => setFilterType(e.target.value)}
              >
                <option value="all">Tous les types</option>
                <option value="Ordinateur">Ordinateurs</option>
                <option value="Périphérique">Périphériques</option>
                <option value="Objet nomade">Objets nomades</option>
              </Form.Select>
            </InputGroup>
          </Col>
        </Row>
      </div>
      
      <div className="table-responsive">
        <Table hover responsive>
          <thead>
            <tr>
              <th>Nom</th>
              <th>Type</th>
              <th>État</th>
              <th>Utilisateur</th>
              <th>Date d'acquisition</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredAppareils.map(appareil => (
              <tr key={appareil.id}>
                <td>{appareil.nom}</td>
                <td>{appareil.type}</td>
                <td>{getEtatBadge(appareil.etat)}</td>
                <td>{appareil.utilisateur}</td>
                <td>{new Date(appareil.dateAcquisition).toLocaleDateString()}</td>
                <td>
                  <Button variant="outline-primary" size="sm" className="me-2">
                    Éditer
                  </Button>
                  <Button variant="outline-danger" size="sm">
                    Supprimer
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
};

export default AppareilsList; 