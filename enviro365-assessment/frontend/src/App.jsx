import { Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar.jsx';
import Dashboard from './pages/Dashboard/Dashboard.jsx';
import Withdrawals from './pages/Withdrawals/Withdrawals.jsx';
import History from './pages/History/History.jsx';

// Hard-coded investor for the demo (the brief assumes a single logged-in investor).
export const CURRENT_INVESTOR_ID = 1;

export default function App() {
  return (
    <>
      <Navbar />
      <div className="container">
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/withdrawals" element={<Withdrawals />} />
          <Route path="/history" element={<History />} />
        </Routes>
      </div>
    </>
  );
}
