package com.noahkoenig.ethos.gui;

import javax.swing.JFrame;

import com.noahkoenig.ethos.grid.Grid;

public class Window {

    public Window(String title, int width, int height, Grid grid) {
        JFrame myFrame = new JFrame(title);       
        myFrame.setSize(width, height);
        myFrame.setVisible(true);
    }
}
