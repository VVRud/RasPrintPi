package client.ui;

import client.logic.Analyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

/**
 * Created by vvrud on 14.11.16.
 *
 * @author VVRud
 */
class DrawArea extends JComponent {

    private Image image;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    private boolean mouseMoved;

    private int beginX, beginY, endX, endY;

    private ArrayList<Integer> listX = new ArrayList<>();
    private ArrayList<Integer> listY = new ArrayList<>();

    private Analyzer analyzer = new Analyzer();

    DrawArea() {

        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();

                beginX = oldX;
                beginY = oldY;

                listX.add(beginX);
                listY.add(beginY);

                mouseMoved = false;
            }

            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();

                if (mouseMoved && ((beginX == endX) && (beginY == endY))) {
                    g2.drawOval(beginX, beginY, 1, 1);
                    System.out.printf("MOUSE MOVED. CIRCLE. (%d; %d)\n", beginX, beginY);
                } else if (mouseMoved && ((beginX != endX) || (beginY != endY))) {
                    g2.drawOval(beginX, beginY, 1, 1);
                    g2.drawOval(endX, endY, 1, 1);
                    System.out.printf("MOUSE MOVED. LINE. (%d; %d) -> (%d; %d)\n", beginX, beginY, endX, endY);
                } else if (!mouseMoved) {
                    g2.drawOval(beginX, beginY, 1, 1);
                    System.out.printf("MOUSE WAS NOT MOVED. DOT. (%d; %d)\n", beginX, beginY);
                } else System.out.println("Something went wrong!");

                repaint();

                analyzer.addToAnalyze(new ArrayList<>(listX), new ArrayList<>(listY));
                System.out.println("X:" + listX.size() + " " + listX.toString());
                System.out.println("Y:" + listY.size() + " " + listY.toString());

                listX.clear();
                listY.clear();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                listX.add(currentX);
                listY.add(currentY);

                if (g2 != null) {
                    g2.drawLine(oldX, oldY, currentX, currentY);
                    g2.drawOval(currentX, currentY, 2, 2);
                    g2.fillOval(currentX, currentY, 2, 2);
                    repaint();
                    oldX = currentX;
                    oldY = currentY;
                }

                mouseMoved = true;
            }
        });

    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    void clear() {
        analyzer.clearLists();
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }
}
