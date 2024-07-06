import AsyncStorage from '@react-native-async-storage/async-storage';
import jwtDecode from 'jwt-decode';

export async function setAccessToken(data) {
    await AsyncStorage.setItem('access_token', data);
}

export async function setRefreshToken(data) {
    await AsyncStorage.setItem('refresh_token', data);
}

export const readTokenFromStorage = async () => {
    try {
        const token = await AsyncStorage.getItem('access_token');
        return token;
    } catch (error) {
        console.error('Error reading token from AsyncStorage:', error);
        return null;
    }
};

export function isTokenExpired(token) {
    decodedToken = jwtDecode(token);
    if (decodedToken.exp < Date.now() / 1000) {
        return true;
    } else {
        return false;
    }
}