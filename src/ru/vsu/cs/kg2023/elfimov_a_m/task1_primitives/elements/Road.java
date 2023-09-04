package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;

public class Road {
    protected int x, y;
    protected int cellSize;
    protected double direction;
    protected Color gravelColor = new Color(127, 127, 127);

    public void draw(Graphics2D g){

    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setGravelColor(Color gravelColor) {
        this.gravelColor = gravelColor;
    }
}
