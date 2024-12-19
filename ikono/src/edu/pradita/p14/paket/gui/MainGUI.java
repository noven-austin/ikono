package gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI {

    public void show() {
        JFrame frame = new JFrame("Warehouse PoS - Main Menu");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JButton productButton = new JButton("Manage Products");
        JButton packageButton = new JButton("Manage Packages");
        JButton menuButton = new JButton("View Menu");

        frame.add(productButton);
        frame.add(packageButton);
        frame.add(menuButton);

        // Redirect to ProductGUI
        productButton.addActionListener(e -> {
            frame.dispose(); // Close MainGUI
            new ProductGUI().show();
        });

        // Redirect to PackageGUI
        packageButton.addActionListener(e -> {
            frame.dispose(); // Close MainGUI
            new PackageGUI().show();
        });

        // Show Menu
        menuButton.addActionListener(e -> {
            new MenuView().show();
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI().show();
    }
}
