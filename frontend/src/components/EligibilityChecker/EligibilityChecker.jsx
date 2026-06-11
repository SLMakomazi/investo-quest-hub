import './EligibilityChecker.css';

/**
 * Withdrawal Eligibility Checker component.
 * Displays withdrawal eligibility status before submission.
 * Shows which business rules are met and which are not.
 * Provides real-time feedback to improve user experience.
 */
export default function EligibilityChecker({ investor, product, amount }) {
  // Calculate 90% of product balance
  const maxAllowed = product ? product.balance * 0.9 : 0;

  // Check each business rule
  const checks = {
    age: investor && product && product.type === 'RETIREMENT' ? investor.age > 65 : true,
    balance: product && amount ? amount <= product.balance : true,
    ninetyPercent: product && amount ? amount <= maxAllowed : true
  };

  // All checks must pass for withdrawal to be eligible
  const isEligible = checks.age && checks.balance && checks.ninetyPercent;

  return (
    <div className="eligibility-checker">
      <h3>Withdrawal Eligibility Check</h3>
      
      <div className="eligibility-summary">
        <div className="summary-item">
          <span className="label">Investor:</span>
          <span className="value">{investor ? `${investor.firstName} ${investor.lastName}` : 'N/A'}</span>
        </div>
        <div className="summary-item">
          <span className="label">Age:</span>
          <span className="value">{investor ? investor.age : 'N/A'}</span>
        </div>
        <div className="summary-item">
          <span className="label">Product:</span>
          <span className="value">{product ? product.name : 'N/A'}</span>
        </div>
        <div className="summary-item">
          <span className="label">Type:</span>
          <span className="value">{product ? product.type : 'N/A'}</span>
        </div>
        <div className="summary-item">
          <span className="label">Current Balance:</span>
          <span className="value">R {product ? product.balance.toLocaleString() : '0'}</span>
        </div>
        <div className="summary-item">
          <span className="label">Requested Amount:</span>
          <span className="value">R {amount ? amount.toLocaleString() : '0'}</span>
        </div>
      </div>

      <div className="eligibility-rules">
        <div className={`rule ${checks.age ? 'pass' : 'fail'}`}>
          <span className="rule-icon">{checks.age ? '✓' : '✗'}</span>
          <span className="rule-text">
            {product && product.type === 'RETIREMENT' 
              ? `Age requirement met (must be > 65)` 
              : 'Age requirement (not applicable for SAVINGS)'}
          </span>
        </div>
        
        <div className={`rule ${checks.balance ? 'pass' : 'fail'}`}>
          <span className="rule-icon">{checks.balance ? '✓' : '✗'}</span>
          <span className="rule-text">Sufficient balance</span>
        </div>
        
        <div className={`rule ${checks.ninetyPercent ? 'pass' : 'fail'}`}>
          <span className="rule-icon">{checks.ninetyPercent ? '✓' : '✗'}</span>
          <span className="rule-text">
            {checks.ninetyPercent 
              ? 'Within 90% withdrawal limit' 
              : `Exceeds 90% withdrawal limit (max: R ${maxAllowed.toLocaleString()})`}
          </span>
        </div>
      </div>

      {!isEligible && (
        <div className="eligibility-error">
          <p>⚠️ Withdrawal cannot be submitted. Please address the failed checks above.</p>
        </div>
      )}

      {isEligible && amount > 0 && (
        <div className="eligibility-success">
          <p>✅ All checks passed. Withdrawal is eligible for submission.</p>
        </div>
      )}
    </div>
  );
}
