package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;

public class Bleachers {
    private int x, y;
    private int cellSize;
    public static Color steelColor = new Color(127, 127, 127);
    public static Color sitsColor = new Color(195, 195, 195);

    private Human[][] people;

    public Bleachers(int x, int y, int cellSize) {
        this.x = x;
        this.y = y;
        this.cellSize = cellSize;

        // people init
        int n = 4, m = 5;
        people = new Human[n][m];
        int xStep = cellSize / 16;
        int yStep = cellSize / 13;
        int xHuman = x + xStep;
        int yHuman = y + yStep;
        int humanSize = cellSize / 18;
        for (int i = 0; i < people.length; i++) {
            xHuman = x + xStep;
            for (int j = 0; j < people[i].length; j++) {
                people[i][j] = new Human(xHuman, yHuman, humanSize);
                xHuman += xStep * 3;
            }
            yHuman += yStep * 3;
        }
    }

    public void draw(Graphics2D g) {
        Color save = g.getColor();
        // ...
        g.setColor(Color.BLACK); // to delete
        g.drawRect(x, y, cellSize, cellSize); // to delete
        int xDraw, yDraw, widthDraw, heightDraw;
        // Steel
        g.setColor(steelColor);
        xDraw = x + cellSize /16;
        yDraw = y;
        widthDraw = cellSize / 8;
        heightDraw = cellSize;
        for (int i = 0; i < 3; i++) {
            g.fillRect(xDraw, yDraw, widthDraw, heightDraw);
            xDraw += cellSize / 4 + widthDraw;
        }
        // Sits
        xDraw = x;
        yDraw = y + cellSize / 16;
        widthDraw = cellSize;
        heightDraw = 2 * cellSize / 13;
        for (int i = 0; i < 4; i++) {
            g.setColor(sitsColor);
            g.fillRect(xDraw, yDraw, widthDraw, heightDraw);
            g.setColor(steelColor);
            g.drawRect(xDraw, yDraw, widthDraw, heightDraw);
            yDraw += cellSize / 13 + heightDraw;
        }
        // people
        for (int i = 0; i < people.length; i++) {
            for (int j = 0; j < people[i].length; j++) {
                people[i][j].draw(g);
            }
        }
        // ...
        g.setColor(save);
    }
}
