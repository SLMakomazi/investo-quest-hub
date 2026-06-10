import { NavLink } from 'react-router-dom';
import './Navbar.css';

export default function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-brand">Enviro365 Investments</div>
      <div className="navbar-links">
        <NavLink to="/dashboard">Dashboard</NavLink>
        <NavLink to="/withdrawals">Withdraw</NavLink>
        <NavLink to="/history">History</NavLink>
      </div>
    </nav>
  );
}
