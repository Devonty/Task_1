package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;

public class TurnRoad extends Road {
    public static final double LEFTDOWN = 0., RIGHTDOWN = -Math.PI / 2., RIGHTUP = -Math.PI, LEFTUP = -3 * Math.PI / 2.; // radians

    public TurnRoad(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellSize = 60;
        this.direction = LEFTDOWN;
    }

    public void draw(Graphics2D g) {
        Color saveColor = g.getColor();
        // ...
        // Rotate
        g.rotate(direction, x + cellSize / 2, y + cellSize / 2);
        int r;
        // Bigger flags
        g.setColor(new Color(150, 10, 10));
        r = cellSize + 1;
        g.fillArc(x - r, y + cellSize - r, 2 * r, 2 * r, 0, 90);
        // Gravel
        g.setColor(new Color(127, 127, 127));
        r = cellSize * 5 / 6 + 1;
        g.fillArc(x - r, y + cellSize - r, 2 * r, 2 * r, 0, 90);
        // Smaller flags
        g.setColor(new Color(150, 10, 10));
        r = cellSize / 6 + 1;
        g.fillArc(x - r, y + cellSize - r, 2 * r, 2 * r, 0, 90);
        // Rotate back
        g.rotate(-direction, x + cellSize / 2, y + cellSize / 2);
        // ...
        g.setColor(saveColor);
    }

}
