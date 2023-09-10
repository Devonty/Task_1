package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives;

import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.Bleachers;
import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DrawPanelAngle extends JPanel {
    private int x, y;
    private double angleDegrees = 0;

    private Bleachers bleachers;
    private int cellSize;

    private java.util.List<java.util.List<Human>> people;

    final Timer timer = new Timer(0, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    public DrawPanelAngle() {
        this.setSize(800, 600);
        timer.setDelay(0);
        timer.start();
        this.cellSize = 180;
        // people = new ArrayList<>();
        // for (int i = 0; i < 10; i++) {
        //     people.add(new ArrayList<>());
        //     for (int j = 0; j < 10; j++) {
        //         people.get(i).add(new Human(200 + i * 150, 200 + j * 70, 35));
        //     }
        // }

        bleachers = new Bleachers(600, 400, cellSize);

    }

    @Override
    public void paint(Graphics gr) {

        Graphics2D g = (Graphics2D) gr;

        x = this.getWidth() / 2;
        y = this.getHeight() / 2;
        // background
        g.setColor(new Color(32, 177, 76));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        bleachers.draw(g);
        // for (int i = 0; i < 10; i++) {
        //     for (int j = 0; j < 10; j++) {
        //         people.get(i).get(j).draw(g);
        //     }
        // }
        // agngle
        // double anlgeRadians = Math.toRadians(angleDegrees);
        // double len = 300.;
        // double xRealLine = len * Math.cos(anlgeRadians);
        // double yRealLine =  len * Math.sin(anlgeRadians);
        // g.setColor(Color.GREEN);
        // g.drawLine(x, y, (int) (x +  xRealLine), (int) (y + yRealLine));
        // // calc
        // double cosAngleCalc = yRealLine / Math.sqrt(xRealLine * xRealLine + yRealLine * yRealLine);
        // double angleCalcRadians = Math.acos(cosAngleCalc) + Math.PI / 2;
        // if(xRealLine > 0) angleCalcRadians = Math.PI -angleCalcRadians;
        // len /= 2;
        // double xCalcLine = x + len * Math.cos(angleCalcRadians);
        // double yCalcLine = y + len * Math.sin(angleCalcRadians);
        // g.setColor(Color.BLUE);
        // g.drawLine(x, y, (int) (xCalcLine), (int) (yCalcLine));
        // angleDegrees += 0.5;

        // System.out.println((int)xRealLine + "\t" + (int)yRealLine);
    }
}
