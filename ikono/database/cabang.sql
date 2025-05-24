CREATE TABLE Cabang (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- Primary Key (unik untuk setiap cabang), akan bertambah otomatis.
    kodePos VARCHAR(10) NOT NULL,           -- Kode pos cabang (maksimal 10 karakter).
    namaCabang VARCHAR(100) NOT NULL,       -- Nama cabang (maksimal 100 karakter).
    nomorTelepon VARCHAR(20),               -- Nomor telepon cabang (maksimal 20 karakter).
    alamat VARCHAR(255)                     -- Alamat cabang (maksimal 255 karakter).
);
