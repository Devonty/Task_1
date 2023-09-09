package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;

public class StartRoad extends StraightRoad{
    private Color lineColor = new Color(197, 201, 181);
    private int lineWidth = cellSize / 12;
    private int lineHeight = 2 * cellSize / 3;
    private int xLine = (cellSize - lineWidth )/2;
    private int yLine = (cellSize / 6);

    public StartRoad(int x, int y) {
        super(x, y);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        Color saveColor = g.getColor();
        // ...
        // Rotate
        g.rotate(direction, x + cellSize / 2, y + cellSize / 2);
        // Line
        g.setColor(lineColor);
        g.fillRect(x + xLine, y + yLine, lineWidth, lineHeight);
        // String
        g.rotate(Math.PI/2, x + cellSize / 2, y + cellSize / 2);
        Font font = new Font("Times New Roman", Font.BOLD, cellSize / 8);
        g.setFont(font);
        g.drawString("START", x + xLine - cellSize/6, y +yLine + cellSize/2);
        g.rotate(-Math.PI/2, x + cellSize / 2, y + cellSize / 2);
        // Rotate back
        g.rotate(-direction, x + cellSize / 2, y + cellSize / 2);
        // ...
        g.setColor(saveColor);
    }

    @Override
    public void setCellSize(int cellSize) {
        super.setCellSize(cellSize);
        lineWidth = cellSize / 12;
        lineHeight =  2 * cellSize / 3;
        xLine = (cellSize - lineWidth )/2;
        yLine = (cellSize / 6);
    }
}
