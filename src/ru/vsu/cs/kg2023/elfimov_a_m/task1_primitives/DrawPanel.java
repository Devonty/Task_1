package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives;

import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawPanel extends JPanel {
    private Trace trace;
    private Car car;
    private int cellSize;

    final Timer timer = new Timer(30, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    public DrawPanel() {
        cellSize = 180;

        trace = new Trace("map1.txt", cellSize);
        car = new Car((double) (2 * cellSize), (double) (4 * cellSize / 3.), cellSize);
        car.setTracePath(trace.getTracePath());



        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                car.setxTarget(e.getX());
                car.setyTarget(e.getY());
            }
        });
        timer.setInitialDelay(0);
        timer.start();

    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;

        g.setColor(new Color(29, 182, 44));
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // background grass

        trace.draw(g);
        car.draw(g);
    }




}
