package ru.stoliarenko.gb.lesson7.service;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Message;

@ApplicationScoped
public class MessageConverter {
    private final ObjectMapper mapper = new ObjectMapper();
    
    @SneakyThrows
    public String convertToString(Object message) {
        return mapper.writeValueAsString(message);
    }
    
    @SneakyThrows
    public <T extends Message> T convertToMessage(String textMessage, Class<T> clazz){
        return (T) mapper.readValue(textMessage, clazz);
    }
}
