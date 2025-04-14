import axios from 'axios';
import { AppError } from '../utils/AppError';

const API_URL = 'http://localhost:8080/api';

class AppareilService {
    constructor() {
        this.api = axios.create({
            baseURL: API_URL,
            timeout: 10000,
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Intercepteur pour ajouter le token aux requêtes
        this.api.interceptors.request.use(
            (config) => {
                const token = localStorage.getItem('token');
                if (token) {
                    config.headers.Authorization = `Bearer ${token}`;
                }
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );
    }

    async getAllAppareils() {
        try {
            const response = await this.api.get('/appareils');
            return response.data;
        } catch (error) {
            if (error.response) {
                throw new AppError(
                    error.response.data.message || 'Erreur lors de la récupération des appareils',
                    error.response.status
                );
            }
            throw error;
        }
    }

    async getAppareilById(id) {
        try {
            const response = await this.api.get(`/appareils/${id}`);
            return response.data;
        } catch (error) {
            if (error.response) {
                throw new AppError(
                    error.response.data.message || 'Erreur lors de la récupération de l\'appareil',
                    error.response.status
                );
            }
            throw error;
        }
    }

    async createAppareil(appareilData) {
        try {
            const response = await this.api.post('/appareils', appareilData);
            return response.data;
        } catch (error) {
            if (error.response) {
                throw new AppError(
                    error.response.data.message || 'Erreur lors de la création de l\'appareil',
                    error.response.status
                );
            }
            throw error;
        }
    }

    async updateAppareil(id, appareilData) {
        try {
            const response = await this.api.put(`/appareils/${id}`, appareilData);
            return response.data;
        } catch (error) {
            if (error.response) {
                throw new AppError(
                    error.response.data.message || 'Erreur lors de la mise à jour de l\'appareil',
                    error.response.status
                );
            }
            throw error;
        }
    }

    async deleteAppareil(id) {
        try {
            await this.api.delete(`/appareils/${id}`);
        } catch (error) {
            if (error.response) {
                throw new AppError(
                    error.response.data.message || 'Erreur lors de la suppression de l\'appareil',
                    error.response.status
                );
            }
            throw error;
        }
    }
}

export const appareilService = new AppareilService(); 