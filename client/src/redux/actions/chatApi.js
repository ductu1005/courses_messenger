import axios from 'axios';
import { BASE_URL } from '../../constant/Constant';
import { chatFailed, chatStart, chatSuccess, saveChatGroup } from '../slices/chatSlice';

export const getInfChat = async (data, dispatch, navigate) => {
  dispatch(chatStart());
  try {
    console.log("getInfChat: " + data);
    const res = await axios.get(`${BASE_URL}/messages-chat/${data.courseid}`);
    console.log("getGroupChat:" + res.data.data, data.courseid);
    dispatch(chatSuccess(res.data.data));
    dispatch(saveChatGroup(data));
    navigate('Chat');
    // await setRefreshToken(res.data.data.refreshToken);
    // navigate('ChatGroupsScreen');
  } catch (err) {
    dispatch(chatFailed());
    alert('Get message fail!')
  }
}

export const saveInfChat = async (data, message) => {
  // dispatch(chatStart());
  try {
    console.log("getInfChat: " + data);
    const res = await axios.post(`${BASE_URL}/messages-chat/${data.courseid}`, message);
    // console.log("getGroupChat:" + res.data.data, data.courseid);
    // dispatch(chatSuccess(res.data.data));
    // navigate('Chat');
    // await setRefreshToken(res.data.data.refreshToken);
    // navigate('ChatGroupsScreen');
  } catch (err) {
    // dispatch(chatFailed());
    alert('Save message fail!')
  }
}

export const getAllMessageByGroup = async (data, dispatch, navigate) => {
  dispatch(chatStart());
  try {
    console.log("getInfChat: " + data);
    const res = await axios.get(`${BASE_URL}/messages-info/${data.courseid}`);
    console.log("getGroupChat:" + res.data.data, data.courseid);
    dispatch(chatSuccess(res.data.data));
    dispatch(saveChatGroup(data));
    navigate('Chat');
    // await setRefreshToken(res.data.data.refreshToken);
    // navigate('ChatGroupsScreen');
  } catch (err) {
    dispatch(chatFailed());
    alert('Get message fail!')
  }
}

export const saveMessage = async (data, message) => {
  // dispatch(chatStart());
  try {
    console.log("getInfChat: " + data);
    const res = await axios.post(`${BASE_URL}/messages-info/add`, message);
    // console.log("getGroupChat:" + res.data.data, data.courseid);
    // dispatch(chatSuccess(res.data.data));
    // navigate('Chat');
    // await setRefreshToken(res.data.data.refreshToken);
    // navigate('ChatGroupsScreen');
  } catch (err) {
    // dispatch(chatFailed());
    alert('Save message fail!')
  }
}