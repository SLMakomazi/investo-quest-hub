import { NavLink } from 'react-router-dom';
import './Navbar.css';

/**
 * Navigation bar component.
 * Provides links to Dashboard, Withdraw, and History pages.
 * Uses NavLink for active route highlighting.
 * Includes investor dropdown for switching between users.
 */
export default function Navbar({ currentInvestorId, onInvestorChange }) {
  // Available investors with their IDs, names, and colors for the dropdown
  const investors = [
    { id: 1, name: 'Thabo Mokoena', age: 70, color: '#4CAF50' },
    { id: 2, name: 'Aisha Patel', age: 45, color: '#2196F3' },
    { id: 3, name: 'Sipho Dlamini', age: 30, color: '#FF9800' },
    { id: 4, name: 'Siseko Makomazi', age: 25, color: '#9C27B0' },
    { id: 5, name: 'Nomsa Ngubane', age: 35, color: '#E91E63' }
  ];

  const currentInvestor = investors.find(i => i.id === currentInvestorId);

  return (
    <nav className="navbar">
      <div className="navbar-brand">Enviro365 Investments</div>
      <div className="navbar-links">
        {/* Investor Switcher Dropdown - positioned next to navigation links */}
        <div className="investor-switcher">
          <select 
            value={currentInvestorId} 
            onChange={(e) => onInvestorChange(Number(e.target.value))}
            className="investor-dropdown"
          >
            {investors.map(investor => (
              <option 
                key={investor.id} 
                value={investor.id}
                style={{ backgroundColor: investor.color, color: 'white' }}
              >
                {investor.name} (Age: {investor.age})
              </option>
            ))}
          </select>
          <div 
            className="investor-indicator"
            style={{ backgroundColor: currentInvestor?.color || '#4CAF50' }}
            title={`Current: ${currentInvestor?.name}`}
          />
        </div>
        
        <NavLink to="/dashboard">Dashboard</NavLink>
        <NavLink to="/withdrawals">Withdraw</NavLink>
        <NavLink to="/history">History</NavLink>
      </div>
    </nav>
  );
}
