package services;

import models.Package;
import models.Product;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageService {

    public List<Package> getAllPackages() {
        List<Package> packages = new ArrayList<>();
        String query = "SELECT * FROM packages";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                packages.add(new Package(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packages;
    }

    public void addPackage(Package pkg, List<Product> products) {
        String packageQuery = "INSERT INTO packages (name, description, price) VALUES (?, ?, ?)";
        String packageItemsQuery = "INSERT INTO package_items (package_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pkgStmt = conn.prepareStatement(packageQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pkgItemsStmt = conn.prepareStatement(packageItemsQuery)) {

            conn.setAutoCommit(false);

            // Insert package
            pkgStmt.setString(1, pkg.getName());
            pkgStmt.setString(2, pkg.getDescription());
            pkgStmt.setDouble(3, pkg.getPrice());
            pkgStmt.executeUpdate();

            // Get the generated package ID
            ResultSet rs = pkgStmt.getGeneratedKeys();
            if (rs.next()) {
                int packageId = rs.getInt(1);

                // Insert products into package_items
                for (Product product : products) {
                    pkgItemsStmt.setInt(1, packageId);
                    pkgItemsStmt.setInt(2, product.getId());
                    pkgItemsStmt.setInt(3, product.getQuantity());
                    pkgItemsStmt.addBatch();
                }
                pkgItemsStmt.executeBatch();
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePackage(int packageId) {
        String packageItemsQuery = "DELETE FROM package_items WHERE package_id = ?";
        String packageQuery = "DELETE FROM packages WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pkgItemsStmt = conn.prepareStatement(packageItemsQuery);
             PreparedStatement pkgStmt = conn.prepareStatement(packageQuery)) {

            conn.setAutoCommit(false);

            // Delete package items
            pkgItemsStmt.setInt(1, packageId);
            pkgItemsStmt.executeUpdate();

            // Delete package
            pkgStmt.setInt(1, packageId);
            pkgStmt.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
