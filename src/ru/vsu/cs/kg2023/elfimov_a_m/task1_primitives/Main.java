package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainWindow mn = new MainWindow();
        mn.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mn.setSize(800, 600);
        mn.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mn.setVisible(true);
    }
}