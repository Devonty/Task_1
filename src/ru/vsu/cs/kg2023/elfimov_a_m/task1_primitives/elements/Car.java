package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;
import java.util.List;

public class Car {
    public boolean DEBUG = false;
    private int xTarget = 600, yTarget = 600;

    private List<Point> tracePath;
    private int iTracePart = 0;
    private double xC, yC;
    private int cellSize;
    private int size;
    private int carWidth;
    private int carHeight;
    private double minSpeed;

    private double angleTargetRadians;
    private double angleCurrentRadians;

    private double angleSpeedRadians;

    protected static Color mainColor = new Color(22, 22, 173);
    protected static Color wheelColor = new Color(10, 10, 10);
    protected static Color linesColor = new Color(255, 255, 255);

    public Car(double xC, double yC, int cellSize) {
        this.xC = xC;
        this.yC = yC;

        this.cellSize = cellSize;
        this.size = 2 * cellSize / 7;

        this.carWidth = 3 * size / 2;
        this.carHeight = size;

        this.minSpeed = 3;
        this.angleTargetRadians = 0;
        this.angleCurrentRadians = 0;
        this.angleSpeedRadians = Math.PI / 75;
    }


    public void draw(Graphics2D g) {
        // drawFantom(g);
        calcAngle(g);
        moveToTarget(g);
        drawCar(g);
    }
    private void drawFantom(Graphics2D g){
        Color mainColorSave = mainColor;
        Color linesColorSave = linesColor;
        Color wheelColorSave = wheelColor;

        double fantomDist = 1.;
        xC -= fantomDist * Math.cos(angleCurrentRadians);
        yC -= fantomDist * Math.sin(angleCurrentRadians);

        mainColor = mainColor.darker().darker();
        linesColor = linesColor.darker().darker();
        wheelColor = wheelColor.darker().darker();

        drawCar(g);

        xC += fantomDist * Math.cos(angleCurrentRadians);
        yC += fantomDist * Math.sin(angleCurrentRadians);

        mainColor = mainColorSave;
        linesColor = linesColorSave;
        wheelColor = wheelColorSave;
    }
    private void drawCar(Graphics2D g) {
        int x = (int) Math.round(xC);
        int y = (int) Math.round(yC);
        Color saveColor = g.getColor();
        // ...
        // Rotate
        g.rotate(angleCurrentRadians, x + carWidth / 2, y + carHeight / 2);
        // Paint
        // Spoiler
        int spoilerWidth = carWidth / 6;
        int spoilerHeight = carHeight;
        int spoilerX = x;
        int spoilerY = y;
        g.setColor(mainColor);
        g.fillRect(spoilerX, spoilerY, spoilerWidth, spoilerHeight);
        g.setColor(Color.BLACK);
        g.drawRect(spoilerX, spoilerY, spoilerWidth, spoilerHeight);
        // Body
        int bodyWidth = carWidth / 2;
        int bodyHeight = carHeight / 2;
        int bodyX = x + spoilerWidth;
        int bodyY = y + (size - bodyHeight) / 2;
        g.setColor(mainColor);
        g.fillRoundRect(bodyX, bodyY, bodyWidth, bodyHeight, bodyWidth / 3, bodyHeight / 3);
        g.setColor(Color.BLACK);
        g.drawRoundRect(bodyX, bodyY, bodyWidth, bodyHeight, bodyWidth / 3, bodyHeight / 3);
        // Rect part
        int rectWidth = carWidth / 4;
        int rectHeight = bodyHeight / 2;
        int rectX = x + spoilerWidth + bodyWidth;
        int rectY = bodyY + bodyHeight / 4;
        g.setColor(mainColor);
        g.fillRect(rectX, rectY, rectWidth, rectHeight);
        g.setColor(Color.BLACK);
        g.drawRect(rectX, rectY, rectWidth, rectHeight);
        // Forward spoiler
        int forwardSpoilerWidth = carWidth / 8;
        int forwardSpoilerHeight = 4 * carHeight / 5;
        int forwardSpoilerX = rectX + rectWidth;
        int forwardSpoilerY = y + (size - forwardSpoilerHeight) / 2;
        g.setColor(mainColor);
        g.fillRect(forwardSpoilerX, forwardSpoilerY, forwardSpoilerWidth, forwardSpoilerHeight);
        g.setColor(Color.BLACK);
        g.drawRect(forwardSpoilerX, forwardSpoilerY, forwardSpoilerWidth, forwardSpoilerHeight);
        // Wheels
        int wheelWidth = carWidth / 4;
        int wheelHeight = carHeight / 4;
        int[] wheelX = new int[]{
                x + spoilerWidth + size/24,
                x + spoilerWidth + size/24,
                forwardSpoilerX - wheelWidth -size/24,
                forwardSpoilerX - wheelWidth -size/24,
        };
        int[] wheelY = new int[]{
                y + size/24,
                y + bodyHeight + wheelHeight,
                forwardSpoilerY,
                forwardSpoilerY + wheelHeight + rectHeight + size/24,
        };

        for (int i = 0; i < wheelX.length; i++) {
            g.setColor(wheelColor);
            g.fillRect(wheelX[i], wheelY[i], wheelWidth, wheelHeight);
            g.setColor(mainColor);
            g.drawRect(wheelX[i], wheelY[i], wheelWidth, wheelHeight);
        }
        // Lines
        int lineWidth = carWidth;
        int lineHeight = rectHeight / 7;
        int lineX = x;
        int lineY1 = rectY + 3* rectHeight / 7;
        int lineY2 = rectY + 5* rectHeight / 7;
        g.setColor(Color.WHITE);
        g.fillRect(lineX, lineY1, lineWidth, lineHeight);
        g.fillRect(lineX, lineY2, lineWidth, lineHeight);
        // Rotate back
        g.rotate(-angleCurrentRadians, x + carWidth / 2, y + carHeight / 2);
        // ...
        g.setColor(saveColor);
    }

    public void moveToTarget(Graphics2D g) {
        if (moveToPoint(g, xTarget, yTarget, cellSize / 5)) {
            iTracePart = (iTracePart + 1) % tracePath.size();
            this.xTarget = (int) tracePath.get(iTracePart).getX();
            this.yTarget = (int) tracePath.get(iTracePart).getY();
        }
    }

    protected boolean moveToPoint(Graphics2D g, int x, int y, int radius) {
        double xCenter = xC + carWidth / 2, yCenter = yC + carHeight / 2;
        double dist = Math.sqrt((x - xCenter) * (x - xCenter) + (yCenter - y) * (yCenter - y));
        double v = minSpeed + 2 * Math.sqrt(1 + dist / cellSize);
        double delta = Math.abs(angleCurrentRadians - angleTargetRadians);
        double dif = Math.min(Math.abs(2 * Math.PI - delta), delta);

        if (dif > Math.PI / 36) v /= 1.55;

        if (dist <= radius) {
            return true;
        }
        //if (dist < v) v = dist;

        double deltaX = v * (x - xCenter) / dist;
        double deltaY = v * (y - yCenter) / dist;

        this.xC += deltaX;
        this.yC += deltaY;

        return Math.abs(xCenter - xTarget) <= radius && Math.abs(yCenter - yTarget) <= radius;
    }

    private void calcAngle(Graphics2D g) {
        Color save = g.getColor();
        double xCenter = xC + carWidth / 2., yCenter = yC + carHeight / 2.;
        double deltaX = xTarget - xCenter;
        double deltaY = yTarget - yCenter;
        double cosAngleCalc = deltaY / Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double angleCalcRadians = Math.acos(cosAngleCalc) + Math.PI / 2;
        if (deltaX > 0) angleCalcRadians = Math.PI - angleCalcRadians;
        angleTargetRadians = angleCalcRadians;

        // spin to target angle
        double localTarget = (angleCurrentRadians - angleTargetRadians + 4 * Math.PI) % (2 * Math.PI);
        int digit = -1;
        if (localTarget >= Math.PI) {
            digit = 1;
        }
        double angleStep = digit * Math.min(localTarget, angleSpeedRadians);
        if (Math.abs(angleStep) >= angleSpeedRadians) {
            angleCurrentRadians = (angleCurrentRadians + angleStep + 2 * Math.PI) % (2 * Math.PI);
        }

        // test angle
        if (!DEBUG) {
            g.setColor(save);
            return;
        }
        double len = 300.;
        double xCalcLine = xCenter + len * Math.cos(angleCalcRadians);
        double yCalcLine = yCenter + len * Math.sin(angleCalcRadians);
        g.setColor(Color.CYAN);
        g.drawLine((int) xCenter, (int) yCenter, (int) (xCalcLine), (int) (yCalcLine));
        len -= 100;
        xCalcLine = xCenter + len * Math.cos(angleCurrentRadians);
        yCalcLine = yCenter + len * Math.sin(angleCurrentRadians);
        g.setColor(Color.BLUE);
        g.drawLine((int) xCenter, (int) yCenter, (int) (xCalcLine), (int) (yCalcLine));
        g.setColor(save);

    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
        this.size = 2 * cellSize / 7;
        this.carWidth = 3 * size / 2;
        this.carHeight = size;
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
