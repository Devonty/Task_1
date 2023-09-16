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
    private int x0, y0;
    private int tick = 0;

    public Trace(String mapName, int cellSize, int x0, int y0) {
        this.x0 = x0;
        this.y0 = y0;
        this.cellSize = cellSize;
        try {
            loadFromFile(mapName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        tick++;
        Color saveColor = g.getColor();
        // ...
        for (Road road : roads) {
            road.draw(g);
        }
        // ...
        for (Bleachers bleacher : bleachers) {
            bleacher.draw(g);
        }
        // DEBUG
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
        g.fillOval((int) (tracePath.get((tick / 2) % tracePath.size()).getX() - r),
                (int) (tracePath.get((tick / 2) % tracePath.size()).getY() - r),
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
        Road road;

        List<String> map = new ArrayList<>();

        int i = 0, x, y;
        int iS = -1, jS = -1, i0 = Integer.MAX_VALUE, j0 = Integer.MAX_VALUE, i1 = 0, j1 = 0;
        // Trace
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            map.add(line);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '.') continue;
                // start save
                if (line.charAt(j) == 's') {
                    iS = i;
                    jS = j;
                }
                // borders save
                i0 = Math.min(i0, i);
                j0 = Math.min(j0, j);
                i1 = Math.max(i1, i);
                j1 = Math.max(j1, j);


                x = j * cellSize;
                y = i * cellSize;
                road = getRoadByChar(line.charAt(j), x0 + x, y0 + y);
                assert road != null;
                road.setCellSize(cellSize);
                roads.add(road);
            }
            i++;
        }

        // Path
        findPath(map, iS, jS, 0, 0);
        i = map.size();

        System.out.println(tracePath.size());
        for (i = 0; i < tracePath.size(); i++) {
            tracePath.set(i, new Point(tracePath.get(i).x + x0, tracePath.get(i).y + y0));
            System.out.println((i + 1) + ") " + tracePath.get(i));
        }

        // Bleachers
        i0--;
        j0--;
        i1++;
        j1++;
        Bleachers bleachers;
        // top/button
        for (int j = j0 + 1; j < j1; j++) {
            bleachers = new Bleachers(x0 + j * cellSize, y0 + i0 * cellSize, cellSize);
            this.bleachers.add(bleachers);

            bleachers = new Bleachers(x0 + j * cellSize, y0 + i1 * cellSize, cellSize);
            bleachers.setDirection(Math.PI);
            this.bleachers.add(bleachers);
        }
        for (i = i0 + 1; i < i1; i++) {
            bleachers = new Bleachers(x0 + j0 * cellSize, y0 + i * cellSize, cellSize);
            bleachers.setDirection(-Math.PI / 2);
            this.bleachers.add(bleachers);

            bleachers = new Bleachers(x0 + j1 * cellSize, y0 + i * cellSize, cellSize);
            bleachers.setDirection(Math.PI / 2);
            this.bleachers.add(bleachers);
        }


    }

    private Road getRoadByChar(char ltr, int x, int y) {
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
