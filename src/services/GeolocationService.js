import axios from 'axios';

class GeolocationService {
    static async getCoordinates(address) {
        try {
            // Utilisation de l'API Nominatim (OpenStreetMap) pour la géocodification
            const response = await axios.get(
                `https://nominatim.openstreetmap.org/search`,
                {
                    params: {
                        q: address,
                        format: 'json',
                        limit: 1
                    },
                    headers: {
                        'User-Agent': 'ParcInfoSolo-Web'
                    }
                }
            );

            if (response.data && response.data.length > 0) {
                return {
                    latitude: parseFloat(response.data[0].lat),
                    longitude: parseFloat(response.data[0].lon),
                    displayName: response.data[0].display_name
                };
            }
            throw new Error('Adresse non trouvée');
        } catch (error) {
            console.error('Erreur de géocodification:', error);
            throw error;
        }
    }

    static async getCurrentLocation() {
        return new Promise((resolve, reject) => {
            if (!navigator.geolocation) {
                reject(new Error('La géolocalisation n\'est pas supportée par votre navigateur'));
                return;
            }

            navigator.geolocation.getCurrentPosition(
                (position) => {
                    resolve({
                        latitude: position.coords.latitude,
                        longitude: position.coords.longitude
                    });
                },
                (error) => {
                    reject(error);
                },
                {
                    enableHighAccuracy: true,
                    timeout: 5000,
                    maximumAge: 0
                }
            );
        });
    }
}

export default GeolocationService; 