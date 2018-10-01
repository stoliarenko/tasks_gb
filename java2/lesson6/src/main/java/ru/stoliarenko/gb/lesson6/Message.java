package ru.stoliarenko.gb.lesson6;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public @AllArgsConstructor class Message implements Serializable{
   private @Getter MessageType type;
   private @Getter String text;
   
   public Message(MessageType type) {
      this.type = type;
   }
}
