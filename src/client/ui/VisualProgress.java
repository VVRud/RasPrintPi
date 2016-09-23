package client.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static client.Constants.CELL_SIZE;

/**
 * Created by vvrud on 12.09.16.
 * @author VVRud
 * This class creates visual progress table, that is shown on the Worcspace window.
 */
class VisualProgress extends JTable {

    //TODO изменяемый размер таблицы, зависимо от размера массива
    private static int mapSize = 120;

    VisualProgress() {
        setModel(new DefaultTableModel(mapSize, mapSize));
        setRowHeight(CELL_SIZE);
        for (int i = 0; i < mapSize; i++) {
            this.getColumnModel().getColumn(i).setMinWidth(CELL_SIZE);
            this.getColumnModel().getColumn(i).setMaxWidth(CELL_SIZE);
        }
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                setValueAt(0x0000, y, x);
            }
        }
        setVisible(true);
    }


    public static int getMapSize() {
        return mapSize;
    }

    public static void setMapSize(int mapSize) {
        VisualProgress.mapSize = mapSize;
    }
}
