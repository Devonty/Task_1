package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;
import java.util.Random;

public class Human {
    public static final Color[] shirtColors = new Color[]{
            new Color(218, 22, 22),
            new Color(68, 204, 48),
            new Color(195, 206, 41),
            new Color(63, 215, 215),
            new Color(38, 77, 217),
            new Color(175, 30, 204),
    };
    public static final Color[] headColors = new Color[]{
            new Color(157, 113, 64),
            new Color(93, 78, 59),
            new Color(173, 105, 37),
            new Color(187, 140, 65),
            new Color(124, 106, 47),
            new Color(115, 97, 71),
    };
    private Color shirtColor;
    private Color headColor;

    private int x;
    private int y;
    private int size;
    private int height;
    private int width;

    private double angleRadians;

    public Human(int x, int y, int size) {
        Random rd = new Random();
        shirtColor = shirtColors[rd.nextInt(shirtColors.length)];
        headColor = headColors[rd.nextInt(headColors.length)];
        this.x = x;
        this.y = y;
        this.size = size;
        this.angleRadians = 0;
        height = 4 * size / 3;
        width = 7 * size / 3;
    }

    public void draw(Graphics2D g) {
        Color save = g.getColor();
        // ...
        // rotate
        g.rotate(angleRadians, x + width / 2, y + height / 2);
        // body
        g.setColor(shirtColor);
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
        // head
        g.setColor(headColor);
        g.fillOval(x + (width - size) / 2, y + (height - size) / 2, size, size);
        g.setColor(Color.BLACK);
        g.drawOval(x + (width - size) / 2, y + (height - size) / 2, size, size);
        // rotate back
        g.rotate(-angleRadians, x + width / 2, y + height / 2);
        // ...
        g.setColor(save);
    }
}
