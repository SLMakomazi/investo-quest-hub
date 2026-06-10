// Client-side mirror of backend business rules — UI feedback before submit.

export function validateWithdrawal({ investor, product, amount }) {
  if (!product) return 'Please choose a product.';
  if (!amount || isNaN(amount) || Number(amount) <= 0) return 'Enter a positive amount.';
  if (product.type === 'RETIREMENT' && investor.age <= 65) {
    return 'Retirement withdrawals are only allowed for investors over 65.';
  }
  if (Number(amount) > Number(product.balance)) {
    return 'Amount exceeds available balance.';
  }
  const max = Number(product.balance) * 0.9;
  if (Number(amount) > max) {
    return `Amount cannot exceed 90% of balance (max: ${max.toFixed(2)}).`;
  }
  return null;
}
