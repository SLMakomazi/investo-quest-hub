import { useEffect, useState } from 'react';
import { getHistory, exportCsvUrl } from '../../services/apiService.js';
import WithdrawalTable from '../../components/WithdrawalTable/WithdrawalTable.jsx';
import './History.css';

/**
 * Withdrawal history page.
 * Displays all withdrawals made by the current investor.
 * Includes search and filter functionality for withdrawal records.
 * Includes a CSV download button for exporting withdrawal records.
 * Accepts currentInvestorId prop to support investor switching.
 */
export default function History({ currentInvestorId }) {
  const [items, setItems] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterStatus, setFilterStatus] = useState('all');

  // Load withdrawal history on component mount and when investor changes
  useEffect(() => { getHistory(currentInvestorId).then(setItems); }, [currentInvestorId]);

  if (!items) return <p>Loading…</p>;

  // Filter items based on search term and status
  const filteredItems = items.filter(item => {
    const matchesSearch = searchTerm === '' || 
      item.product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.amount.toString().includes(searchTerm);
    
    // Since all successful withdrawals are stored, we treat all as "approved"
    const matchesFilter = filterStatus === 'all' || filterStatus === 'approved';
    
    return matchesSearch && matchesFilter;
  });

  return (
    <div className="history-page">
      <div className="history-header">
        <h1>Withdrawal History</h1>
        <a className="csv-btn" href={exportCsvUrl(currentInvestorId)}>Download CSV</a>
      </div>

      {/* Search and Filter Controls */}
      <div className="history-controls">
        <div className="search-box">
          <input
            type="text"
            placeholder="Search by product name or amount..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
        </div>
        
        <div className="filter-box">
          <label htmlFor="status-filter">Filter by Status:</label>
          <select
            id="status-filter"
            value={filterStatus}
            onChange={(e) => setFilterStatus(e.target.value)}
            className="filter-select"
          >
            <option value="all">All</option>
            <option value="approved">Approved</option>
            <option value="rejected">Rejected</option>
            <option value="pending">Pending</option>
          </select>
        </div>
      </div>

      {/* Results count */}
      <div className="results-count">
        Showing {filteredItems.length} of {items.length} records
      </div>

      <WithdrawalTable items={filteredItems} />
    </div>
  );
}
