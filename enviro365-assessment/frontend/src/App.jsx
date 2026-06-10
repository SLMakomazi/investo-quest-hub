import { Routes, Route, Navigate } from 'react-router-dom';
import { useState } from 'react';
import Navbar from './components/Navbar/Navbar.jsx';
import Dashboard from './pages/Dashboard/Dashboard.jsx';
import Withdrawals from './pages/Withdrawals/Withdrawals.jsx';
import History from './pages/History/History.jsx';

/**
 * Main application component.
 * Sets up routing for Dashboard, Withdrawals, and History pages.
 * Includes navigation bar and redirects root to dashboard.
 * Manages current investor selection state for switching between users.
 */
export default function App() {
  // State to track the currently selected investor
  const [currentInvestorId, setCurrentInvestorId] = useState(1);

  // Handler for investor selection change
  const handleInvestorChange = (investorId) => {
    setCurrentInvestorId(investorId);
  };

  return (
    <>
      <Navbar 
        currentInvestorId={currentInvestorId} 
        onInvestorChange={handleInvestorChange} 
      />
      <div className="container">
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route 
            path="/dashboard" 
            element={<Dashboard currentInvestorId={currentInvestorId} />} 
          />
          <Route 
            path="/withdrawals" 
            element={<Withdrawals currentInvestorId={currentInvestorId} />} 
          />
          <Route 
            path="/history" 
            element={<History currentInvestorId={currentInvestorId} />} 
          />
        </Routes>
      </div>
    </>
  );
}
