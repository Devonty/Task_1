package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trace {
    public boolean DEBUG = false;
    protected int cellSize;
    private List<Road> roads = new ArrayList<>();
    private List<Point> tracePath = new ArrayList<>();
    private List<Bleachers> bleachers = new ArrayList<>();
    private int testFor = 0;

    public Trace(String mapName, int cellSize) {
        this.cellSize = cellSize;
        try {
            loadFromFile(mapName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        testFor++;
        Color saveColor = g.getColor();
        // ...
        for (Road road : roads) {
            road.draw(g);
        }
        // Points
        if (!DEBUG) {
            g.setColor(saveColor);
            return;
        }
        g.setColor(Color.BLUE);
        int r = 7;
        for (int i = 0; i < tracePath.size(); i++) {
            g.fillOval((int) (tracePath.get(i).getX() - r), (int) (tracePath.get(i).getY() - r), 2 * r, 2 * r);
        }
        g.setColor(Color.GREEN);
        g.fillOval((int) (tracePath.get((testFor / 2) % tracePath.size()).getX() - r),
                (int) (tracePath.get((testFor / 2) % tracePath.size()).getY() - r),
                2 * r, 2 * r);
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

        List<String> map = new ArrayList<>();

        int i = 0, x, y;
        // Trace
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            map.add(line);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '.') continue;
                x = j * cellSize;
                y = i * cellSize;
                road = getRoadByChar(line.charAt(j), x, y);
                assert road != null;
                road.setCellSize(cellSize);
                roads.add(road);
            }
            i++;
        }
        // Path
        for (i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (map.get(i).charAt(j) == 's') {
                    findPath(map, i, j, 0, 0);
                    i = map.size();
                    break;
                }
            }
        }
        System.out.println(tracePath.size());
        for (int j = 0; j < tracePath.size(); j++) {
            System.out.println((j + 1) + ") " + tracePath.get(j));
        }
    }

    private Road getRoadByChar(char ltr, int x, int y){
        Road road = null;
        if (ltr == '=') {
            road = new StraightRoad(x, y);
        }
        if (ltr == 's') {
            road = new StartRoad(x, y);
        }
        if (ltr == '|') {
            road = new StraightRoad(x, y);
            road.setDirection(StraightRoad.VERTICAL);
        }
        if (ltr == '1') {
            road = new TurnRoad(x, y);
            road.setDirection(TurnRoad.RIGHTDOWN);
        }
        if (ltr == '2') {
            road = new TurnRoad(x, y);
            road.setDirection(TurnRoad.LEFTDOWN);
        }
        if (ltr == '3') {
            road = new TurnRoad(x, y);
            road.setDirection(TurnRoad.LEFTUP);
        }
        if (ltr == '4') {
            road = new TurnRoad(x, y);
            road.setDirection(TurnRoad.RIGHTUP);
        }
        return road;
    }

    private void findPath(List<String> map, int i, int j, int iFrom, int jFrom) {
        char cFrom = map.get(iFrom).charAt(jFrom);
        char cNow = map.get(i).charAt(j);
        if (iFrom != 0 && jFrom != 0) {
            if ("1234".contains(String.valueOf(cFrom))
                    && "1234=|s".contains(String.valueOf(cNow))) {
                int x1 = j * cellSize + cellSize / 2;
                int y1 = i * cellSize + cellSize / 2;
                int x2 = jFrom * cellSize + cellSize / 2;
                int y2 = iFrom * cellSize + cellSize / 2;

                int x = (x1 + x2) / 2;
                int y = (y1 + y2) / 2;
                tracePath.add(new Point(x, y));
            } else if ("1234".contains(String.valueOf(cNow))
                    && "=|s".contains(String.valueOf(cFrom))) {
                int x1 = j * cellSize + cellSize / 2;
                int y1 = i * cellSize + cellSize / 2;
                int x2 = jFrom * cellSize + cellSize / 2;
                int y2 = iFrom * cellSize + cellSize / 2;

                int x = (x1 + x2) / 2;
                int y = (y1 + y2) / 2;
                tracePath.add(new Point(x, y));
            }
            if ("1234".contains(String.valueOf(cFrom))) {
                Point last = tracePath.get(tracePath.size() - 1);
                Point preLast = tracePath.get(tracePath.size() - 2);
                int x1 = (int) last.getX();
                int y1 = (int) last.getY();
                int x2 = (int) preLast.getX();
                int y2 = (int) preLast.getY();
                int x3 = jFrom * cellSize + cellSize / 2;
                int y3 = iFrom * cellSize + cellSize / 2;

                int x = (x1 + x2 + x3) / 3;
                int y = (y1 + y2 + y3) / 3;
                tracePath.set(tracePath.size() - 1, new Point(x, y));
                tracePath.add(last);
            }
        }
        if (cNow == 's' && iFrom != 0 && jFrom != 0) {
            return;
        }

        int I[] = {-1, 0, 1, 0};
        int J[] = {0, 1, 0, -1};

        for (int k = 0; k < I.length; k++) {
            if (i + I[k] == iFrom && j + J[k] == jFrom) continue;
            if (i + I[k] < 0 || j + J[k] < 0) continue;

            if ("1234=|s".contains(String.valueOf(map.get(i + I[k]).charAt(j + J[k])))) {
                findPath(map, i + I[k], j + J[k], i, j);
                break;
            }
        }

    }

    public List<Point> getTracePath() {
        return tracePath;
    }
}
