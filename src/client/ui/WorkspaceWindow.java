package client.ui;

import client.logic.GetterClient;
import client.logic.PrintingData;
import client.logic.SenderClient;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

import static client.Constants.*;

/**
 * Created by vvrud on 11.09.16.
 * @author VVRud
 * Workspace window of my soft, that let you to download file,
 * choose all available features of printing and start printing.
 */

public class WorkspaceWindow extends JFrame {

    private static GetterClient getter;
    private final JPanel workPanel = new JPanel(new GridBagLayout());

    public WorkspaceWindow() {
        super(TITLE + " on IP: " + LoginWindow.getIp() + ":" + LoginWindow.getPort());

        JTable tableProgress = new VisualProgress();
        JProgressBar progressBar = new JProgressBar();
        workPanel.add(tableProgress, new GridBagConstraints(0, 0, 1, 9, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        createProgressBar(progressBar);

        JLabel fileDir = new JLabel("<file directory here>");
        JButton chooseButton = new JButton("Open file");
        JButton startButton = new JButton("START");
        JButton stopButton = new JButton("STOP");
        JComboBox<String> speedList = new JComboBox<>(SPEED_DATA);
        JComboBox<String> modeList = new JComboBox<>(MODE_DATA);
        JComboBox<String> intensityList = new JComboBox<>(INTENSITY_DATA);

        createOptionsPanel(fileDir, chooseButton, startButton, stopButton, speedList, modeList, intensityList);

        stopButton.setEnabled(false);

        chooseButton.addActionListener(lChoose -> {
            JFileChooser chooseDialog = new JFileChooser();
            int returnedFile = chooseDialog.showDialog(null, "Open file");
            if (returnedFile == JFileChooser.APPROVE_OPTION) {
                File file = chooseDialog.getSelectedFile();
                fileDir.setText(file.getName());
                PrintingData.setFile(file);
            }
        });

        startButton.addActionListener(lStart -> {
            setInactiveTrue(chooseButton, startButton, speedList, modeList, intensityList);
            stopButton.setEnabled(true);

            HashMap<String, String> options = new HashMap<>();
            options.put("speed", String.valueOf(speedList.getSelectedItem()));
            options.put("mode", String.valueOf(modeList.getSelectedItem()));
            options.put("intensity", String.valueOf(intensityList.getSelectedItem()));
            System.out.println(options);

            //Start printing

            PrintingData.setOptions(options);
            PrintingData.setPrintingInterrupted(false);

            SenderClient sender = new SenderClient();
            sender.start();

            getter = new GetterClient();
            getter.start();
        });

        stopButton.addActionListener(lStop -> {
            setInactiveFalse(chooseButton, startButton, speedList, modeList, intensityList);
            stopButton.setEnabled(false);
            PrintingData.setPrintingInterrupted(true);

            if (getter.isAlive()) {
                getter.interrupt();
            }
            SenderClient sender = new SenderClient();
            sender.start();
        });

        workPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        getContentPane().add(workPanel, BorderLayout.CENTER);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createProgressBar(JProgressBar progressBar) {
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(100);
        //TODO Изменение значение по количеству отпечатанных "символов"

        workPanel.add(progressBar, new GridBagConstraints(0, 9, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    }

    private void createOptionsPanel(JLabel fileDir, JButton chooseButton, JButton startButton, JButton stopButton,
                                    JComboBox<String> speedList, JComboBox<String> modeList, JComboBox<String> intensityList) {

        JLabel chooseLabel = new JLabel("Choose your file in *.txt");
        workPanel.add(chooseLabel, new GridBagConstraints(1, 0, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        workPanel.add(fileDir, new GridBagConstraints(1, 1, 2, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.CENTER, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(chooseButton, new GridBagConstraints(1, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.CENTER, new Insets(0, 5, 5, 10), 0, 0));

        JLabel speedLabel = new JLabel("Printing speed");
        workPanel.add(speedLabel, new GridBagConstraints(1, 3, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(speedList, new GridBagConstraints(1, 4, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 10), 0, 0));


        JLabel modeLabel = new JLabel("Printing mode");
        workPanel.add(modeLabel, new GridBagConstraints(1, 5, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(modeList, new GridBagConstraints(1, 6, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 10), 0, 0));

        JLabel intensityLabel = new JLabel("Printing intensity");
        workPanel.add(intensityLabel, new GridBagConstraints(1, 7, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(intensityList, new GridBagConstraints(1, 8, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

        workPanel.add(startButton, new GridBagConstraints(1, 9, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
        workPanel.add(stopButton, new GridBagConstraints(2, 9, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    }

    private void setInactiveFalse(JButton chooseButton, JButton startButton, JComboBox<String> speedList,
                                  JComboBox<String> modeList, JComboBox<String> intensityList) {
        chooseButton.setEnabled(true);
        startButton.setEnabled(true);
        speedList.setEnabled(true);
        modeList.setEnabled(true);
        intensityList.setEnabled(true);
    }

    private void setInactiveTrue(JButton chooseButton, JButton startButton, JComboBox<String> speedList,
                                 JComboBox<String> modeList, JComboBox<String> intensityList) {
        chooseButton.setEnabled(false);
        startButton.setEnabled(false);
        speedList.setEnabled(false);
        modeList.setEnabled(false);
        intensityList.setEnabled(false);
    }
}
