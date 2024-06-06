import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, Image } from 'react-native';
import { useDispatch, useSelector } from 'react-redux';
import { BASE_URL } from '../constant/Constant';
import axios from 'axios';
import { getGroupChat } from '../redux/actions/groupChatApi';
import { useNavigation } from '@react-navigation/native';
import { getAllMessageByGroup, getInfChat } from '../redux/actions/chatApi';
import { logoutUser } from '../redux/actions/authApi';

// Giả sử bạn có một danh sách các nhóm chat như sau

const ChatGroupsScreen = () => {

  //navigation
  const navigation = useNavigation();
  //function of navigate 
  const { navigate, goback } = navigation;

  // State để lưu danh sách nhóm chat
  const currentUser = useSelector((state) => state.auth.login.currentUser);
  const group = useSelector((state) => state.groupChatSlice.group);
  const [chatGroups, setChatGroups] = useState([]);

  const dispatch = useDispatch();

  // useEffect để khởi tạo danh sách nhóm chat khi màn hình được mount
  useEffect(() => {
    async function fetchData() {
      // console.log("ChatGroupsScreen: " + currentUser.id);
      if (currentUser != null) {
        getGroupChat(currentUser, dispatch);
      }
    }
    fetchData();

    // Trong trường hợp thực tế, bạn có thể gọi API để lấy danh sách nhóm chat từ server
    // setChatGroups(sampleChatGroups);
  }, [currentUser]);

  // Hàm xử lý khi người dùng chọn một nhóm chat
  const handleChatGroupPress = (item) => {
    // Điều hướng đến màn hình chat của nhóm đã chọn
    // navigate('Chat', { chatGroupId, chatGroupName });
    console.log("handleChatGroupPress: " + item.courseid);
    // getInfChat(item, dispatch, navigate);
    getAllMessageByGroup(item, dispatch, navigate);
  };

  // Render mỗi item trong danh sách nhóm chat
  const renderChatGroupItem = ({ item }) => (
    <TouchableOpacity
      style={styles.chatGroupItem}
      onPress={() => handleChatGroupPress(item)}
    >
      <View style={styles.groupInfo}>
        <Image
          style={styles.groupImage}
          source={{ uri: item.image }} // Sử dụng đường link ảnh từ item
        />
        <Text>{item.fullnameCourse}</Text>
      </View>
    </TouchableOpacity>
  );

  const handleLogout = () => {
    if (currentUser) {
      logoutUser(dispatch, navigate);
    }
    else alert('Bạn chưa đăng nhập!');
  }

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Chat Groups</Text>
      <FlatList
        data={group}
        keyExtractor={(item) => item.courseid}
        renderItem={renderChatGroupItem}
      />
      <TouchableOpacity
        onPress={() => { handleLogout() }}>
        <Text>Đăng xuất</Text>
        <View style={{ flex: 1 }} />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 16,
  },
  chatGroupItem: {
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  groupInfo: {
    flexDirection: 'row', // Xếp hàng theo chiều ngang
    alignItems: 'center', // Canh giữa theo chiều ngang
  },
  groupImage: {
    width: 50,
    height: 50, // Độ cao của ảnh
    borderRadius: 25, // Độ cong góc để tạo hình tròn
    marginRight: 10, // Khoảng cách giữa ảnh và văn bản
  },
});

export default ChatGroupsScreen;
