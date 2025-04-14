import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Dashboard from './components/Dashboard';
import AppareilList from './components/appareils/AppareilList';
import AppareilDetails from './components/appareils/AppareilDetails';
import PeripheriqueList from './components/peripheriques/PeripheriqueList';
import PeripheriqueDetails from './components/peripheriques/PeripheriqueDetails';
import PersonneList from './components/personnes/PersonneList';
import PersonneDetails from './components/personnes/PersonneDetails';
import MaintenanceList from './components/maintenance/MaintenanceList';
import MaintenanceDetails from './components/maintenance/MaintenanceDetails';
import MaintenanceForm from './components/maintenance/MaintenanceForm';
import HistoriqueList from './components/historique/HistoriqueList';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import NotFound from './components/common/NotFound';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/" element={<Layout />}>
          <Route index element={<Dashboard />} />
          <Route path="appareils" element={<AppareilList />} />
          <Route path="appareils/:id" element={<AppareilDetails />} />
          <Route path="peripheriques" element={<PeripheriqueList />} />
          <Route path="peripheriques/:id" element={<PeripheriqueDetails />} />
          <Route path="personnes" element={<PersonneList />} />
          <Route path="personnes/:id" element={<PersonneDetails />} />
          <Route path="maintenance" element={<MaintenanceList />} />
          <Route path="maintenance/new" element={<MaintenanceForm />} />
          <Route path="maintenance/:id/edit" element={<MaintenanceForm />} />
          <Route path="maintenance/:id" element={<MaintenanceDetails />} />
          <Route path="historique" element={<HistoriqueList />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App; 