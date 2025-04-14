import axios from 'axios';
import { API_URL } from '../config';

class AuthService {
    async login(email, password) {
        try {
            const response = await axios.post(`${API_URL}/auth/login`, {
                email,
                password
            });
            
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('user', JSON.stringify(response.data.user));
            }
            
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    async register(userData) {
        try {
            const response = await axios.post(`${API_URL}/auth/register`, userData);
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    async getCurrentUser() {
        try {
            const token = this.getToken();
            if (!token) {
                throw new Error('Non authentifié');
            }

            const response = await axios.get(`${API_URL}/auth/me`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    async updateProfile(userData) {
        try {
            const token = this.getToken();
            if (!token) {
                throw new Error('Non authentifié');
            }

            const response = await axios.put(`${API_URL}/auth/profile`, userData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    async changePassword(currentPassword, newPassword) {
        try {
            const token = this.getToken();
            if (!token) {
                throw new Error('Non authentifié');
            }

            const response = await axios.put(
                `${API_URL}/auth/password`,
                { currentPassword, newPassword },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    async resetPassword(email) {
        try {
            const response = await axios.post(`${API_URL}/auth/reset-password`, { email });
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    async verifyResetToken(token) {
        try {
            const response = await axios.post(`${API_URL}/auth/verify-reset-token`, { token });
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    async setNewPassword(token, newPassword) {
        try {
            const response = await axios.post(`${API_URL}/auth/set-new-password`, {
                token,
                newPassword
            });
            return response.data;
        } catch (error) {
            throw this.handleError(error);
        }
    }

    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    }

    isAuthenticated() {
        return !!this.getToken();
    }

    getToken() {
        return localStorage.getItem('token');
    }

    getUser() {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    }

    handleError(error) {
        if (error.response) {
            const message = error.response.data.message || 'Une erreur est survenue lors de l\'authentification';
            const status = error.response.status;
            
            switch (status) {
                case 401:
                    this.logout();
                    return new Error('Session expirée. Veuillez vous reconnecter.');
                case 403:
                    return new Error('Accès non autorisé.');
                case 404:
                    return new Error('Service non trouvé.');
                case 422:
                    return new Error('Données invalides.');
                case 429:
                    return new Error('Trop de tentatives. Veuillez réessayer plus tard.');
                default:
                    return new Error(message);
            }
        }
        return error;
    }
}

export default new AuthService(); 