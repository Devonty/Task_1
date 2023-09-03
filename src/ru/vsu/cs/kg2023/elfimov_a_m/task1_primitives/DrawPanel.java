package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives;

import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.StraightRoad;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    private StraightRoad road;
    private StraightRoad road2;

    public DrawPanel() {
        road = new StraightRoad(50, 50);
        road2 = new StraightRoad(110, 50);
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;

        g.setColor(new Color(38, 133, 47));
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // background grass

        road.draw(g);
        road2.draw(g);
    }
}
