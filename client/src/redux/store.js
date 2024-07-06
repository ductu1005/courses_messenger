import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import loginReducer from './slices/loginSlice';
import groupChatSlice from './slices/groupChatSlice';
import chatSlice from './slices/chatSlice';

export default configureStore({
    reducer: {
        auth: authReducer,
        loginSuccess: loginReducer,
        groupChatSlice: groupChatSlice,
        chat: chatSlice
    }
});