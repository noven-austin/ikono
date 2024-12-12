package edu.pradita.p14.javafx;

import java.sql.*;

public class DisplayShippingMerek {
    public static void main(String[] args) {
        // URL database, username, dan password MySQL Anda
        String url = "jdbc:mysql://localhost:3306/shippingmerek"; // Sesuaikan dengan nama database dan host Anda
        String username = "root"; // Username MySQL Anda
        String password = "1234"; // Password MySQL Anda

        // Query SQL untuk mengambil data dari tabel
        String query = "SELECT * FROM shippingmerek";

        // Menampilkan hasil query
        try {
            // Menghubungkan ke database
            Connection connection = DriverManager.getConnection(url, username, password);
            // Membuat Statement untuk menjalankan query
            Statement statement = connection.createStatement();
            // Menjalankan query dan mendapatkan hasil
            ResultSet resultSet = statement.executeQuery(query);

            // Menampilkan data hasil query
            System.out.println("ID | Name | Harga | Jenis");
            System.out.println("-----------------------------------");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double harga = resultSet.getDouble("harga");
                String jenis = resultSet.getString("jenis");

                // Menampilkan setiap record dalam tabel
                System.out.printf("%d | %s | %.2f | %s\n", id, name, harga, jenis);
            }

            // Menutup koneksi
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            // Menangani kesalahan yang terjadi saat koneksi atau query
            e.printStackTrace();
        }
    }
}
