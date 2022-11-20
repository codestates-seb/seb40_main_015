import axios from 'axios';
import { BASE_URL } from '../constants/constants';

const config = {
	baseURL: BASE_URL,
	headers: { 'Content-Type': 'application/json' },
	timeout: 10000,
};

const axiosInstance = axios.create(config);

const axiosInstanceAuth = axios.create(config);
axiosInstanceAuth.defaults.withCredentials = true;

export { axiosInstance, axiosInstanceAuth };
