import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchDashboardStart, fetchDashboardSuccess, fetchDashboardFailure } from '../store/slices/dashboardSlice';
import axios from 'axios';
import './Dashboard.scss';

const Dashboard = () => {
  const dispatch = useDispatch();
  const { stats, recentActivity, loading, error } = useSelector(state => state.dashboard);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        dispatch(fetchDashboardStart());
        const response = await axios.get('http://localhost:8080/api/dashboard');
        dispatch(fetchDashboardSuccess(response.data));
      } catch (err) {
        dispatch(fetchDashboardFailure(err.message));
      }
    };

    fetchDashboardData();
  }, [dispatch]);

  if (loading) return <div className="loading">Chargement du tableau de bord...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="dashboard">
      <h2>Tableau de Bord</h2>

      <div className="stats-grid">
        <div className="stat-card">
          <i className="fas fa-desktop"></i>
          <div className="stat-info">
            <h3>Appareils</h3>
            <p>{stats.totalAppareils}</p>
          </div>
        </div>

        <div className="stat-card">
          <i className="fas fa-keyboard"></i>
          <div className="stat-info">
            <h3>Périphériques</h3>
            <p>{stats.totalPeripheriques}</p>
          </div>
        </div>

        <div className="stat-card">
          <i className="fas fa-users"></i>
          <div className="stat-info">
            <h3>Personnes</h3>
            <p>{stats.totalPersonnes}</p>
          </div>
        </div>

        <div className="stat-card">
          <i className="fas fa-tools"></i>
          <div className="stat-info">
            <h3>Maintenances en cours</h3>
            <p>{stats.maintenancesEnCours}</p>
          </div>
        </div>
      </div>

      <div className="recent-activity">
        <h3>Activités Récentes</h3>
        <div className="activity-list">
          {recentActivity.length === 0 ? (
            <p className="no-activity">Aucune activité récente</p>
          ) : (
            recentActivity.map(activity => (
              <div key={activity.id} className="activity-item">
                <div className="activity-icon">
                  <i className={`fas ${activity.icon}`}></i>
                </div>
                <div className="activity-details">
                  <p className="activity-text">{activity.description}</p>
                  <span className="activity-date">
                    {new Date(activity.date).toLocaleDateString()}
                  </span>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard; 