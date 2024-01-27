package org.example;


import javax.swing.*;

/**
 * main class - here the program starts
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CDTimer().setVisible(true);
            }
        });
    }
}