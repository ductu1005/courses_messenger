import React, { useState, useEffect, useRef } from 'react';
import { View, Text, TextInput, Button, StyleSheet, ScrollView } from 'react-native';
import { useDispatch, useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { BASE_URL } from '../constant/Constant';
import { useNavigation } from '@react-navigation/native';
import { readTokenFromStorage } from '../constant/auth';
import { stayLogged } from '../redux/actions/authApi';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { saveInfChat, saveMessage } from '../redux/actions/chatApi';

const Chat = ({ chatGroupId, chatGroupName }) => {

  //navigation
  const navigation = useNavigation();
  //function of navigate 
  const { navigate, goback } = navigation;

  const currentUser = useSelector((state) => state.auth.login.currentUser);
  const messageChat = useSelector((state) => state.chat.chatGroupInfor);
  const group = useSelector((state) => state.chat.group);
  const [stompClient, setStompClient] = useState(null);
  const [publicChats, setPublicChats] = useState([]);
  const [privateChats, setPrivateChats] = useState(new Map());
  const [inputMessage, setInputMessage] = useState('');
  const privateTabRef = useRef(null);
  const scrollViewRef = useRef();
  const dispatch = useDispatch();
  const [messageHistory, setMessageHistory] = useState([]);

  // useEffect(() => {
  //   const checkToken = async () => {
  //     const userToken = await readTokenFromStorage();
  //     console.log("token: " + userToken);
  //     if (userToken) {
  //       stayLogged(userToken, dispatch, navigate);
  //     }
  //   };

  //   checkToken();
  // }, []);

  useEffect(() => {
    if (currentUser != null) {
      fetchPreviousMessages();
      console.log("fetchPreviousMessages: " + publicChats);
      connectToWebSocket();
      console.log("Chat currentUser " + currentUser.username);
      console.log("Chat currentUser " + chatGroupId, chatGroupName);
      return () => {
        disconnectFromWebSocket();
      };
    }
  }, [currentUser]);

  const fetchPreviousMessages = async () => {
    try {
      if (messageChat != null) { setPublicChats(messageChat); }
    } catch (error) {
      console.error('Error fetching previous messages:', error);
    }
  };

  const connectToWebSocket = () => {
    if (currentUser) {
      const socket = new SockJS(BASE_URL + '/ws');
      console.log("{connectToWebSocket} socket: " + socket);
      const stomp = Stomp.over(socket);
      console.log("{connectToWebSocket} stomp: " + stomp);

      stomp.connect({}, (frame) => {
        console.log("{connect}: " + frame);
        stomp.subscribe('/chatroom/public', (message) => {
          handlePublicMessage(JSON.parse(message.body));
          console.log("{handlePublicMessage}: " + message.body);
        });

        console.log("{connectToWebSocket} stomp1: " + stomp);
        // stomp.subscribe(`/user/${currentUser.username}/private`, (message) => {
        //   handlePrivateMessage(JSON.parse(message.body));
        // });

        // const joinMessage = {
        //   senderName: currentUser.username,
        //   status: 'JOIN',
        // };
        // stomp.send('/app/message', {}, JSON.stringify(joinMessage));

        setStompClient(stomp);
      });
    }
  };

  const disconnectFromWebSocket = () => {
    if (stompClient) {
      stompClient.disconnect();
    }
  };

  // useEffect(() => {
  //   // Load lịch sử tin nhắn từ AsyncStorage khi component mount
  //   const loadMessageHistory = async () => {
  //     try {
  //       const storedHistory = await AsyncStorage.getItem('messageHistory');
  //       if (storedHistory) {
  //         setMessageHistory(JSON.parse(storedHistory));
  //       }
  //     } catch (error) {
  //       console.error('Error loading message history:', error);
  //     }
  //   };

  //   loadMessageHistory();
  // }, []);

  const saveMessageToHistory = async (message) => {
    // Lưu tin nhắn mới vào lịch sử và AsyncStorage
    setMessageHistory((prevHistory) => [...prevHistory, message]);
    try {
      await AsyncStorage.setItem('messageHistory', JSON.stringify(messageHistory));
    } catch (error) {
      console.error('Error saving message history:', error);
    }
  };

  const handlePublicMessage = (message) => {
    setPublicChats((prevChats) => [...prevChats, message]);
    // setPublicChats((prevChats) => [...prevChats, messageChat]);
    // console.log("messageChat: " + messageChat, message);
    // saveMessageToHistory(message); 
  };

  const handlePrivateMessage = (message) => {
    const senderName = message.senderName;
    setPrivateChats((prevChats) => {
      const updatedChats = new Map(prevChats);
      if (!updatedChats.has(senderName)) {
        updatedChats.set(senderName, []);
      }
      updatedChats.get(senderName).push(message);
      return updatedChats;
    });
  };

  const sendMessage = () => {
    // console.log("{sendMessage}: " + inputMessage, stompClient);
    if (stompClient && inputMessage.trim() !== '') {
      const message = {
        groupId: group.courseid,
        userId: currentUser.id,
        senderName: currentUser.username,
        message: inputMessage,
        status: 'MESSAGE',
      };
      stompClient.send('/app/message', {}, JSON.stringify(message));
      // saveInfChat(group, message);
      saveMessage(group, message);
      setInputMessage('');
    }
  };

  const sendPrivateMessage = () => {
    if (stompClient && inputMessage.trim() !== '') {
      const message = {
        senderName: currentUser.username,
        receiverName: privateTabRef.current.value,
        message: inputMessage,
        status: 'MESSAGE',
      };
      stompClient.send('/app/private-message', {}, JSON.stringify(message));
      setInputMessage('');
    }
  };

  const handleInputChange = (e) => {
    console.log("Message received: " + e);
    setInputMessage(e);
  };

  const handlePrivateTabChange = (e) => {
    privateTabRef.current = e.target.value;
  };

  useEffect(() => {
    scrollViewRef.current.scrollToEnd({ animated: true });
  }, [publicChats]);

  return (
    <View style={styles.chatContainer}>
      <ScrollView
        contentContainerStyle={styles.scrollContainer}
        ref={scrollViewRef}
        onContentSizeChange={() => scrollViewRef.current.scrollToEnd({ animated: true })}
      >
        <View style={styles.publicChats}>
          {publicChats.map((chat, index) => (
            <View
              key={index}
              style={[
                styles.textChat,
                chat.senderName === currentUser.username ? styles.rightChat : styles.leftChat,
              ]}
            >
              <Text style={styles.chatText}>
                <Text style={styles.senderName}>{chat.senderName}:</Text> {chat.message}
              </Text>
            </View>
          ))}
        </View>
      </ScrollView>

      <View style={styles.inputSection}>
        {/* Input field for typing messages */}
        <TextInput
          style={styles.inputField}
          value={inputMessage}
          onChangeText={handleInputChange}
          placeholder="Type your message..."
        />

        {/* Button to send public message */}
        <Button style={styles.button} title="Send" onPress={sendMessage} />
      </View>
    </View>
  );
};


export default Chat;

const styles = StyleSheet.create({
  chatContainer: {
    flex: 1,
    padding: 10,
  },
  publicChats: {
    // flex: 1,
    flexDirection: 'column',
  },
  inputSection: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  inputField: {
    flex: 1,
    marginRight: 10,
    borderWidth: 1,
    padding: 5,
    borderRadius: 10,
  },
  button: {
    borderRadius: 8,
  },
  scrollContainer: {
    flexGrow: 1,
  },
  textChat: {
    marginVertical: 5,
    marginHorizontal: 10,
    padding: 10,
    borderRadius: 20,
    maxWidth: '70%',
    // fontWeight: 'bold',
  },
  leftChat: {
    alignSelf: 'flex-start',
    backgroundColor: '#e0e0e0', // Background color for received messages
  },
  rightChat: {
    alignSelf: 'flex-end',
    backgroundColor: '#0099FF', // Background color for your messages
  },
  chatText: {
    fontSize: 16,
  },
  senderName: {
    fontWeight: 'bold',
    marginRight: 5,
  },
});
