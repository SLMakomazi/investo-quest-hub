import { useEffect, useState } from 'react';
import { getHistory, exportCsvUrl } from '../../services/apiService.js';
import WithdrawalTable from '../../components/WithdrawalTable/WithdrawalTable.jsx';
import './History.css';

/**
 * Withdrawal history page.
 * Displays all withdrawals made by the current investor.
 * Includes a CSV download button for exporting withdrawal records.
 * Accepts currentInvestorId prop to support investor switching.
 */
export default function History({ currentInvestorId }) {
  const [items, setItems] = useState(null);

  // Load withdrawal history on component mount and when investor changes
  useEffect(() => { getHistory(currentInvestorId).then(setItems); }, [currentInvestorId]);

  if (!items) return <p>Loading…</p>;

  return (
    <div className="history-page">
      <div className="history-header">
        <h1>Withdrawal History</h1>
        <a className="csv-btn" href={exportCsvUrl(currentInvestorId)}>Download CSV</a>
      </div>
      <WithdrawalTable items={items} />
    </div>
  );
}
