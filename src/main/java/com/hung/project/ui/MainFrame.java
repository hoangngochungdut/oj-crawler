package com.hung.project.ui;
import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("OJ Crawler");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new UserPanel());
        setVisible(true);
    }
}
    
    