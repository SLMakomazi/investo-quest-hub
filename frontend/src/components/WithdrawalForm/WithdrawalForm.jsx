import { useState } from 'react';
import { submitWithdrawal } from '../../services/apiService.js';
import { validateWithdrawal } from '../../utils/validators.js';
import EligibilityChecker from '../EligibilityChecker/EligibilityChecker.jsx';
import './WithdrawalForm.css';

/**
 * Withdrawal form component.
 * Allows users to select a product and enter a withdrawal amount.
 * Performs client-side validation before submitting to backend.
 * Displays success/error messages and updates parent on success.
 * Includes eligibility checker to show withdrawal status before submission.
 */
export default function WithdrawalForm({ investor, products, onSuccess }) {
  const [productId, setProductId] = useState(products[0]?.id ?? '');
  const [amount, setAmount] = useState('');
  const [error, setError] = useState(null);
  const [msg, setMsg] = useState(null);
  const [busy, setBusy] = useState(false);

  const product = products.find(p => String(p.id) === String(productId));
  const amountValue = amount ? Number(amount) : 0;

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null); setMsg(null);

    // Perform client-side validation first
    const clientError = validateWithdrawal({ investor, product, amount });
    if (clientError) { setError(clientError); return; }

    setBusy(true);
    try {
      const result = await submitWithdrawal({
        investorId: investor.id,
        productId: Number(productId),
        amount: Number(amount)
      });
      setMsg(`Success. New balance: R ${Number(result.remainingBalance).toLocaleString()}`);
      setAmount('');
      onSuccess?.();  // Notify parent to reload data
    } catch (err) {
      setError(err.response?.data?.message ?? 'Request failed.');
    } finally {
      setBusy(false);
    }
  }

  return (
    <form className="withdrawal-form" onSubmit={handleSubmit}>
      <label>
        Product
        <select value={productId} onChange={e => setProductId(e.target.value)}>
          {products.map(p => (
            <option key={p.id} value={p.id}>
              {p.name} ({p.type}) — R {Number(p.balance).toLocaleString()}
            </option>
          ))}
        </select>
      </label>

      <label>
        Amount (ZAR)
        <input type="number" min="0" step="0.01" value={amount}
               onChange={e => setAmount(e.target.value)} required />
      </label>

      {/* Eligibility Checker - shows withdrawal status before submission */}
      {product && amountValue > 0 && (
        <EligibilityChecker 
          investor={investor} 
          product={product} 
          amount={amountValue} 
        />
      )}

      {error && <div className="wf-error">{error}</div>}
      {msg   && <div className="wf-ok">{msg}</div>}

      <button type="submit" disabled={busy}>
        {busy ? 'Submitting…' : 'Submit Withdrawal'}
      </button>
    </form>
  );
}
