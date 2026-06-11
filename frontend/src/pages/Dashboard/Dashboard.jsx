import { useEffect, useState } from 'react';
import { getPortfolio } from '../../services/apiService.js';
import PortfolioCard from '../../components/PortfolioCard/PortfolioCard.jsx';
import PortfolioAnalytics from '../../components/PortfolioAnalytics/PortfolioAnalytics.jsx';
import './Dashboard.css';

/**
 * Dashboard page component.
 * Displays investor information, portfolio details, and all investment products.
 * Calculates and shows total portfolio balance.
 * Includes portfolio analytics dashboard with key metrics.
 * Accepts currentInvestorId prop to support investor switching.
 */
export default function Dashboard({ currentInvestorId }) {
  const [portfolio, setPortfolio] = useState(null);
  const [error, setError] = useState(null);

  // Load portfolio data on component mount and when investor changes
  useEffect(() => {
    getPortfolio(currentInvestorId).then(setPortfolio).catch(e => setError(e.message));
  }, [currentInvestorId]);

  if (error) return <p className="error">{error}</p>;
  if (!portfolio) return <p>Loading…</p>;

  // Calculate total balance across all products
  const total = portfolio.products.reduce((s, p) => s + Number(p.balance), 0);

  return (
    <div className="dashboard">
      <header>
        <h1>Welcome, {portfolio.investor.firstName}</h1>
        <p>Age: {portfolio.investor.age} · Portfolio: {portfolio.name}</p>
        <div className="total">Total balance: R {total.toLocaleString()}</div>
      </header>

      {/* Portfolio Analytics Dashboard */}
      <PortfolioAnalytics portfolio={portfolio} />

      <h2>Products</h2>
      <div className="product-grid">
        {portfolio.products.map(p => <PortfolioCard key={p.id} product={p} />)}
      </div>
    </div>
  );
}
