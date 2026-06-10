import axiosClient from '../api/axiosClient.js';

export const getInvestor   = (id)            => axiosClient.get(`/investors/${id}`).then(r => r.data);
export const getPortfolio  = (investorId)    => axiosClient.get(`/portfolios/investor/${investorId}`).then(r => r.data);
export const submitWithdrawal = (payload)    => axiosClient.post('/withdrawals', payload).then(r => r.data);
export const getHistory    = (investorId)    => axiosClient.get(`/withdrawals/investor/${investorId}`).then(r => r.data);
export const exportCsvUrl  = (investorId)    => `http://localhost:8080/api/withdrawals/export?investorId=${investorId}`;
