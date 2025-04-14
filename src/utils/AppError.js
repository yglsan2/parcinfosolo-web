export class AppError extends Error {
    constructor(message, status) {
        super(message);
        this.name = 'AppError';
        this.status = status;
        this.timestamp = new Date().toISOString();
    }

    toString() {
        return `${this.name} (${this.status}): ${this.message}`;
    }
} 