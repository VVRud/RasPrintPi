package client.ui;


import client.logic.PrintingData;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static client.Constants.*;

/**
 * Created by vvrud on 10.09.16.
 * @author VVRud
 * This class shows first login window in my soft.
 */
public class LoginWindow extends JFrame {

    private static String ip;
    private static int port;
    private static boolean connectionSuccess = false;
    private final JPanel loginWindow = new JPanel(new GridBagLayout());

    public LoginWindow() {
        super(TITLE);

        JTextField ipField = new JTextField(10);
        JTextField portField = new JTextField(5);
        JButton btnConnect = new JButton("CONNECT");

        //TODO открыть корректировку ввода
        createPanel(ipField, portField, btnConnect);
        //addTextCheck(ipField, portField);

        loginWindow.setBorder(BorderFactory.createLineBorder(Color.red));
        getContentPane().add(loginWindow, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Add button listener
        btnConnect.addActionListener(l -> {
            boolean ipPortSuccess;

            //Get IP and port from JTextFields
            ip = ipField.getText();
            try {
                port = Integer.parseInt(portField.getText());
                ipPortSuccess = true;
            } catch (NumberFormatException numErr) {
                JOptionPane.showMessageDialog(loginWindow, "ERROR. Can't find PORT. Check your PORT.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                ipPortSuccess = false;
            }

            //Try connect to socket
            if (ipPortSuccess) {
                //TODO УБРАТЬ ЗАГЛУШКУ ПЕРЕХОДА
                connectionSuccess = true;
                try {
                    PrintingData.setSocket(new Socket(ip, port));
                    connectionSuccess = true;
                    PrintingData.setDataOutput(new DataOutputStream(PrintingData.getSocket().getOutputStream()));
                    PrintingData.setDataInput(new DataInputStream(PrintingData.getSocket().getInputStream()));
                } catch (UnknownHostException hostErr) {
                    JOptionPane.showMessageDialog(loginWindow, "ERROR. Can't connect to server. Check your IP.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    connectionSuccess = false;
                } catch (IOException ioErr) {
                    JOptionPane.showMessageDialog(loginWindow, "ERROR. Can't connect to server. Check your PORT.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    connectionSuccess = false;
                }
            }

            //Set fields empty
            ipField.setText("");
            portField.setText("");
        });

    }

    public static boolean isConnectionSuccess() {
        return connectionSuccess;
    }

    static String getIp() {
        return ip;
    }

    static int getPort() {
        return port;
    }

    private void addTextCheck(JTextField ipField, JTextField portField) {
        //IP must contain only 123456789.
        ipField.setDocument(new PlainDocument() {
            String chars = "0123456789.";

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (chars.contains(str)) {
                    if (getLength() < 15) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });

        //Port must contain only 123456789
        portField.setDocument(new PlainDocument() {
            String chars = "0123456789";

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (chars.contains(str)) {
                    if (getLength() < 5) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });
    }

    private void createPanel(JTextField ipField, JTextField portField, JButton btnConnect) {
        //Add JLabel with text
        JLabel text = new JLabel("Enter server IP and PORT to connect");
        text.setFont(new Font("Arial", Font.BOLD, 14));
        loginWindow.add(text, new GridBagConstraints(0, 0, 3, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 1, 1));

        JLabel ipLabel = new JLabel("IP:");
        loginWindow.add(ipLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 0), 0, 0));
        loginWindow.add(ipField, new GridBagConstraints(1, 1, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        ipField.setToolTipText("Default IP is: " + DEFAULT_IP);

        JLabel portLabel = new JLabel("PORT:");
        loginWindow.add(portLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 0), 0, 0));
        loginWindow.add(portField, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        portField.setToolTipText("Default port is: " + DEFAULT_PORT);

        loginWindow.add(btnConnect, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        //Set correct sizes
        ipLabel.setPreferredSize(btnConnect.getPreferredSize());
        ipField.setPreferredSize(btnConnect.getPreferredSize());
        portLabel.setPreferredSize(btnConnect.getPreferredSize());
        portField.setPreferredSize(btnConnect.getPreferredSize());
    }
}
