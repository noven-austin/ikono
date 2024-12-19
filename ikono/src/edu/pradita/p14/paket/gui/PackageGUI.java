package gui;

import models.Package;
import models.Product;
import services.PackageService;
import services.ProductService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PackageGUI {
    private PackageService packageService = new PackageService();
    private ProductService productService = new ProductService();

    public void show() {
        JFrame frame = new JFrame("Warehouse PoS - Package Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window only
        frame.setLayout(new BorderLayout());

        JTextArea packageList = new JTextArea();
        packageList.setEditable(false);
        frame.add(new JScrollPane(packageList), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JButton addPackageButton = new JButton("Create Package");
        JButton deletePackageButton = new JButton("Delete Package");
        JButton backButton = new JButton("Back to Main Menu");

        panel.add(addPackageButton);
        panel.add(deletePackageButton);
        panel.add(backButton);

        frame.add(panel, BorderLayout.SOUTH);

        addPackageButton.addActionListener(e -> {
            String packageName = JOptionPane.showInputDialog("Enter Package Name:");
            String packageDescription = JOptionPane.showInputDialog("Enter Package Description (Optional):");

            List<Product> selectedProducts = selectProductsForPackage(frame);

            if (selectedProducts.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No products selected. Package creation cancelled.");
                return;
            }

            double totalPrice = selectedProducts.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
            Package newPackage = new Package(0, packageName, packageDescription, totalPrice);
            packageService.addPackage(newPackage, selectedProducts);

            JOptionPane.showMessageDialog(frame, "Package created successfully!");
            refreshPackageList(packageList);
        });

        deletePackageButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Package ID to Delete:");
            packageService.deletePackage(Integer.parseInt(id));
            JOptionPane.showMessageDialog(frame, "Package deleted successfully!");
            refreshPackageList(packageList);
        });

        // Back button listener
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the Package GUI
            new MainGUI().show(); // Open the Main GUI
        });

        refreshPackageList(packageList);
        frame.setVisible(true);
    }

    private List<Product> selectProductsForPackage(JFrame parentFrame) {
        List<Product> products = productService.getAllProducts();
        List<Product> selectedProducts = new ArrayList<>();

        // Create a panel to display products with checkboxes
        JPanel productSelectionPanel = new JPanel(new GridLayout(products.size(), 1));
        List<JCheckBox> checkBoxes = new ArrayList<>();
        List<JTextField> quantityFields = new ArrayList<>();

        for (Product product : products) {
            JPanel productRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JCheckBox checkBox = new JCheckBox(product.getName() + " ($" + product.getPrice() + ")");
            JTextField quantityField = new JTextField("1", 5);

            checkBoxes.add(checkBox);
            quantityFields.add(quantityField);

            productRow.add(checkBox);
            productRow.add(new JLabel("Quantity:"));
            productRow.add(quantityField);
            productSelectionPanel.add(productRow);
        }

        int result = JOptionPane.showConfirmDialog(
                parentFrame,
                new JScrollPane(productSelectionPanel),
                "Select Products for Package",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                JCheckBox checkBox = checkBoxes.get(i);
                JTextField quantityField = quantityFields.get(i);

                if (checkBox.isSelected()) {
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        if (quantity > 0) {
                            Product product = products.get(i);
                            selectedProducts.add(new Product(product.getId(), product.getName(), quantity, product.getPrice()));
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(parentFrame, "Invalid quantity for " + checkBox.getText());
                    }
                }
            }
        }

        return selectedProducts;
    }

    private void refreshPackageList(JTextArea packageList) {
        List<Package> packages = packageService.getAllPackages();
        StringBuilder sb = new StringBuilder();
        for (Package pkg : packages) {
            sb.append("ID: ").append(pkg.getId())
              .append(", Name: ").append(pkg.getName())
              .append(", Description: ").append(pkg.getDescription() == null ? "None" : pkg.getDescription())
              .append(", Price: ").append(pkg.getPrice())
              .append("\n");
        }
        packageList.setText(sb.toString());
    }
}
