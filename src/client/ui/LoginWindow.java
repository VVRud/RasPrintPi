package client.ui;


import client.Client;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static client.data.Constants.*;

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
        JButton btnOffline = new JButton("OFFLINE");

        createPanel(ipField, portField, btnConnect, btnOffline);
        addTextCheck(ipField, portField);

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
                JOptionPane.showMessageDialog(loginWindow, PORT_NUM_ERR, ERR_TITLE, JOptionPane.ERROR_MESSAGE);
                ipPortSuccess = false;
            }

            //Try connect to socket
            if (ipPortSuccess) {
                try {
                    Client.setSocket(new Socket(ip, port));
                    connectionSuccess = true;

                    Socket socket = Client.getSocket();
                    DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());

                    Client.setDataOutput(dataOutput);
                    Client.setObjectOutput(new ObjectOutputStream(dataOutput));
                    Client.setObjectInput(new ObjectInputStream(new DataInputStream(socket.getInputStream())));
                } catch (UnknownHostException hostErr) {
                    JOptionPane.showMessageDialog(loginWindow, IP_ERR, ERR_TITLE, JOptionPane.ERROR_MESSAGE);
                    connectionSuccess = false;
                } catch (IOException ioErr) {
                    JOptionPane.showMessageDialog(loginWindow, PORT_ERR, ERR_TITLE, JOptionPane.ERROR_MESSAGE);
                    connectionSuccess = false;
                } catch (IllegalArgumentException ilArgErr) {
                    JOptionPane.showMessageDialog(loginWindow, PORT_ILLEGAL_ARG_ERR, ERR_TITLE, JOptionPane.ERROR_MESSAGE);
                    connectionSuccess = false;
                }
            }

            //Set fields empty
            ipField.setText("");
            portField.setText("");
        });

        btnOffline.addActionListener(l -> {
            ip = "OFFLINE MODE";
            port = 0;
            connectionSuccess = true;
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

    private void createPanel(JTextField ipField, JTextField portField, JButton btnConnect, JButton btnOffline) {
        //Add JLabel with text
        JLabel text = new JLabel("Enter server IP and PORT to connect");
        text.setFont(new Font("Arial", Font.BOLD, 14));
        loginWindow.add(text, new GridBagConstraints(0, 0, 3, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 1, 1));

        JLabel ipLabel = new JLabel("IP:");
        loginWindow.add(ipLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));
        loginWindow.add(ipField, new GridBagConstraints(1, 1, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));
        ipField.setToolTipText("Default IP is: " + DEFAULT_IP);

        JLabel portLabel = new JLabel("PORT:");
        loginWindow.add(portLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));
        loginWindow.add(portField, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));
        portField.setToolTipText("Default port is: " + DEFAULT_PORT);

        loginWindow.add(btnConnect, new GridBagConstraints(2, 2, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        loginWindow.add(btnOffline, new GridBagConstraints(0, 3, 3, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        //Set correct sizes
        ipLabel.setPreferredSize(btnConnect.getPreferredSize());
        ipField.setPreferredSize(btnConnect.getPreferredSize());
        portLabel.setPreferredSize(btnConnect.getPreferredSize());
        portField.setPreferredSize(btnConnect.getPreferredSize());
    }
}
