import axios from 'axios';

/**
 * Axios HTTP client configured for backend API communication.
 * Base URL points to the Spring Boot backend server.
 */
const axiosClient = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' }
});

export default axiosClient;
