package ru.stoliarenko.gb.lesson4;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class App extends JFrame{
    public static void main( String[] args )    {
        App app = new App();
        app.setVisible(true);
    }
    private App() throws HeadlessException{
        setTitle("Pony Chat");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        useNimbus();
        
        final JButton exitButton = new JButton("Quit");
        exitButton.setSize(60, 40);
        exitButton.setToolTipText("EXIT");
        exitButton.addActionListener(event -> System.exit(0));
        
        final JPanel leftPanel = new JPanel();
        final JPanel rightPanel = new JPanel();
        final JPanel topPanel = new JPanel();
        final JPanel bottomPanel = new JPanel();
        final JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        mainPanel.setBackground(Color.CYAN);
        leftPanel.setBackground(Color.green);
        rightPanel.setBackground(Color.RED);
        topPanel.setBackground(Color.blue);
        bottomPanel.setBackground(Color.MAGENTA);
        
        bottomPanel.add(exitButton, BorderLayout.LINE_END);
    }
    private void useNimbus() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }
}
