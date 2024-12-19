package gui;

import models.Package;
import models.Product;
import services.PackageService;
import services.ProductService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuView {
    private ProductService productService = new ProductService();
    private PackageService packageService = new PackageService();

    public void show() {
        JFrame frame = new JFrame("Warehouse PoS - Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window only
        frame.setLayout(new BorderLayout());

        JTextArea menuDisplay = new JTextArea();
        menuDisplay.setEditable(false);
        frame.add(new JScrollPane(menuDisplay), BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Menu");
        frame.add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            frame.dispose(); // Close MenuView
            new MainGUI().show(); // Open the Main GUI
        });

        refreshMenu(menuDisplay);
        frame.setVisible(true);
    }

    private void refreshMenu(JTextArea menuDisplay) {
        StringBuilder sb = new StringBuilder();

        sb.append("Products:\n");
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            sb.append("ID: ").append(product.getId())
              .append(", Name: ").append(product.getName())
              .append(", Quantity: ").append(product.getQuantity())
              .append(", Price: ").append(product.getPrice())
              .append("\n");
        }

        sb.append("\nPackages:\n");
        List<Package> packages = packageService.getAllPackages();
        for (Package pkg : packages) {
            sb.append("ID: ").append(pkg.getId())
              .append(", Name: ").append(pkg.getName())
              .append(", Description: ").append(pkg.getDescription() == null ? "None" : pkg.getDescription())
              .append(", Price: ").append(pkg.getPrice())
              .append("\n");
        }

        menuDisplay.setText(sb.toString());
    }
}
