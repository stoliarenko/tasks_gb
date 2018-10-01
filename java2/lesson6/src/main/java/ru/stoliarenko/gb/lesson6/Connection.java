package ru.stoliarenko.gb.lesson6;

import java.io.*;
import java.net.Socket;

public final class Connection implements Closeable{
   private final Socket socket;
   private final ObjectInputStream in;
   private final ObjectOutputStream out;
   
   public Connection(Socket socket) throws IOException{
      super();
      this.socket = socket;
      this.in = new ObjectInputStream(socket.getInputStream());
      this.out = new ObjectOutputStream(socket.getOutputStream());
   }
   
   public void send(Message message) throws IOException{
      out.writeObject(message);
   }
   
   public Message recieve() throws ClassNotFoundException, IOException{
      return (Message)in.readObject();
   }

   @Override
   public void close() throws IOException {
      in.close();
      out.close();
      socket.close();
   }
   
}
