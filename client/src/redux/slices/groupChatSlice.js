import { createSlice } from '@reduxjs/toolkit';

const groupChatSlice = createSlice({
    name: 'groupChat',
    initialState: {
        isFetching: false,
        group: [],
        error: false,
    },
    reducers: {
        groupChatStart: (state) => {
            state.isFetching = true;
        },
        groupChatSuccess: (state, action) => {
            state.isFetching = false;
            state.group = action.payload;
            state.error = false;
            console.log('groupChat Success!');
        },
        groupChatFailed: (state) => {
            state.isFetching = false;
            state.error = true;
            console.log('groupChat Failed!');
        },
    },
});

export const { groupChatStart, groupChatSuccess, groupChatFailed } = groupChatSlice.actions;
export default groupChatSlice.reducer;