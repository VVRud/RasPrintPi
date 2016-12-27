package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by vvrud on 27.12.16.
 *
 * @author VVRud
 */
class SaveChooser extends JDialog {

    SaveChooser(Frame owner) {
        super(owner, "Choose file to save", true);

        JPanel panel = new JPanel(new GridBagLayout());
        JLabel text = new JLabel("Choose mode for analyzing, please");

        JComboBox<String> modeAnalyzeList = new JComboBox<>();
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("CANCEL");
        panel.add(text, new GridBagConstraints(0, 0, 2, 1,
                0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));
        panel.add(modeAnalyzeList, new GridBagConstraints(0, 1, 2, 1,
                0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));
        panel.add(okBtn, new GridBagConstraints(0, 2, 1, 1,
                0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));
        panel.add(cancelBtn, new GridBagConstraints(1, 2, 1, 1,
                0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        ActionListener actionListener = e -> {
            if (e.getSource() == okBtn) {

            } else if (e.getSource() == cancelBtn) {

            }
            dispose();
        };

        okBtn.addActionListener(actionListener);
        cancelBtn.addActionListener(actionListener);
        getContentPane().add(panel, BorderLayout.CENTER);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}
