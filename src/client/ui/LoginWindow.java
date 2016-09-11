package client.ui;


import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.*;

import static client.Constants.*;

/**
 * Created by vvrud on 10.09.16.
 * This class shows first login window in my soft.
 */
public class LoginWindow extends JFrame {

    private static String ip;
    private static int port;

    public LoginWindow() {
        super(PROG_TITLE);

        final JPanel content = new JPanel(new GridBagLayout());

        JLabel text = new JLabel("Enter server IP and PORT to connect");
        text.setFont(new Font("Arial", Font.BOLD, 16));
        content.add(text, new GridBagConstraints(0, 0, 3, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 1, 1));

        JLabel ipLabel = new JLabel("IP:");
        JTextField ipField = new JTextField(10);

        content.add(ipLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 0), 0, 0));
        content.add(ipField, new GridBagConstraints(1, 1, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        ipField.setDocument(new PlainDocument() {
            String chars = "0123456789.";
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (chars.contains(str)) {
                    if (getLength()< 15) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });

        JLabel portLabel = new JLabel("PORT:");
        JTextField portField = new JTextField(5);

        content.add(portLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 0), 0, 0));
        content.add(portField, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        portField.setDocument(new PlainDocument() {
            String chars = "0123456789";
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (chars.contains(str)) {
                    if (getLength()< 5) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });

        JButton btnConnect = new JButton("CONNECT");
        content.add(btnConnect, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        content.setBorder(BorderFactory.createLineBorder(Color.red));
        getContentPane().add(content, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnConnect.addActionListener(l -> {
            boolean ipPortSuccess = true;
            boolean connectionSuccess = true;

            ip = ipField.getText();
            try {
                port = Integer.parseInt(portField.getText());
                ipPortSuccess = true;
            } catch (NumberFormatException numErr) {
                JOptionPane.showMessageDialog(content, "ERROR. Can't find PORT. Check your PORT.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                ipPortSuccess = false;
            }

            System.out.println(ip); //Заглушка
            System.out.println(port); //Заглушка

            //TODO uncomment connection to server
//            if (ipPortSuccess) {
//                try {
//                    Socket socket = new Socket(ip, port);
//                    connectionSuccess = true;
//                } catch (UnknownHostException hostErr) {
//                    JOptionPane.showMessageDialog(content, "ERROR. Can't connect to server. Check your IP.",
//                            "ERROR", JOptionPane.ERROR_MESSAGE);
//                    connectionSuccess = false;
//                } catch (IOException ioErr) {
//                    JOptionPane.showMessageDialog(content, "ERROR. Can't connect to server. Check your PORT.",
//                            "ERROR", JOptionPane.ERROR_MESSAGE);
//                    connectionSuccess = false;
//                }
//            }

            if (connectionSuccess) {

            }

            ipField.setText("");
            portField.setText("");
        });
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }
}
