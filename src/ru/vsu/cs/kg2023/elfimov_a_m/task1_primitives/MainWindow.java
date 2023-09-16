package ru.vsu.cs.kg2023.elfimov_a_m.task1_primitives;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final JPanel dp;

    public MainWindow() throws HeadlessException {
        dp = new DrawPanel();
        this.add(dp);

    }
}
