import { useState } from 'react';
import { submitWithdrawal } from '../../services/apiService.js';
import { validateWithdrawal } from '../../utils/validators.js';
import './WithdrawalForm.css';

export default function WithdrawalForm({ investor, products, onSuccess }) {
  const [productId, setProductId] = useState(products[0]?.id ?? '');
  const [amount, setAmount] = useState('');
  const [error, setError] = useState(null);
  const [msg, setMsg] = useState(null);
  const [busy, setBusy] = useState(false);

  const product = products.find(p => String(p.id) === String(productId));

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null); setMsg(null);

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
      onSuccess?.();
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

      {error && <div className="wf-error">{error}</div>}
      {msg   && <div className="wf-ok">{msg}</div>}

      <button type="submit" disabled={busy}>
        {busy ? 'Submitting…' : 'Submit Withdrawal'}
      </button>
    </form>
  );
}
