import './PortfolioCard.css';

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
