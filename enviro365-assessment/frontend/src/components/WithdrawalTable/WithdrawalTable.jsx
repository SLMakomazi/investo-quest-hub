import './WithdrawalTable.css';

/**
 * Table component for displaying withdrawal history.
 * Shows date, product name, product type, and withdrawal amount.
 */
export default function WithdrawalTable({ items }) {
  if (!items?.length) return <p>No withdrawals yet.</p>;
  return (
    <table className="withdrawal-table">
      <thead>
        <tr>
          <th>Date</th><th>Product</th><th>Type</th><th>Amount (ZAR)</th>
        </tr>
      </thead>
      <tbody>
        {items.map(w => (
          <tr key={w.id}>
            <td>{new Date(w.createdAt).toLocaleString()}</td>
            <td>{w.product?.name}</td>
            <td>{w.product?.type}</td>
            <td>R {Number(w.amount).toLocaleString()}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
