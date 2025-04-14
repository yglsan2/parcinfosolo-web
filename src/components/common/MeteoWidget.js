import React, { useState, useEffect } from 'react';
import WeatherService from '../../services/WeatherService';
import GeolocationService from '../../services/GeolocationService';
import './MeteoWidget.scss';

const MeteoWidget = ({ latitude, longitude, nom, adresse }) => {
    const [meteo, setMeteo] = useState(null);
    const [forecast, setForecast] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [location, setLocation] = useState(null);

    useEffect(() => {
        const fetchMeteoData = async () => {
            try {
                setLoading(true);
                setError(null);

                // Si on a une adresse mais pas de coordonnées, on fait la géocodification
                let coords = { latitude, longitude };
                if (adresse && (!latitude || !longitude)) {
                    const geoResult = await GeolocationService.getCoordinates(adresse);
                    coords = {
                        latitude: geoResult.latitude,
                        longitude: geoResult.longitude
                    };
                    setLocation(geoResult.displayName);
                }

                // Récupération de la météo actuelle
                const currentWeather = await WeatherService.getCurrentWeather(
                    coords.latitude,
                    coords.longitude
                );
                setMeteo(currentWeather);

                // Récupération des prévisions
                const forecastData = await WeatherService.getForecast(
                    coords.latitude,
                    coords.longitude
                );
                setForecast(forecastData);

            } catch (err) {
                console.error('Erreur lors de la récupération des données météo:', err);
                setError(err.message || "Impossible de récupérer les données météorologiques");
            } finally {
                setLoading(false);
            }
        };

        fetchMeteoData();
    }, [latitude, longitude, adresse]);

    if (loading) {
        return (
            <div className="meteo-widget loading">
                <i className="fas fa-spinner fa-spin"></i>
                <p>Chargement des données météo...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="meteo-widget error">
                <i className="fas fa-exclamation-circle"></i>
                <p>{error}</p>
            </div>
        );
    }

    if (!meteo) {
        return null;
    }

    return (
        <div className="meteo-widget">
            <h3>Météo pour {nom}</h3>
            <div className="meteo-content">
                <div className="meteo-icon">
                    <img 
                        src={WeatherService.getWeatherIconUrl(meteo.icon)} 
                        alt={meteo.description} 
                    />
                </div>
                <div className="meteo-details">
                    <p className="temperature">{meteo.temperature}°C</p>
                    <p className="feels-like">Ressenti: {meteo.feelsLike}°C</p>
                    <p className="description">{meteo.description}</p>
                    <p className="location">
                        <i className="fas fa-map-marker-alt"></i> 
                        {location || meteo.cityName}
                    </p>
                    <div className="additional-info">
                        <p>
                            <i className="fas fa-tint"></i>
                            Humidité: {meteo.humidity}%
                        </p>
                        <p>
                            <i className="fas fa-wind"></i>
                            Vent: {meteo.windSpeed} km/h
                        </p>
                    </div>
                    <div className="sun-info">
                        <p>
                            <i className="fas fa-sunrise"></i>
                            Lever: {meteo.sunrise.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' })}
                        </p>
                        <p>
                            <i className="fas fa-sunset"></i>
                            Coucher: {meteo.sunset.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' })}
                        </p>
                    </div>
                </div>
            </div>
            {forecast && (
                <div className="forecast">
                    <h4>Prévisions sur 5 jours</h4>
                    <div className="forecast-list">
                        {forecast.slice(0, 5).map((item, index) => (
                            <div key={index} className="forecast-item">
                                <p className="date">
                                    {item.date.toLocaleDateString('fr-FR', { weekday: 'short' })}
                                </p>
                                <img 
                                    src={WeatherService.getWeatherIconUrl(item.icon)} 
                                    alt={item.description} 
                                />
                                <p className="temp">{item.temperature}°C</p>
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
};

export default MeteoWidget; 