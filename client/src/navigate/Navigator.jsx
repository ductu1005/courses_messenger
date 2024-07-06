import { NavigationContainer, useNavigation } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import Login from "../screens/Login";
import Chat from "../screens/Chat";
import { readTokenFromStorage } from "../constant/auth";
import { useEffect, useState } from "react";
import { stayLogged } from "../redux/actions/authApi";
import { useDispatch, useSelector } from "react-redux";
import ChatGroupsScreen from "../screens/ChatGroupsScreen";

const Stack = createNativeStackNavigator();
export default function Navigator(props) {
    const loginSuccess = useSelector((state) => state.loginSuccess.isLoginSuccess);
    const dispatch = useDispatch();
    const navigation = useNavigation();
    const [isLoading, setIsLoading] = useState(true);
    const { navigate, goback } = navigation;

    const checkToken = async () => {
        const userToken = await readTokenFromStorage();
        // console.log("token: " + userToken);
        if (userToken) {
            await stayLogged(userToken, dispatch, navigate);
        }
    };
    useEffect(() => {
        checkToken();
    }, []);

    return <Stack.Navigator screenOptions={{headerShown:false}}>
        <Stack.Screen name="ChatGroupsScreen" component={ChatGroupsScreen} />
        <Stack.Screen name="Login" component={Login} />
        <Stack.Screen name="Chat" component={Chat} />
    </Stack.Navigator>
}
