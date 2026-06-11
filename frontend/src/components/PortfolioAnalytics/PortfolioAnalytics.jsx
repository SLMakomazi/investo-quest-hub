import './PortfolioAnalytics.css';

/**
 * Portfolio Analytics Dashboard component.
 * Displays key portfolio metrics and analytics.
 * Shows total value, product count, largest holding, and average value.
 * Provides visual insights into portfolio performance.
 */
export default function PortfolioAnalytics({ portfolio }) {
  if (!portfolio || !portfolio.products) {
    return null;
  }

  const products = portfolio.products;
  
  // Calculate analytics
  const totalValue = products.reduce((sum, p) => sum + Number(p.balance), 0);
  const productCount = products.length;
  
  // Find largest holding
  const largestHolding = products.reduce((max, p) => 
    Number(p.balance) > Number(max.balance) ? p : max, products[0]);
  
  // Calculate average product value
  const averageValue = productCount > 0 ? totalValue / productCount : 0;

  // Calculate risk indicator
  let riskProfile = 'Low Risk';
  let riskColor = '#43A047'; // Green
  
  if (totalValue > 250000) {
    riskProfile = 'High Risk';
    riskColor = '#F4511E'; // Orange
  } else if (totalValue >= 50000) {
    riskProfile = 'Medium Risk';
    riskColor = '#F9A825'; // Yellow
  }

  return (
    <div className="portfolio-analytics">
      <h3>Portfolio Analytics</h3>
      
      <div className="analytics-grid">
        {/* Total Portfolio Value */}
        <div className="analytics-card primary">
          <div className="card-icon">💰</div>
          <div className="card-content">
            <div className="card-label">Total Portfolio Value</div>
            <div className="card-value">R {totalValue.toLocaleString()}</div>
          </div>
        </div>

        {/* Total Products */}
        <div className="analytics-card secondary">
          <div className="card-icon">📊</div>
          <div className="card-content">
            <div className="card-label">Total Products</div>
            <div className="card-value">{productCount}</div>
          </div>
        </div>

        {/* Largest Holding */}
        <div className="analytics-card tertiary">
          <div className="card-icon">🏆</div>
          <div className="card-content">
            <div className="card-label">Largest Holding</div>
            <div className="card-value">{largestHolding.name}</div>
            <div className="card-subvalue">R {Number(largestHolding.balance).toLocaleString()}</div>
          </div>
        </div>

        {/* Average Value */}
        <div className="analytics-card quaternary">
          <div className="card-icon">📈</div>
          <div className="card-content">
            <div className="card-label">Average Value</div>
            <div className="card-value">R {averageValue.toLocaleString(undefined, {maximumFractionDigits: 0})}</div>
          </div>
        </div>
      </div>

      {/* Risk Indicator */}
      <div className="risk-indicator">
        <div className="risk-label">Risk Profile:</div>
        <div className="risk-value" style={{ color: riskColor }}>
          {riskProfile}
        </div>
        <div className="risk-description">
          {riskProfile === 'Low Risk' && 'Portfolio value below R50,000'}
          {riskProfile === 'Medium Risk' && 'Portfolio value between R50,000 and R250,000'}
          {riskProfile === 'High Risk' && 'Portfolio value above R250,000'}
        </div>
      </div>
    </div>
  );
}
