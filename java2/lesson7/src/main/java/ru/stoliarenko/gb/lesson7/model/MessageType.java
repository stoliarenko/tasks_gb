package ru.stoliarenko.gb.lesson7.model;

public enum MessageType {
    USER_LOGIN,
    USER_LOGOUT,
    USER_REGISTER,
    
    USER_ACCEPTED,
    USER_REJECTED,
    
    USER_CONNECTED,
    USER_DISCONNECTED,
    
    MESSAGE_PRIVATE,
    MESSAGE_BROADCAST,
    
    USER_OFFLINE,
    USER_NOT_EXIST;
}
