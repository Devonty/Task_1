package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trace {
    protected int cellSize;
    private List<Road> roads = new ArrayList<>();
    private List<Point> tracePath = new ArrayList<>();

    public Trace(String mapName, int cellSize) {
        this.cellSize = cellSize;
        try {
            loadFromFile(mapName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        Color saveColor = g.getColor();
        // ...
        for (Road road : roads) {
            road.draw(g);
        }
        // ...
        g.setColor(saveColor);
    }

    public void loadFromFile(String mapName) throws FileNotFoundException {
        /*
        "=" horizontal
        "|" vertical
        "1-4" turns RIGHTDOWN, LEFTDOWN, LEFTUP, RIGHTUP;
        "." nothing
        */
        Scanner scanner = new Scanner(new File("src/ru/vsu/cs/kg2023/elfimov_a_m/task1_primitives/maps/" + mapName));
        Road road = null;
        int i = 0, x, y;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '.') continue;
                x = j * cellSize;
                y = i * cellSize;
                if (line.charAt(j) == '=') {
                    road = new StraightRoad(x, y);
                }
                if (line.charAt(j) == '|') {
                    road = new StraightRoad(x, y);
                    road.setDirection(StraightRoad.VERTICAL);
                }
                if (line.charAt(j) == '1') {
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.RIGHTDOWN);
                }
                if (line.charAt(j) == '2') {
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.LEFTDOWN);
                }
                if (line.charAt(j) == '3') {
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.LEFTUP);
                }
                if (line.charAt(j) == '4') {
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.RIGHTUP);
                }
                road.setCellSize(cellSize);
                roads.add(road);
            }
            i++;
        }
    }
}
