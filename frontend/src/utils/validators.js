/**
 * Client-side validation for withdrawal requests.
 * Only blocks malformed requests. Business-rule failures still submit so the
 * backend can record approved or rejected attempts in withdrawal history.
 */
export function validateWithdrawal({ investor, product, amount }) {
  // Check if a product is selected
  if (!product) return 'Please choose a product.';
  
  // Check if amount is valid (positive number)
  if (!amount || isNaN(amount) || Number(amount) <= 0) return 'Enter a positive amount.';

  return null;
}
