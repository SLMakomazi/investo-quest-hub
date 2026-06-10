/**
 * Client-side validation for withdrawal requests.
 * Mirrors backend business rules to provide immediate UI feedback before submission.
 * Returns an error message if validation fails, null if valid.
 */
export function validateWithdrawal({ investor, product, amount }) {
  // Check if a product is selected
  if (!product) return 'Please choose a product.';
  
  // Check if amount is valid (positive number)
  if (!amount || isNaN(amount) || Number(amount) <= 0) return 'Enter a positive amount.';
  
  // Business rule: Retirement withdrawals only allowed for investors over 65
  if (product.type === 'RETIREMENT' && investor.age <= 65) {
    return 'Retirement withdrawals are only allowed for investors over 65.';
  }
  
  // Business rule: Cannot exceed available balance
  if (Number(amount) > Number(product.balance)) {
    return 'Amount exceeds available balance.';
  }
  
  // Business rule: Cannot exceed 90% of balance
  const max = Number(product.balance) * 0.9;
  if (Number(amount) > max) {
    return `Amount cannot exceed 90% of balance (max: ${max.toFixed(2)}).`;
  }
  
  // All validations passed
  return null;
}
