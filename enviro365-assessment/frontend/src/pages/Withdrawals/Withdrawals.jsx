import { useEffect, useState } from 'react';
import { getPortfolio } from '../../services/apiService.js';
import { CURRENT_INVESTOR_ID } from '../../App.jsx';
import WithdrawalForm from '../../components/WithdrawalForm/WithdrawalForm.jsx';
import './Withdrawals.css';

export default function Withdrawals() {
  const [portfolio, setPortfolio] = useState(null);

  const load = () => getPortfolio(CURRENT_INVESTOR_ID).then(setPortfolio);
  useEffect(() => { load(); }, []);

  if (!portfolio) return <p>Loading…</p>;

  return (
    <div className="withdrawals-page">
      <h1>Submit a Withdrawal</h1>
      <p>Validation enforces the 90% rule and retirement age &gt; 65.</p>
      <WithdrawalForm
        investor={portfolio.investor}
        products={portfolio.products}
        onSuccess={load}
      />
    </div>
  );
}
