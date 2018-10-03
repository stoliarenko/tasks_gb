package ru.stoliarenko.gb.lesson6;

import java.io.Serializable;


public class Message implements Serializable{
    private final MessageType type;
    private final String text;
    
    public Message(MessageType type) {
        this.type = type;
        this.text = null;
    }
    public Message(MessageType type, String text) {
        this.type = type;
        this.text = text;
    }
    public MessageType getType() {
        return type;
    }
    public String getText() {
        return text;
    }
}
