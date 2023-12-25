import axios from 'axios';
const BASE_URL = process.env.REACT_APP_DEFAULT_SERVER;

export const axiosPrivate = axios.create({
    baseURL: BASE_URL,
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
});

