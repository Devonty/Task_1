package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives;

import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.Car;
import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.Road;
import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.StraightRoad;
import ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives.elements.TurnRoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrawPanel extends JPanel {
    private List<Road> roads = new ArrayList<>();
    private Car car;
    private int cellsize;
    private int x = 600, y = 600; // for test

    final Timer timer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            car.moveToPoint(x, y);
            repaint();
        }
    });
    public DrawPanel() {
        cellsize = 120;


        try {
            loadFromFile("map1.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        car = new Car((double) (2 * cellsize), (double) (4 * cellsize / 3.));
        car.setCellSize(cellsize);


        timer.setInitialDelay(0);
        timer.start();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });

    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;

        g.setColor(new Color(29, 182, 44));
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // background grass

        for (Road road : roads) {
            road.draw(g);
        }
        car.draw(g);
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
                x = j * cellsize;
                y = i * cellsize;
                if (line.charAt(j) == '=') {
                    road = new StraightRoad(x, y);
                }
                if (line.charAt(j) == '|') {
                    road = new StraightRoad(x, y);
                    road.setDirection(StraightRoad.VERTICAL);
                }
                if(line.charAt(j) == '1'){
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.RIGHTDOWN);
                }
                if(line.charAt(j) == '2'){
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.LEFTDOWN);
                }
                if(line.charAt(j) == '3'){
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.LEFTUP);
                }
                if(line.charAt(j) == '4'){
                    road = new TurnRoad(x, y);
                    road.setDirection(TurnRoad.RIGHTUP);
                }
                road.setCellSize(cellsize);
                roads.add(road);
            }
            i++;
        }
    }
}
