import axios from 'axios';
import { loginFailed, loginStart, loginSuccess, logoutStart, logoutSuccess, setUserAccessToken } from '../slices/authSlice';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useState } from "react";
import { BASE_URL } from '../../constant/Constant';
import { setAccessToken, setRefreshToken } from '../../constant/auth';
import { loginAccount } from '../slices/loginSlice';

export const loginUser = async (user, dispatch, navigate) => {
    dispatch(loginStart());
    try {
        console.log("user: " + user);
        const res = await axios.post(`${BASE_URL}/api/login`, user);
        console.log("authApi:" + res.data.data.accessToken);
        dispatch(loginSuccess(res.data.data));
        dispatch(loginAccount());
        await setAccessToken(res.data.data.accessToken);
        // await setRefreshToken(res.data.data.refreshToken);
        navigate('ChatGroupsScreen');
    } catch (err) {
        dispatch(loginFailed());
        alert('Tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại!')
    }
}

export const stayLogged = async (access_token, dispatch, navigate) => {
    dispatch(loginStart());
    try {
        // giải mã token 
        // decodedToken = jwtDecode(access_token);
        // console.log("stayLogged: ", access_token);
        // Lấy data người dùng
        const res = await axios.get(`${BASE_URL}/user`, {
            headers: {
                Authorization: `${access_token}`,
              },
        });
        console.log("stayLogged: ", res.data.data);
        if (res.data.data === null) {
            dispatch(loginFailed());
            navigate('Login');
        } else {
            await dispatch(loginAccount());
            await dispatch(loginSuccess(res.data.data));
            navigate('ChatGroupsScreen');
        }
    } catch (err) {
        dispatch(loginFailed());
        console.log('Error: ', err.message);
        navigate('Login');
    }
}

export const logoutUser = async (dispatch, navigate) => {
    dispatch(logoutStart());
    try {
        dispatch(logoutSuccess());
        dispatch(loginAccount());
        await setAccessToken('');
        // await setRefreshToken('');
        navigate('Login');
    } catch (err) {
        dispatch(loginFailed);
        alert('Đã xảy ra lỗi. Vui lòng thử lại!')
    }
}

// export const refreshToken = async (dispatch) => {
//     const [firstLogin, setFirstLogin] = useState('');
//     AsyncStorage.getItem('firstLogin').then((value) => {
//         setFirstLogin(value);
//     });

// }

// AsyncStorage.getItem('access_token').then((value) => {
//     console.log(value);
// });