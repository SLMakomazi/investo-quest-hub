import axiosClient from '../api/axiosClient.js';

/**
 * API service functions for communicating with the backend.
 * All functions return promises that resolve to the response data.
 */

// Get investor details by ID
export const getInvestor   = (id)            => axiosClient.get(`/investors/${id}`).then(r => r.data);

// Get portfolio with products by investor ID
export const getPortfolio  = (investorId)    => axiosClient.get(`/portfolios/investor/${investorId}`).then(r => r.data);

// Submit a withdrawal request
export const submitWithdrawal = (payload)    => axiosClient.post('/withdrawals', payload).then(r => r.data);

// Get withdrawal history for an investor
export const getHistory    = (investorId)    => axiosClient.get(`/withdrawals/investor/${investorId}`).then(r => r.data);

// Generate CSV export URL for an investor's withdrawals
export const exportCsvUrl  = (investorId)    => `http://localhost:8080/api/withdrawals/export?investorId=${investorId}`;
