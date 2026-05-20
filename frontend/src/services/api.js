import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

api.interceptors.request.use((config) => {
  console.log("[API REQUEST]", config.url);
  return config;
});

api.interceptors.response.use(
  (response) => {
    console.log("[API SUCCESS]", response.data);
    return response;
  },
  (error) => {
    console.error("[API ERROR]", error);
    return Promise.reject(error);
  }
);

export const fetchMonitoringStats = async () => {
  const { data } = await api.get('/monitor/stats');
  return data;
};

export const fetchIncidents = async () => {
  const { data } = await api.get('/incidents');
  return Array.isArray(data) ? data : [];
};
