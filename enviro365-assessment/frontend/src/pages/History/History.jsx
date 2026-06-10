import { useEffect, useState } from 'react';
import { getHistory, exportCsvUrl } from '../../services/apiService.js';
import { CURRENT_INVESTOR_ID } from '../../App.jsx';
import WithdrawalTable from '../../components/WithdrawalTable/WithdrawalTable.jsx';
import './History.css';

export default function History() {
  const [items, setItems] = useState(null);

  useEffect(() => { getHistory(CURRENT_INVESTOR_ID).then(setItems); }, []);

  if (!items) return <p>Loading…</p>;

  return (
    <div className="history-page">
      <div className="history-header">
        <h1>Withdrawal History</h1>
        <a className="csv-btn" href={exportCsvUrl(CURRENT_INVESTOR_ID)}>Download CSV</a>
      </div>
      <WithdrawalTable items={items} />
    </div>
  );
}
