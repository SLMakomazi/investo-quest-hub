import './WithdrawalTable.css';

/**
 * Table component for displaying withdrawal history.
 * Shows date, product name, product type, withdrawal amount, and status.
 * Includes status badge for audit trail visualization.
 */
export default function WithdrawalTable({ items }) {
  if (!items?.length) return <p>No withdrawals yet.</p>;

  const getStatusBadge = (status) => {
    const statusStyles = {
      APPROVED: { bg: '#e8f5e9', color: '#2e7d32', text: 'Approved' },
      REJECTED: { bg: '#ffebee', color: '#c62828', text: 'Rejected' },
      PENDING: { bg: '#fff3e0', color: '#ef6c00', text: 'Pending' }
    };
    const style = statusStyles[status] || statusStyles.APPROVED;
    return (
      <span 
        className="status-badge" 
        style={{ backgroundColor: style.bg, color: style.color }}
      >
        {style.text}
      </span>
    );
  };

  return (
    <table className="withdrawal-table">
      <thead>
        <tr>
          <th>Date</th><th>Product</th><th>Type</th><th>Amount (ZAR)</th><th>Status</th>
        </tr>
      </thead>
      <tbody>
        {items.map(w => (
          <tr key={w.id}>
            <td>{new Date(w.createdAt).toLocaleString()}</td>
            <td>{w.product?.name}</td>
            <td>{w.product?.type}</td>
            <td>R {Number(w.amount).toLocaleString()}</td>
            <td>{getStatusBadge(w.status || 'APPROVED')}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
