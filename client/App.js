import { StatusBar } from 'expo-status-bar';
import React, { useState, createContext, useContext, useEffect } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { useSelector, Provider, useDispatch } from "react-redux";
import { NavigationAction, NavigationContainer, useNavigation } from '@react-navigation/native';

import Login from './src/screens/Login';
import store from './src/redux/store';
import { readTokenFromStorage } from './src/constant/auth';
import { Navigator } from './src/navigate';
import { stayLogged } from './src/redux/actions/authApi';

function RootNavigator() {
  const loginSuccess = useSelector((state) => state.loginSuccess.isLoginSuccess);
  //navigation
  const navigation = useNavigation();
  //function of navigate 
  const { navigate, goback } = navigation;
  const dispatch = useDispatch();
  useEffect(() => {
    const checkToken = async () => {
      const userToken = await readTokenFromStorage();
      console.log("token: " + userToken);
      if (userToken) {
        stayLogged(userToken, dispatch, navigate);
      }
    };

    checkToken();
  }, []);
  return (
    <NavigationContainer>
      {/* <ChatStack /> */}
      {loginSuccess ? <Navigator /> : <Login />}
    </NavigationContainer>
  );
}


export default function App() {
  return (
    <Provider store={store}>
      <NavigationContainer >
        <Navigator />
      </NavigationContainer>
      {/* <RootNavigator /> */}
    </Provider>

  );
}

