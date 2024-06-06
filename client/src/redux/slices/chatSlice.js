import { createSlice } from '@reduxjs/toolkit';

const chatSlice = createSlice({
    name: 'chat',
    initialState: {
        isFetching: false,
        chatGroupInfor: [],
        group: null,
    },
    reducers: {
        chatStart: (state) => {
            state.isFetching = true;
        },
        chatSuccess: (state, action) => {
            state.isFetching = false;
            state.chatGroupInfor = action.payload;
            console.log('Join chat Success!');
        },
        chatFailed: (state) => {
            state.isFetching = false;
            console.log('Join Chat Failed!');
        },
        /////Lưu thông tin nhóm lớp
        saveChatGroup: (state, action) => {
            state.group = action.payload;
        },
    },
});

export const { chatStart, chatSuccess, chatFailed, saveChatGroup } = chatSlice.actions;
export default chatSlice.reducer;