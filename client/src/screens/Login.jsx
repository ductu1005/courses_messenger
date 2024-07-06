import React, { useState } from "react";
import { StyleSheet, Text, View, Button, TextInput, Image, SafeAreaView, TouchableOpacity, StatusBar, Alert, KeyboardAvoidingView } from "react-native";
import axios from "axios";
const backImage = require("../../assets/backImage.png");
import { BASE_URL } from "../constant/Constant";
import { useDispatch } from "react-redux";
// import jwt_decode from "jwt-decode";
import { loginUser } from "../redux/actions/authApi";
import { useNavigation } from "@react-navigation/native";

export default function Login({ props }) {

  // const [email, setEmail] = useState("");
  // const [password, setPassword] = useState("");
  const navigation = useNavigation();
  // //function of navigate
  const { navigate, goback } = navigation;

  const dispatch = useDispatch();

  const [user, setUser] = useState({
    userName: "",
    password: "",
  });

  const handleChange = (field, value) => {
    setUser({ ...user, [field]: value });
  };

  const onHandleLogin = async () => {
    if (user.userName !== "" && user.password !== "") {
      console.log("login: " + user.userName + " " + user.password);
      loginUser(user, dispatch, navigate);
    }
    // try {
    //   if (user.userName !== "" && user.password !== "") {
    //     console.log(user);
    //     const response = await axios.post(`${BASE_URL}/api/login`, user);
    //     const data = response.data;
    //     if (data.code === 200) {
    //       const accessToken = data.data.accessToken;
    //       const userLogin = jwtDecode(accessToken);
    //       dispatch({
    //         type: 'LOGIN_SUCCESS',
    //         payload: userLogin
    //       })
    //     }
    //   }
    // } catch (error) {
    //   if (error.response) {
    //     // The request was made, but the server responded with an error status
    //     console.error("Server error:", error.response.data);
    //     Alert.alert(error.response.data.message);
    //   } else if (error.request) {
    //     // The request was made, but no response was received
    //     console.error("No response from server");
    //     Alert.alert("Network error", "Please check your internet connection");
    //   } else {
    //     // Something happened in setting up the request that triggered an Error
    //     console.error("Request setup error:", error.message);
    //     Alert.alert("Request error", "Please try again later");
    //   }
    // }
  };


  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
    >
      <Image source={backImage} style={styles.backImage} />
      <View style={styles.whiteSheet} />
      <SafeAreaView style={styles.form}>
        {/* <Text style={styles.title}>Vui lòng nhập tài khoản và mật khẩu</Text> */}
        <TextInput
          style={styles.input}
          placeholder="Enter username"
          autoCapitalize="none"
          textContentType="username"
          value={user.userName}
          autoFocus={true}
          onChangeText={(text) => handleChange('userName', text)}
        />

        <TextInput
          style={styles.input}
          placeholder="Enter password"
          autoCapitalize="none"
          autoCorrect={false}
          secureTextEntry={true}
          textContentType="password"
          value={user.password}
          onChangeText={(text) => handleChange('password', text)}
        />
        <TouchableOpacity style={styles.button} onPress={onHandleLogin}>
          <Text style={{ fontWeight: 'bold', color: '#fff', fontSize: 18 }}> Đăng Nhập</Text>
        </TouchableOpacity>
        <View style={{ marginTop: 20, flexDirection: 'row', alignItems: 'center', alignSelf: 'center' }}>
          <Text style={{ color: 'gray', fontWeight: '600', fontSize: 12 }}>Chưa có tài khoản vui lòng liên hệ </Text>
          <TouchableOpacity 
          // onPress={() => navigation.navigate("Signup")}
          >
            <Text style={{ color: '#f57c00', fontWeight: '600', fontSize: 13 }}>PĐT!</Text>
          </TouchableOpacity>
        </View>
      </SafeAreaView>
      <StatusBar barStyle="light-content" />
    </KeyboardAvoidingView>
  );
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 12,
    // fontWeight: 'bold',
    color: "orange",
    alignSelf: "flex-start",
    paddingBottom: 10,
  },
  input: {
    backgroundColor: "#F6F7FB",
    height: 58,
    marginBottom: 20,
    fontSize: 16,
    borderRadius: 10,
    padding: 12,
  },
  backImage: {
    width: "100%",
    height: 340,
    position: "absolute",
    top: 0,
    resizeMode: 'cover',
  },
  whiteSheet: {
    width: '100%',
    height: '75%',
    position: "absolute",
    bottom: 0,
    backgroundColor: ' ',
    borderTopLeftRadius: 60,
    // paddingVertical: 20,
  },
  form: {
    flex: 1,
    justifyContent: 'center',
    marginHorizontal: 30,
  },
  button: {
    backgroundColor: '#f57c00',
    height: 58,
    borderRadius: 10,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20,
  },
});
