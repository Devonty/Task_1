package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.List;

public class Car {
    protected int x, y;
    protected int xTarget = 600, yTarget = 600;

    private List<Point> tracePath;
    private int iTracePart;
    protected double xC, yC;
    protected int cellSize;
    protected int carWidth;
    protected int carHeight;
    protected double direction;

    protected static Color mainColor = new Color(22, 22, 173);
    protected static Color wheelColor = new Color(10, 10, 10);
    protected static Color linesColor = new Color(255, 255, 255);

    public Car(double xC, double yC, int cellSize) {
        this.xC = xC;
        this.yC = yC;

        this.cellSize = cellSize;
        this.carWidth = cellSize / 2;
        this.carHeight = cellSize / 3;
    }



    public void draw(Graphics2D g) {
        drawCar(g, true);
        moveToTarget();
        drawCar(g, false);
    }

    private void drawCar (Graphics2D g, boolean darker){
        x = (int) Math.round(xC);
        y = (int) Math.round(yC);
        Color saveColor = g.getColor();
        // ...
        // Rotate
        g.rotate(direction, x + carWidth / 2, y + carHeight / 2);
        // Paint
        g.setColor(mainColor);
        if(darker) g.setColor(mainColor.darker());
        g.fillOval(x, y, carWidth, carHeight);
        // Rotate back
        g.rotate(-direction, x + carWidth / 2, y + carHeight / 2);
        // ...
        g.setColor(saveColor);
    }

    public void moveToTarget() {
        if (moveToPoint(xTarget, yTarget, cellSize/4)){
            iTracePart = (iTracePart + 1) % tracePath.size();
            this.xTarget = (int) tracePath.get(iTracePart).getX();
            this.yTarget = (int) tracePath.get(iTracePart).getY();
        }
    }

    protected boolean moveToPoint(int x, int y, int radius) {
        double xCenter = xC + carWidth / 2, yCenter = yC + carHeight / 2;
        double dist = Math.sqrt((x - xCenter) * (x - xCenter) + (yCenter - y) * (yCenter - y));
        double v = 1.54  + 2 * Math.sqrt(1 + dist/cellSize);
        System.out.println(v);

        if (dist <= radius){
            return true;
        }
        //if (dist < v) v = dist;

        double deltaX = v * (x - xCenter) / dist;
        double deltaY = v * (y - yCenter) / dist;

        this.xC += deltaX;
        this.yC += deltaY;

        return Math.abs(xCenter - xTarget) <= radius && Math.abs(yCenter - yTarget) <= radius;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
        this.carWidth = cellSize / 2;
        this.carHeight = cellSize / 3;
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

    public void setxTarget(int xTarget) {
        this.xTarget = xTarget;
    }

    public void setyTarget(int yTarget) {
        this.yTarget = yTarget;
    }

    public void setTracePath(List<Point> tracePath) {
        this.tracePath = tracePath;
        this.iTracePart = 0;
        this.xTarget = (int) tracePath.get(0).getX();
        this.yTarget = (int) tracePath.get(0).getY();
    }
}
