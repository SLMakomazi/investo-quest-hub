import './PortfolioCard.css';

/**
 * Portfolio card component for displaying individual investment products.
 * Shows product name, type (RETIREMENT/SAVINGS), and current balance.
 */
export default function PortfolioCard({ product }) {
  return (
    <div className="portfolio-card">
      <div className="pc-header">
        <h3>{product.name}</h3>
        <span className={`pc-tag ${product.type.toLowerCase()}`}>{product.type}</span>
      </div>
      <div className="pc-balance">R {Number(product.balance).toLocaleString()}</div>
    </div>
  );
}
