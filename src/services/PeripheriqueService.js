import axios from 'axios';
import { API_URL } from '../config';

class PeripheriqueService {
  async getPeripheriques() {
    try {
      const response = await axios.get(`${API_URL}/peripheriques`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async getPeripherique(id) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/${id}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async createPeripherique(peripherique) {
    try {
      const response = await axios.post(`${API_URL}/peripheriques`, peripherique);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async updatePeripherique(id, peripherique) {
    try {
      const response = await axios.put(`${API_URL}/peripheriques/${id}`, peripherique);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async deletePeripherique(id) {
    try {
      await axios.delete(`${API_URL}/peripheriques/${id}`);
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async getPeripheriquesParType(type) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/type/${type}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async getPeripheriquesParLocalisation(localisation) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/localisation/${localisation}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async getPeripheriquesParEtat(etat) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/etat/${etat}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async rechercherPeripheriques(nom) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/recherche?nom=${encodeURIComponent(nom)}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async getPeripheriquesParAppareil(appareilId) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/appareil/${appareilId}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async getPeripheriquesNonModifies(date) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/non-modifies?date=${date.toISOString()}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async countPeripheriquesParEtat(etat) {
    try {
      const response = await axios.get(`${API_URL}/peripheriques/count/etat/${etat}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  handleError(error) {
    if (error.response) {
      return new Error(error.response.data.message || 'Une erreur est survenue lors de l\'opération sur les périphériques');
    }
    return error;
  }
}

export default new PeripheriqueService(); 