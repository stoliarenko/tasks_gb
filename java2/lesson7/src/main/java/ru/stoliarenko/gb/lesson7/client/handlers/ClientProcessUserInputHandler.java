package ru.stoliarenko.gb.lesson7.client.handlers;

import java.io.DataOutputStream;
import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ProcessUserInputEvent;
import ru.stoliarenko.gb.lesson7.client.services.ClientLogger;
import ru.stoliarenko.gb.lesson7.model.MessageTextBroadcast;
import ru.stoliarenko.gb.lesson7.model.MessageTextPrivate;
import ru.stoliarenko.gb.lesson7.model.MessageUserLogin;

@ApplicationScoped
public final class ClientProcessUserInputHandler {
    @Inject
    private Client client;
    
    @SneakyThrows
    public void processUserInput(@ObservesAsync ProcessUserInputEvent event) {
        if ("login".equals(event.getText().toLowerCase())) {
            ClientLogger.writeMessage("Logging in");
            MessageUserLogin message = new MessageUserLogin();
            message.setLogin("admin");
            message.setPassword("123456");
            
            final ObjectMapper mapper = new ObjectMapper();
            final DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
            out.writeUTF(mapper.writeValueAsString(message));
            return;
        }
        if(event.getText().toLowerCase().startsWith("/w")) {
            final String targetUser = event.getText().split(" ")[1];
            final String text = event.getText().substring(3 + targetUser.length());
            final MessageTextPrivate message = new MessageTextPrivate();
            message.setText(text);
            message.setUsername(targetUser);
            
            final ObjectMapper mapper = new ObjectMapper();
            final DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
            out.writeUTF(mapper.writeValueAsString(message));
            return;
        }
        final MessageTextBroadcast message = new MessageTextBroadcast();
        message.setText(event.getText());
        
        final ObjectMapper mapper = new ObjectMapper();
        final DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
        out.writeUTF(mapper.writeValueAsString(message));
    }
}
