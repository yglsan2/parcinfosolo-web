import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export class ApiService {
    constructor(endpoint) {
        this.endpoint = endpoint;
        this.api = axios.create({
            baseURL: API_BASE_URL,
            headers: {
                'Content-Type': 'application/json',
            },
            withCredentials: true
        });
    }

    async getAll() {
        const response = await this.api.get(this.endpoint);
        return response.data;
    }

    async getById(id) {
        const response = await this.api.get(`${this.endpoint}/${id}`);
        return response.data;
    }

    async create(item) {
        const response = await this.api.post(this.endpoint, item);
        return response.data;
    }

    async update(id, item) {
        const response = await this.api.put(`${this.endpoint}/${id}`, item);
        return response.data;
    }

    async delete(id) {
        await this.api.delete(`${this.endpoint}/${id}`);
    }
} 