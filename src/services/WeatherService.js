import axios from 'axios';

class WeatherService {
    static API_KEY = process.env.REACT_APP_OPENWEATHER_API_KEY;
    static BASE_URL = 'https://api.openweathermap.org/data/2.5';

    static async getCurrentWeather(latitude, longitude) {
        try {
            const response = await axios.get(`${this.BASE_URL}/weather`, {
                params: {
                    lat: latitude,
                    lon: longitude,
                    appid: this.API_KEY,
                    units: 'metric',
                    lang: 'fr'
                }
            });

            return {
                temperature: Math.round(response.data.main.temp),
                feelsLike: Math.round(response.data.main.feels_like),
                description: response.data.weather[0].description,
                icon: response.data.weather[0].icon,
                humidity: response.data.main.humidity,
                windSpeed: Math.round(response.data.wind.speed * 3.6), // Conversion en km/h
                cityName: response.data.name,
                country: response.data.sys.country,
                sunrise: new Date(response.data.sys.sunrise * 1000),
                sunset: new Date(response.data.sys.sunset * 1000)
            };
        } catch (error) {
            console.error('Erreur lors de la récupération de la météo:', error);
            throw error;
        }
    }

    static async getForecast(latitude, longitude) {
        try {
            const response = await axios.get(`${this.BASE_URL}/forecast`, {
                params: {
                    lat: latitude,
                    lon: longitude,
                    appid: this.API_KEY,
                    units: 'metric',
                    lang: 'fr'
                }
            });

            return response.data.list.map(item => ({
                date: new Date(item.dt * 1000),
                temperature: Math.round(item.main.temp),
                description: item.weather[0].description,
                icon: item.weather[0].icon,
                humidity: item.main.humidity,
                windSpeed: Math.round(item.wind.speed * 3.6)
            }));
        } catch (error) {
            console.error('Erreur lors de la récupération des prévisions:', error);
            throw error;
        }
    }

    static getWeatherIconUrl(iconCode) {
        return `http://openweathermap.org/img/wn/${iconCode}@2x.png`;
    }
}

export default WeatherService; 