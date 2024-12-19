package gui;

import models.Product;
import services.ProductService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductGUI {
    private ProductService productService = new ProductService();

    public void show() {
        JFrame frame = new JFrame("Warehouse PoS - Product Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window only
        frame.setLayout(new BorderLayout());

        JTextArea productList = new JTextArea();
        productList.setEditable(false);
        frame.add(new JScrollPane(productList), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();
        JButton addButton = new JButton("Add Product");
        JButton updateButton = new JButton("Update Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton backButton = new JButton("Back to Main Menu");

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(backButton);

        frame.add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            Product product = new Product(0, nameField.getText(),
                    Integer.parseInt(quantityField.getText()), Double.parseDouble(priceField.getText()));
            productService.addProduct(product);
            refreshProductList(productList);
        });

        updateButton.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Product ID to Update:"));
            Product product = productService.getAllProducts().stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (product != null) {
                product.setName(nameField.getText());
                product.setQuantity(Integer.parseInt(quantityField.getText()));
                product.setPrice(Double.parseDouble(priceField.getText()));
                productService.updateProduct(product);
                refreshProductList(productList);
            }
        });

        deleteButton.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Product ID to Delete:"));
            productService.deleteProduct(id);
            refreshProductList(productList);
        });

        // Back button listener
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the Product GUI
            new MainGUI().show(); // Open the Main GUI
        });

        refreshProductList(productList);
        frame.setVisible(true);
    }

    private void refreshProductList(JTextArea productList) {
        List<Product> products = productService.getAllProducts();
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append("ID: ").append(product.getId())
              .append(", Name: ").append(product.getName())
              .append(", Quantity: ").append(product.getQuantity())
              .append(", Price: ").append(product.getPrice())
              .append("\n");
        }
        productList.setText(sb.toString());
    }
}
