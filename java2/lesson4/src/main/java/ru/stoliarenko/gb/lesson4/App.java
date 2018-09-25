package ru.stoliarenko.gb.lesson4;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class App extends JFrame{
    private final static Color HIGH_RED = new Color(0xF0, 0x62, 0x92);
    private final static Color LOW_RED = new Color(0xBFF48FB1);//#F48FB1
    private final static Color HIGH_PURPLE = new Color(0x8E, 0x24, 0xAA);//#8E24AA
    private final static Color LOW_PURPLE = new Color(0xAB, 0x47, 0xBC);//#BA68C8
    private final int MAIN_WONDOW_SIZE = 100;
    private final int USERS_WINDOW_SIZE = (int)(MAIN_WONDOW_SIZE * 0.2);
    
    public static void main( String[] args ) {
        App app = new App();
        app.setVisible(true);
    }
    
    private App() throws HeadlessException{
        setupBaseFrame();
        useNimbus();
        
        final JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setBackground(LOW_PURPLE);
        setContentPane(basePanel);
        
        
        final JTextArea chatHistory = new MyTextArea();
        final JScrollPane chatHistoryScrollPane = new JScrollPane(chatHistory);
        final JTextArea users = new JTextArea();
        final JScrollPane usersScrollPane = new JScrollPane(users);
        final JButton enterButton = new JButton("Enter", new ImageIcon("src/resources/Pinkie_Pie_small.png"));
        final JTextField inputField = new JTextField();
        
        GridBagConstraints chatHistoryConstraints = new GridBagConstraints();
        GridBagConstraints usersConstraints = new GridBagConstraints();
        GridBagConstraints enterButtonConstraints = new GridBagConstraints();
        GridBagConstraints inputFieldConstraints = new GridBagConstraints();
////////////////////////////////////////////////////////////////////////////// 
        chatHistoryConstraints.anchor =  GridBagConstraints.FIRST_LINE_START;
        chatHistoryConstraints.fill = GridBagConstraints.BOTH;
        chatHistoryConstraints.weightx = 1;
        chatHistoryConstraints.weighty = 1;
        chatHistoryConstraints.gridwidth = MAIN_WONDOW_SIZE;
        chatHistoryConstraints.gridheight = MAIN_WONDOW_SIZE;
        chatHistoryConstraints.gridx = 0;
        chatHistoryConstraints.gridy = 0;
        
        chatHistoryScrollPane.setBorder(null);
        chatHistoryScrollPane.setBackground(new Color(0xFfF48FB1, true));
        chatHistory.setBackground(new Color(0xBfF48FB1, true));
        chatHistory.setForeground(LOW_PURPLE);
        chatHistory.setBorder(BorderFactory.createTitledBorder("Messages:"));
        chatHistory.setEditable(false);
        chatHistory.setAutoscrolls(true);
///////////////////////////////////////////////////////////////////////////////        
        enterButtonConstraints.fill = GridBagConstraints.BOTH;
        enterButtonConstraints.gridwidth = USERS_WINDOW_SIZE;
        enterButtonConstraints.gridx = MAIN_WONDOW_SIZE;
        enterButtonConstraints.gridy = MAIN_WONDOW_SIZE;

        enterButton.setForeground(HIGH_PURPLE);
        enterButton.addActionListener((event)->{
            System.out.println(inputField.getText());
            chatHistory.append(inputField.getText() + "\n");
            inputField.setText("");
        });
///////////////////////////////////////////////////////////////////////////////
        usersConstraints.anchor = GridBagConstraints.LINE_END;
        usersConstraints.fill = GridBagConstraints.BOTH;
        usersConstraints.gridwidth = USERS_WINDOW_SIZE;
        usersConstraints.gridheight = MAIN_WONDOW_SIZE;
        
        usersScrollPane.setBorder(null);
        users.setBackground(LOW_RED);
        users.setForeground(HIGH_PURPLE);
        users.setFont(users.getFont().deriveFont(Font.BOLD, users.getFont().getSize()+1));
        users.setBorder(BorderFactory.createTitledBorder("Users:"));
        users.setEditable(false);
        users.append("Alexander Radionovoch" + "\n");
///////////////////////////////////////////////////////////////////////////////
        inputFieldConstraints.fill = GridBagConstraints.BOTH;
        inputFieldConstraints.gridwidth = MAIN_WONDOW_SIZE;
        inputFieldConstraints.gridx = 0;
        inputFieldConstraints.gridy = MAIN_WONDOW_SIZE;
        inputFieldConstraints.ipady = 10;
        
        inputField.setBackground(LOW_PURPLE);
        inputField.setForeground(LOW_RED);
        inputField.addActionListener((event)->{
            System.out.println(inputField.getText());
            chatHistory.append(inputField.getText() + "\n");
            inputField.setText("");
        });
        inputField.setBorder(BorderFactory.createEtchedBorder());
///////////////////////////////////////////////////////////////////////////////
        
        
        basePanel.add(chatHistoryScrollPane, chatHistoryConstraints);
        basePanel.add(usersScrollPane, usersConstraints);
        basePanel.add(enterButton, enterButtonConstraints);
        basePanel.add(inputField, inputFieldConstraints);
    }
    private void setupBaseFrame() {
        final ImageIcon icon = new ImageIcon("src/resources/Pinkie Pie.png");
        setIconImage(icon.getImage());
        setTitle("Pony Chat");
        setSize(924, 768);setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
    private class MyTextArea extends JTextArea{
        private Image img;
        
        public MyTextArea() {
            super();
            try{
                img = ImageIO.read(new File("src/resources/big_pony_small.png"));
            } catch(IOException e) {
                System.out.println(e.toString());
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            graphics.drawImage(img, 50, 50, null);
            super.paintComponent(graphics);
        }
        
    }
}
