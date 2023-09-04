package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;

public class StraightRoad extends Road{
    public static final double HORIZONTAL = 0., VERTICAL = Math.PI / 2.; // radians

    public StraightRoad(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellSize = 60;
        this.direction = HORIZONTAL;
    }

    public void draw(Graphics2D g) {
        Color saveColor = g.getColor();
        // ...
        // Rotate
        g.rotate(direction, x + cellSize / 2, y + cellSize / 2);
        // Gravel
        g.setColor(gravelColor);
        g.fillRect(x, y, cellSize, cellSize);
        // red flags
        g.setColor(Color.RED);
        g.fillRect(x, y, cellSize, cellSize / 6);
        g.fillRect(x, y + 5 * cellSize / 6, cellSize, cellSize / 6);
        // white flags
        g.setColor(Color.WHITE);
        for (int i = 0; i < 3; i++) {
            int dx = i * cellSize / 3;
            g.fillRect(x + dx, y, cellSize / 6, cellSize / 6);
            g.fillRect(x + dx, y + 5 * cellSize / 6, cellSize / 6, cellSize / 6);
        }
        // border
        //g.setColor(new Color(50, 50, 50));
        //g.drawRect(x, y, cellSize, cellSize);
        // Rotate back
        g.rotate(-direction, x + cellSize / 2, y + cellSize / 2);
        // ...
        g.setColor(saveColor);
    }

}
