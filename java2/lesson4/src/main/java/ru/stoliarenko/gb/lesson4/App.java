package ru.stoliarenko.gb.lesson4;

import javax.swing.*;
import java.awt.*;


public class App extends JFrame{
    public static void main( String[] args )    {
        App app = new App();
        app.setVisible(true);
    }
    private App() {
        setTitle("Pony Chat");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
