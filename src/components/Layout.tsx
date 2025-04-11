import React, { ReactNode } from 'react';
import Navbar from './layout/Navbar';
import './layout/Layout.css';

interface LayoutProps {
  children: ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <div className="layout">
      <Navbar />
      <main className="main-content">
        {children}
      </main>
      <footer className="footer">
        <p>&copy; {new Date().getFullYear()} ParcInfo. Tous droits réservés.</p>
      </footer>
    </div>
  );
};

export default Layout;
