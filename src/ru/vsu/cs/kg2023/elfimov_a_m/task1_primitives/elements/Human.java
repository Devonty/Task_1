package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;
import java.util.Random;

public class Human {
    public static final Color[] shirtColors = new Color[]{
            Color.GREEN,
            Color.BLUE,
            Color.RED,
            Color.CYAN,
            Color.PINK,
            Color.YELLOW,
    };
    public static final Color[] headColors = new Color[]{
            new Color(255, 198, 132),
            new Color(93, 78, 59),
            new Color(79, 48, 15),
            new Color(37, 30, 20),
            new Color(227, 192, 71),
            new Color(227, 205, 173),
    };
    private Color shirtColor;
    private Color headColor;

    private int x;
    private int y;
    private int height;
    private int width;

    public Human(int x, int y, int width, int height) {
        Random rd = new Random();
        shirtColor = shirtColors[rd.nextInt(shirtColors.length)];
        headColor = headColors[rd.nextInt(headColors.length)];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g){
        Color save = g.getColor();
        // ...
        // body
        g.setColor(shirtColor);
        g.fillOval(x, y, width, height);
        // head
        g.setColor(Color.BLACK);
        g.fillOval(x + (width - height) / 2, y, height, height);
        // ...
        g.setColor(save);
    }
}
