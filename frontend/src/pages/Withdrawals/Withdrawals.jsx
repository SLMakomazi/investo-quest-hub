import { useEffect, useState } from 'react';
import { getPortfolio } from '../../services/apiService.js';
import WithdrawalForm from '../../components/WithdrawalForm/WithdrawalForm.jsx';
import './Withdrawals.css';

/**
 * Withdrawal submission page.
 * Loads portfolio data and renders the withdrawal form.
 * Reloads portfolio data after successful withdrawal to update balances.
 * Accepts currentInvestorId prop to support investor switching.
 */
export default function Withdrawals({ currentInvestorId }) {
  const [portfolio, setPortfolio] = useState(null);

  // Load portfolio data on component mount and when investor changes
  const load = () => getPortfolio(currentInvestorId).then(setPortfolio);
  useEffect(() => { load(); }, [currentInvestorId]);

  if (!portfolio) return <p>Loading…</p>;

  return (
    <div className="withdrawals-page">
      <h1>Submit a Withdrawal</h1>
      <p>Validation enforces the 90% rule and retirement age &gt; 65.</p>
      <WithdrawalForm
        investor={portfolio.investor}
        products={portfolio.products}
        onSuccess={load}  // Reload portfolio after successful withdrawal
      />
    </div>
  );
}
