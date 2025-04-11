import React from 'react';
import { Card, Row, Col } from 'react-bootstrap';
import { FaDesktop, FaLaptop, FaMobile, FaTablet } from 'react-icons/fa';

interface DashboardStats {
  totalAppareils: number;
  ordinateurs: number;
  peripheriques: number;
  objetsNomades: number;
}

const Dashboard: React.FC = () => {
  const stats: DashboardStats = {
    totalAppareils: 150,
    ordinateurs: 45,
    peripheriques: 75,
    objetsNomades: 30
  };

  return (
    <div className="dashboard">
      <h1 className="mb-4">Tableau de bord</h1>
      
      <Row className="g-4">
        <Col md={3}>
          <Card className="dashboard-card">
            <Card.Body>
              <div className="d-flex align-items-center">
                <div className="icon-wrapper bg-primary">
                  <FaDesktop />
                </div>
                <div className="ms-3">
                  <h6 className="card-subtitle mb-1">Total Appareils</h6>
                  <h2 className="card-title mb-0">{stats.totalAppareils}</h2>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={3}>
          <Card className="dashboard-card">
            <Card.Body>
              <div className="d-flex align-items-center">
                <div className="icon-wrapper bg-success">
                  <FaLaptop />
                </div>
                <div className="ms-3">
                  <h6 className="card-subtitle mb-1">Ordinateurs</h6>
                  <h2 className="card-title mb-0">{stats.ordinateurs}</h2>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={3}>
          <Card className="dashboard-card">
            <Card.Body>
              <div className="d-flex align-items-center">
                <div className="icon-wrapper bg-warning">
                  <FaMobile />
                </div>
                <div className="ms-3">
                  <h6 className="card-subtitle mb-1">Périphériques</h6>
                  <h2 className="card-title mb-0">{stats.peripheriques}</h2>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={3}>
          <Card className="dashboard-card">
            <Card.Body>
              <div className="d-flex align-items-center">
                <div className="icon-wrapper bg-info">
                  <FaTablet />
                </div>
                <div className="ms-3">
                  <h6 className="card-subtitle mb-1">Objets Nomades</h6>
                  <h2 className="card-title mb-0">{stats.objetsNomades}</h2>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row className="mt-4">
        <Col md={8}>
          <Card>
            <Card.Header>
              <h5 className="card-title mb-0">État du parc</h5>
            </Card.Header>
            <Card.Body>
              {/* Graphique d'état du parc à implémenter */}
              <div className="chart-placeholder">
                Graphique d'état du parc
              </div>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={4}>
          <Card>
            <Card.Header>
              <h5 className="card-title mb-0">Dernières activités</h5>
            </Card.Header>
            <Card.Body>
              <div className="activity-list">
                {/* Liste des dernières activités à implémenter */}
                <div className="activity-item">
                  <div className="activity-icon bg-primary">
                    <FaDesktop />
                  </div>
                  <div className="activity-content">
                    <p className="mb-0">Nouvel ordinateur ajouté</p>
                    <small className="text-muted">Il y a 2 heures</small>
                  </div>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Dashboard; 