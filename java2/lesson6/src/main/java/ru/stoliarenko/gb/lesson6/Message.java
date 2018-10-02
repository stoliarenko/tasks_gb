package ru.stoliarenko.gb.lesson6;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Message implements Serializable{
    private @Getter MessageType type;
    private @Getter String text;
    
    public Message(MessageType type) {
        this.type = type;
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
