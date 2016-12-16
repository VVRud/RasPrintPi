package client.ui;

import client.Client;
import client.data.PrintingData;
import client.io.ReceiverClient;
import client.io.SenderClient;
import client.logic.Analyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static client.data.Constants.*;

/**
 * Created by vvrud on 11.09.16.
 * @author VVRud
 * Workspace window of my soft, that let you to download file,
 * choose all available features of printing and start printing.
 */

public class WorkspaceWindow extends JFrame {

    private static final JPanel workPanel = new JPanel(new GridBagLayout());

    private JButton chooseFileButton,
            startButton,
            stopButton,
            saveFileButton,
            clearButton;

    private JLabel fileDir;

    private DrawArea drawArea;

    private JComboBox<String> speedList = new JComboBox<>(SPEED_DATA);
    private JComboBox<String> modeList = new JComboBox<>(MODE_DATA);
    private JComboBox<String> intensityList = new JComboBox<>(INTENSITY_DATA);
    private JComboBox<String> modeAnalyzeList = new JComboBox<>(MODE_ANALYZE_DATA);

    public WorkspaceWindow() {
        super(TITLE + " | Opened on IP: " + LoginWindow.getIp() + ":" + LoginWindow.getPort());

        ActionListener actionListener = e -> {
            if (e.getSource() == startButton) {
                startPrinting();
            } else if (e.getSource() == stopButton) {
                stopPrinting();
            } else if (e.getSource() == chooseFileButton) {
                chooseFile();
            } else if (e.getSource() == saveFileButton) {
                saveFile();
            } else if (e.getSource() == clearButton) {
                drawArea.clear();
            }
        };

        drawAreaCreate();
        createOptionsPanel(actionListener);

        JProgressBar progressBar = new JProgressBar();
        createProgressBar(progressBar);

        workPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        getContentPane().add(workPanel, BorderLayout.CENTER);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(workPanel, message, WARN_TITLE, JOptionPane.WARNING_MESSAGE);
    }

    private void saveFile() {
        JFileChooser saveDialog = new JFileChooser();
        saveDialog.addChoosableFileFilter(FILTER);
        saveDialog.setFileFilter(FILTER);
        if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(saveDialog.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Всё погибло!");
            }
        }
    }

    private void chooseFile() {
        JFileChooser chooseDialog = new JFileChooser();
        int returnedFile = chooseDialog.showDialog(null, "Open file");
        if (returnedFile == JFileChooser.APPROVE_OPTION) {
            File file = chooseDialog.getSelectedFile();
            fileDir.setText(file.getName());
            PrintingData.setFile(file);
            System.out.println(file.getAbsolutePath().replaceAll(file.getName(), ""));
        }
    }

    private void stopPrinting() {
        setInactiveFalse();
        PrintingData.setPrintingInterrupted(true);
        ReceiverClient receiver = Client.getReceiver();

        if (receiver.isAlive()) {
            receiver.interrupt();
        }
        SenderClient sender = new SenderClient();
        sender.start();
    }

    private void startPrinting() {
        setInactiveTrue();

        String speed = String.valueOf(speedList.getSelectedItem());
        String mode = String.valueOf(modeList.getSelectedItem());
        String intensity = String.valueOf(intensityList.getSelectedItem());

        HashMap<String, String> options = new HashMap<>();
        options.put("Speed", speed);
        options.put("Intensity", intensity);
        options.put("Mode", mode);

        PrintingData.setOptions(options);
        PrintingData.setPrintingInterrupted(false);

        String anMode = String.valueOf(modeAnalyzeList.getSelectedItem());
        if (anMode.equals("Analyze Drawing")) {
            new Analyzer(BEZ_MODE).start();
        } else if (anMode.equals("Analyze Chosen File")) {
            new Analyzer(JPG_MODE).start();
        }
    }

    private void drawAreaCreate() {
        drawArea = new DrawArea();
        drawArea.setPreferredSize(CANVAS_SIZE);
        workPanel.add(drawArea, new GridBagConstraints(0, 0, 1, 12, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }

    private void createProgressBar(JProgressBar progressBar) {
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(100);
        //TODO Изменение значение по количеству отпечатанных "символов"

        workPanel.add(progressBar, new GridBagConstraints(0, 12, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    }

    private void createOptionsPanel(ActionListener actionListener) {

        chooseFileButton = new JButton("Open file");
        startButton = new JButton("START");
        stopButton = new JButton("STOP");
        saveFileButton = new JButton("SAVE");
        clearButton = new JButton("CLEAR");

        chooseFileButton.addActionListener(actionListener);
        startButton.addActionListener(actionListener);
        stopButton.addActionListener(actionListener);
        saveFileButton.addActionListener(actionListener);
        clearButton.addActionListener(actionListener);

        JLabel speedLabel = new JLabel("Printing speed");
        JLabel modeLabel = new JLabel("Printing mode");
        JLabel intensityLabel = new JLabel("Printing intensity");
        fileDir = new JLabel("<file directory here>");
        JLabel chooseLabel = new JLabel("Choose file to print...");
        JLabel saveClearLabel = new JLabel("Save or choose drawing");
        JLabel startStopLabel = new JLabel("Start or stop printing");

        workPanel.add(chooseLabel, new GridBagConstraints(1, 0, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        workPanel.add(fileDir, new GridBagConstraints(1, 1, 2, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.CENTER, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(chooseFileButton, new GridBagConstraints(1, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.CENTER, new Insets(0, 5, 5, 10), 0, 0));

        workPanel.add(speedLabel, new GridBagConstraints(1, 3, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(speedList, new GridBagConstraints(1, 4, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 10), 0, 0));


        workPanel.add(modeLabel, new GridBagConstraints(1, 5, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(modeList, new GridBagConstraints(1, 6, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 10), 0, 0));

        workPanel.add(intensityLabel, new GridBagConstraints(1, 7, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(intensityList, new GridBagConstraints(1, 8, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 10), 0, 0));
        intensityList.setPreferredSize(modeList.getPreferredSize());

        workPanel.add(saveClearLabel, new GridBagConstraints(1, 9, 2, 1, 0, 0, GridBagConstraints.SOUTH,
                GridBagConstraints.SOUTH, new Insets(0, 5, 5, 3), 0, 0));
        workPanel.add(saveFileButton, new GridBagConstraints(1, 10, 1, 1, 0, 0, GridBagConstraints.SOUTH,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
        workPanel.add(clearButton, new GridBagConstraints(2, 10, 1, 1, 0, 0, GridBagConstraints.SOUTH,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

        workPanel.add(startStopLabel, new GridBagConstraints(1, 11, 2, 1, 0, 0, GridBagConstraints.SOUTH,
                GridBagConstraints.SOUTH, new Insets(0, 5, 5, 5), 0, 0));
        workPanel.add(startButton, new GridBagConstraints(1, 12, 1, 1, 0, 0, GridBagConstraints.SOUTH,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
        workPanel.add(stopButton, new GridBagConstraints(2, 12, 1, 1, 0, 0, GridBagConstraints.SOUTH,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
        stopButton.setEnabled(false);

    }

    private void setInactiveFalse() {
        chooseFileButton.setEnabled(true);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        saveFileButton.setEnabled(true);
        clearButton.setEnabled(true);
        speedList.setEnabled(true);
        modeList.setEnabled(true);
        intensityList.setEnabled(true);
    }

    private void setInactiveTrue() {
        chooseFileButton.setEnabled(false);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        saveFileButton.setEnabled(false);
        clearButton.setEnabled(false);
        speedList.setEnabled(false);
        modeList.setEnabled(false);
        intensityList.setEnabled(false);
    }
}
