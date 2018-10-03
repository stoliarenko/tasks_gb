package ru.stoliarenko.gb.lesson6;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import ru.stoliarenko.gb.lesson6.client.Client;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Pony Chat - Evolved.
 * Теперь работает с сетью.
 * 
 * @author Stoliarenko Alexander
 */

public class PonyChatClient extends JFrame{
    private Set<String> users = new TreeSet<>();
    /**
     * Используемая цветовая схема
     */
    private static final Color LOW_RED = new Color(0xBFF48FB1, true);//#F48FB1
    private static final Color HIGH_PURPLE = new Color(0x8E, 0x24, 0xAA);//#8E24AA
    private static final Color LOW_PURPLE = new Color(0xAB, 0x47, 0xBC);//#BA68C8
    
    private JTextArea messagesArea;
    private JTextArea usersArea;
    private JButton enterButton;
    private JTextField inputField;
    
    private final int MESSAGE_AREA_SIZE = 100;
    private final int USERS_AREA_SIZE = (int)(MESSAGE_AREA_SIZE * 0.2);
    
    public static void main( String[] args ) {
        PonyChatClient app = new PonyChatClient();
        Client client = new Client(app);
        app.setActionListeners(client);
        app.setVisible(true);
        client.run();//Да, именно run(), а не start().

    }
    
    private PonyChatClient() throws HeadlessException{
        useNimbus();
        setupFrames(setupBaseFrame());
    }
    
    /**
     * Обновляет отображение списка пользователей в соответствии с полем users
     */
    private void updateUsers() {
        StringBuffer sb = new StringBuffer();
        for (String user : users) {
            sb.append(user);
            sb.append("\n");
        }
        usersArea.setText(sb.toString().trim());
    }
    /**
     * Добавляет пользователя из параметра в общий список
     * @param user
     */
    public void addUser(String user) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(user == null) return;
                users.add(user);
                updateUsers();
            }
        });
    }
    /**
     * Удаляет пользователя из параметра из общего списка
     * @param user
     */
    public void deleteUser(String user) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(user == null) return;
                users.remove(user);
                updateUsers();
            }
        });
    }
    /**
     * Добавляет очередное сообщение в окно истории чата
     * @param text
     */
    public void showMessage(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                messagesArea.append(text + "\n");
            }
        });
    }
    /**
     * Связывает отображение и клиента
     * @param client
     */
    public void setActionListeners(Client client) {
        inputField.addActionListener((event)->{
            System.out.println(inputField.getText());
            client.sendMessage(inputField.getText());
            inputField.setText("");
        });
        enterButton.addActionListener(inputField.getActionListeners()[0]);
    }
    
    /**
     * Добавляет на переданную панель поле для ввода текста
     * 
     * @param mainPanel - базовая панель, должна иметь GridBagLayout
     * @return inputPanel - добавленное текстовое поле
     */
    private JTextField createInputField(JPanel mainPanel) {
        final JTextField inputField = new JTextField();
        GridBagConstraints inputFieldConstraints = new GridBagConstraints();
        
        inputFieldConstraints.fill = GridBagConstraints.BOTH;
        inputFieldConstraints.gridwidth = MESSAGE_AREA_SIZE;
        inputFieldConstraints.gridx = 0;
        inputFieldConstraints.gridy = MESSAGE_AREA_SIZE;
        inputFieldConstraints.ipady = 10;
        inputFieldConstraints.ipadx = 5;
        
        inputField.setBackground(LOW_PURPLE);
        inputField.setForeground(LOW_RED);
        inputField.setBorder(BorderFactory.createEtchedBorder());
        
        mainPanel.add(inputField, inputFieldConstraints);
        return inputField;
    }
    /**
     * Добавляет на переданную панель кнопку ввода
     * 
     * @param mainPanel - базовая панель, должна иметь GridBagLayout
     * @return enterButton - добавленная кнопка ввода
     */
    private JButton createEnterButton(JPanel mainPanel) {
        final JButton enterButton = new JButton("Enter", new ImageIcon("src/resources/Pinkie_Pie_small.png"));
        GridBagConstraints enterButtonConstraints = new GridBagConstraints();
        
        enterButtonConstraints.fill = GridBagConstraints.BOTH;
        enterButtonConstraints.gridwidth = USERS_AREA_SIZE;
        enterButtonConstraints.gridx = MESSAGE_AREA_SIZE;
        enterButtonConstraints.gridy = MESSAGE_AREA_SIZE;

        enterButton.setForeground(HIGH_PURPLE);
        
        mainPanel.add(enterButton, enterButtonConstraints);
        return enterButton;
    }
    /**
     * Добавляет на переданную панель панель с пользователями
     * 
     * @param mainPanel - базовая панель, должна иметь GridBagLayout
     * @return users - добавленная панель пользователей
     */
    private JTextArea createUsersArea(JPanel mainPanel) {
        final JTextArea users = new JTextArea();
        final JScrollPane usersScrollPane = new JScrollPane(users);
        GridBagConstraints usersConstraints = new GridBagConstraints();
        
        usersConstraints.anchor = GridBagConstraints.LINE_END;
        usersConstraints.fill = GridBagConstraints.BOTH;
        usersConstraints.gridwidth = USERS_AREA_SIZE;
        usersConstraints.gridheight = MESSAGE_AREA_SIZE;
        
        usersScrollPane.setBorder(null);
        users.setBackground(LOW_RED);
        users.setForeground(HIGH_PURPLE);
        users.setFont(users.getFont().deriveFont(Font.BOLD, users.getFont().getSize()+1));
        users.setBorder(BorderFactory.createTitledBorder("Users:"));
        users.setEditable(false);
        users.append("Alexander Radionovoch" + "\n");
        
        mainPanel.add(usersScrollPane, usersConstraints);
        return users;
    }
    /**
     * Добавляет на переданную палель основное окно чата
     * 
     * @param mainPanel - базовая панель, должна иметь GridBagLayout
     * @return chatHistory - добавленное окно чата
     */
    private JTextArea createMessagesArea(JPanel mainPanel) {
        final JTextArea chatHistory = new MyTextArea();
        final JScrollPane chatHistoryScrollPane = new JScrollPane(chatHistory);
        GridBagConstraints chatHistoryConstraints = new GridBagConstraints();
        
        chatHistoryConstraints.anchor =  GridBagConstraints.FIRST_LINE_START;
        chatHistoryConstraints.fill = GridBagConstraints.BOTH;
        chatHistoryConstraints.weightx = 1;
        chatHistoryConstraints.weighty = 1;
        chatHistoryConstraints.gridwidth = MESSAGE_AREA_SIZE;
        chatHistoryConstraints.gridheight = MESSAGE_AREA_SIZE;
        chatHistoryConstraints.gridx = 0;
        chatHistoryConstraints.gridy = 0;
        
        chatHistoryScrollPane.setBorder(null);
        chatHistory.setBackground(new Color(0xBfF48FB1, true));
        chatHistory.setForeground(LOW_PURPLE);
        chatHistory.setBorder(BorderFactory.createTitledBorder("Messages:"));
        chatHistory.setEditable(false);
        chatHistory.setAutoscrolls(true);
        
        mainPanel.add(chatHistoryScrollPane, chatHistoryConstraints);
        return chatHistory;
    }
    /**
     * Осуществляет предварительную настройку главного окна
     * @return basePanel - панель контента основного окна
     */
    private JPanel setupBaseFrame() {
        final ImageIcon icon = new ImageIcon("src/resources/Pinkie Pie.png");
        setIconImage(icon.getImage());
        setTitle("Pony Chat");
        setSize(924, 768);setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        final JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setBackground(LOW_PURPLE);
        setContentPane(basePanel);
        return basePanel;
    }
    /**
     * Добавляет элементы на панель контента
     * 
     * @param basePanel - панель контента основного окна
     */
    private void setupFrames(JPanel basePanel) {
        this.messagesArea = createMessagesArea(basePanel);
        this.usersArea = createUsersArea(basePanel);
        this.inputField = createInputField(basePanel);
        this.enterButton = createEnterButton(basePanel);
    }
    /**
     * По возможности применяет улучшенный нешний вид
     */
    private void useNimbus() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus is not awailable for you, poor creature!");
        }
    }
    /**
     * Класс, необходимый для отображения фоновой картинки
     */
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
