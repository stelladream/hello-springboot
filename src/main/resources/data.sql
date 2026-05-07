-- Initial sample data
-- Executed after Hibernate creates the schema (spring.jpa.defer-datasource-initialization=true)
-- Active only when spring.sql.init.mode=always  (set in application-dev.properties)

INSERT INTO products (name, price, description) VALUES
('Laptop',   1500000, 'High-performance developer laptop - Intel i7, 16GB RAM'),
('Mouse',      35000, 'Wireless Bluetooth mouse - 3 buttons, adjustable DPI'),
('Keyboard',   89000, 'Mechanical full-size keyboard - Blue switch'),
('Monitor',   450000, '27-inch QHD IPS monitor - 144Hz'),
('Webcam',     75000, '1080p Full HD webcam for video conferencing');
