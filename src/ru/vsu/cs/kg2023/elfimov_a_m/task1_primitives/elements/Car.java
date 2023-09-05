package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;

public class Car {
    protected int x, y;
    protected double xC, yC;
    protected int cellSize;
    protected double direction;

    protected Color mainColor = new Color(22, 22, 173);
    protected Color wheelColor = new Color(10, 10, 10);
    protected Color linesColor = new Color(255, 255, 255);

    public Car(double xC, double yC) {
        this.xC = xC;
        this.yC = yC;
    }

    public void draw(Graphics2D g) {
        x = (int) Math.round(xC);
        y = (int) Math.round(yC);
        int carWidth = cellSize / 2;
        int carHeight = cellSize / 3;
        Color saveColor = g.getColor();
        // ...
        // Rotate
        g.rotate(direction, x + carWidth / 2, y + carHeight / 2);
        // Paint
        g.setColor(mainColor);
        g.fillRect(x, y, cellSize / 2, cellSize / 3);
        // Rotate back
        g.rotate(-direction, x + carWidth / 2, y + carHeight / 2);
        // ...
        g.setColor(saveColor);
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public void setWheelColor(Color wheelColor) {
        this.wheelColor = wheelColor;
    }

    public void setLinesColor(Color linesColor) {
        this.linesColor = linesColor;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setxC(double xC) {
        this.xC = xC;
    }

    public void setyC(double yC) {
        this.yC = yC;
    }

    public double getxC() {
        return xC;
    }

    public double getyC() {
        return yC;
    }

    protected void stepToPoint(int x, int y) {
        double v = 2;
        double dist = Math.sqrt((x - xC) * (x - xC) + (y - yC) * (y - yC));
        if (dist == 0) return;
        if (dist < v) v = dist;

        double deltaX = v * (x - xC) / dist;
        double deltaY = v * (y - yC) / dist;

        this.xC += deltaX;
        this.yC += deltaY;
    }

    public boolean moveToPoint(int x, int y){
        stepToPoint(x, y);
        return xC != x || yC != y;
    }
}
