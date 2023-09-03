package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;

public class StraightRoad {
    public final double HORIZONTAL = 0., VERTICAL = Math.PI / 2.; // radians
    private int x, y;
    private int cellSize;
    private double direction = HORIZONTAL;

    public StraightRoad(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellSize = 60;
    }

    public StraightRoad(int x, int y, int cellSize) {
        this.x = x;
        this.y = y;
        this.cellSize = cellSize;
    }

    public void draw(Graphics2D g) {
        Color saveColor = g.getColor();
        // ...
        // Rotate
        g.rotate(direction, x + cellSize / 2, y + cellSize / 2);
        // Gravel
        g.setColor(new Color(127, 127, 127));
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
        // Rotate back
        g.rotate(-direction, x + cellSize / 2, y + cellSize / 2);
        // ...
        g.setColor(saveColor);
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}
