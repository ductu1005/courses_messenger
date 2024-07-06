import axios from 'axios';
import { groupChatStart, groupChatSuccess, groupChatFailed } from '../slices/groupChatSlice';
import { BASE_URL } from '../../constant/Constant';

export const getGroupChat = async (user, dispatch) => {
    dispatch(groupChatStart());
    try {
      console.log("user: " + user.id);
      const res = await axios.get(`${BASE_URL}/course/${user.id}`);
      console.log("getGroupChat:" + res.data.data);
      dispatch(groupChatSuccess(res.data.data));
      // await setRefreshToken(res.data.data.refreshToken);
      // navigate('ChatGroupsScreen');
    } catch (err) {
      dispatch(groupChatFailed());
      alert('Tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại!')
    }
  }