package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives;

import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.*;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class DrawPanel extends JPanel {
    public boolean DEBUG = false;
    private Trace trace;
    private Car car;
    private Car car2;
    private int x0, y0;
    private int cellSize;
    private int ticks = 0;

    final Timer timer = new Timer(0, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    public DrawPanel() {
        cellSize = 150;
        // coordinate shift
        x0 = 200;
        y0 = 100;
        // trace
        trace = new Trace("map1.txt", cellSize, x0, y0);
        // car
        car = new Car(x0 + (2 * cellSize), y0 + (4 * cellSize / 3.), cellSize);
        car.setTracePath(trace.getTracePath());

        car2 = new Car(x0 + (2 * cellSize) - cellSize, y0 + (4 * cellSize / 3.), cellSize);
        car2.setMainColor(new Color(39, 203, 15));
        car2.setTracePath(trace.getTracePath());
        // Debug mode
        switchDebug();
        // Mouse Target for Car
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                car.setxTarget(e.getX());
                car.setyTarget(e.getY());
                DEBUG = !DEBUG;
                switchDebug();
            }
        });

        // Timer
        timer.setInitialDelay(0);
        timer.start();
    }

    public void switchDebug(){
        car.DEBUG = DEBUG;
        car2.DEBUG = DEBUG;
        trace.DEBUG = DEBUG;
    }

    @Override
    public void paint(Graphics gr) {
        ticks++;

        Graphics2D g = (Graphics2D) gr;



        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // background grass
        trace.draw(g);


        if(DEBUG){
            g.setColor(Color.BLACK);
            g.drawString("AngleCurrentRadians: " + car.getAngleCurrentRadians(), 800, 680);
            g.drawString("AngleTargetRadians: " + car.getAngleTargetRadians(), 800, 710);
        }


        car.draw(g);
        car2.draw(g);
    }




}
