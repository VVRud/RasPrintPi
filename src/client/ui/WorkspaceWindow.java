package client.ui;

import javax.swing.*;
import java.awt.*;

import static client.Constants.*;

/**
 * Created by vvrud on 11.09.16.
 * Workspace window of my soft, that let you to download file, choose all available features of printing
 * and start printing.
 */
public class WorkspaceWindow extends JFrame {

    private final JPanel workPanel = new JPanel(new GridBagLayout());

    public WorkspaceWindow() {
        super(PROG_TITLE + " on IP: " + LoginWindow.getIp() + ":" + LoginWindow.getPort());

        JTable tableProgress = new VisualProgress();
        workPanel.add(tableProgress, new GridBagConstraints(0, 0, 1, 9, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        createProgressBar();
        JLabel fileDir = new JLabel("<file directory here>");
        JButton chooseButton = new JButton("Open file");
        JButton startButton = new JButton("START");
        JButton stopButton = new JButton("STOP");
        createOptionsPanel(fileDir, chooseButton, startButton, stopButton);

        JFileChooser chooseDialog = new JFileChooser();

        chooseButton.addActionListener(lChoose -> {
            //TODO Вызов диалогового окна выбора файла
        });

        startButton.addActionListener(lStart -> {
            //TODO Запуск процесса печати файла, обновление статусбара и вижуалбара, невозможность менять функциональность печати
        });

        stopButton.addActionListener(lStop -> {
            //TODO Остановка процесса печати и возможность изменения функций
        });

        workPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        getContentPane().add(workPanel, BorderLayout.CENTER);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createProgressBar() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(30);
        //TODO Изменение значение по количеству отпечатанных "символов"

        workPanel.add(progressBar, new GridBagConstraints(0, 9, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    }

    private void createOptionsPanel(JLabel fileDir, JButton chooseButton, JButton startButton, JButton stopButton) {

        JLabel chooseLabel = new JLabel("Choose your file in *.txt");
        workPanel.add(chooseLabel, new GridBagConstraints(1, 0, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        workPanel.add(fileDir, new GridBagConstraints(1, 1, 2, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.CENTER, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(chooseButton, new GridBagConstraints(1, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.CENTER, new Insets(0, 5, 5, 10), 0, 0));

        JLabel speedLabel = new JLabel("Printing speed");
        JComboBox<String> speedList = new JComboBox<>(SPEED_DATA);
        workPanel.add(speedLabel, new GridBagConstraints(1, 3, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(speedList, new GridBagConstraints(1, 4, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 10), 0, 0));


        JLabel modeLabel = new JLabel("Printing mode");
        JComboBox<String> modeList = new JComboBox<>(MODE_DATA);
        workPanel.add(modeLabel, new GridBagConstraints(1, 5, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(modeList, new GridBagConstraints(1, 6, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 10), 0, 0));

        JLabel intensityLabel = new JLabel("Printing intensity");
        JComboBox<String> intensityList = new JComboBox<>(INTENSITY_DATA);
        workPanel.add(intensityLabel, new GridBagConstraints(1, 7, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 3), 0, 0));
        workPanel.add(intensityList, new GridBagConstraints(1, 8, 2, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

        workPanel.add(startButton, new GridBagConstraints(1, 9, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
        workPanel.add(stopButton, new GridBagConstraints(2, 9, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    }
}
