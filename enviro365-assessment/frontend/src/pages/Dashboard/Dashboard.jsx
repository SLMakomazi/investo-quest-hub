import { useEffect, useState } from 'react';
import { getPortfolio } from '../../services/apiService.js';
import { CURRENT_INVESTOR_ID } from '../../App.jsx';
import PortfolioCard from '../../components/PortfolioCard/PortfolioCard.jsx';
import './Dashboard.css';

export default function Dashboard() {
  const [portfolio, setPortfolio] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    getPortfolio(CURRENT_INVESTOR_ID).then(setPortfolio).catch(e => setError(e.message));
  }, []);

  if (error) return <p className="error">{error}</p>;
  if (!portfolio) return <p>Loading…</p>;

  const total = portfolio.products.reduce((s, p) => s + Number(p.balance), 0);

  return (
    <div className="dashboard">
      <header>
        <h1>Welcome, {portfolio.investor.firstName}</h1>
        <p>Age: {portfolio.investor.age} · Portfolio: {portfolio.name}</p>
        <div className="total">Total balance: R {total.toLocaleString()}</div>
      </header>

      <h2>Products</h2>
      <div className="product-grid">
        {portfolio.products.map(p => <PortfolioCard key={p.id} product={p} />)}
      </div>
    </div>
  );
}
