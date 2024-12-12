

-- Create the table with appropriate data types
CREATE TABLE shippingmerek (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    harga DECIMAL(10, 2),
    jenis VARCHAR(20)
);

-- Insert values into the table
INSERT INTO shippingmerek (name, harga, jenis) VALUES 
    ('JNE', 4000.00, 'Instant'),
    ('JNE', 3500.00, 'Sameday'),
    ('JNT', 5400.00, 'Sameday'),
    ('SiCepat', 6000.00, 'Sameday'),
    ('JNE', 2500.00, 'Reguler'),
    ('JNT', 3800.00, 'Reguler'),
    ('SiCepat', 4500.00, 'Reguler'),
    ('Pos Indonesia', 3000.00, 'Reguler'),
    ('JNE', 12000.00, 'Express'),
    ('JNT', 15000.00, 'Express'),
    ('SiCepat', 18000.00, 'Express'),
    ('Pos Indonesia', 10000.00, 'Express'),
    ('JNE', 8000.00, 'Economy'),
    ('JNT', 9000.00, 'Economy'),
    ('SiCepat', 10000.00, 'Economy'),
    ('Pos Indonesia', 7000.00, 'Economy'),
    ('JNE', 5000.00, 'Same Day'),
    ('JNT', 6000.00, 'Same Day'),
    ('SiCepat', 7000.00, 'Same Day'),
    ('Pos Indonesia', 4000.00, 'Same Day'),
    ('JNE', 2000.00, 'Next Day'),
    ('JNT', 2500.00, 'Next Day'),
    ('SiCepat', 3000.00, 'Next Day'),
    ('Pos Indonesia', 1500.00, 'Next Day');
    ;

-- Select all rows from the table
SELECT * FROM shippingmerek;

DELETE t1
FROM (
  SELECT *, ROW_NUMBER() OVER (PARTITION BY id, name, harga, jenis) as row_num
  FROM shipping_rate
) t1
WHERE row_num > 1;

DELETE from shippingmerek where id <> '';