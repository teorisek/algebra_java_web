-- Categories
INSERT INTO category (name, description) VALUES ('Electronics', 'Electronic devices and gadgets');
INSERT INTO category (name, description) VALUES ('Books', 'Various genres of books');
INSERT INTO category (name, description) VALUES ('Clothing', 'Apparel and accessories');

-- Items for Electronics (category_id = 1)
INSERT INTO item (name, description, quantity, category_id) VALUES ('Smartphone', 'Latest model smartphone', 15, 1);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Laptop', 'High-performance laptop', 7, 1);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Headphones', 'Noise-cancelling headphones', 20, 1);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Smartwatch', 'Water-resistant smartwatch', 12, 1);

-- Items for Books (category_id = 2)
INSERT INTO item (name, description, quantity, category_id) VALUES ('Spring Boot Guide', 'Comprehensive guide to Spring Boot', 10, 2);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Novel', 'Bestselling fiction novel', 25, 2);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Cookbook', 'Healthy recipes cookbook', 8, 2);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Travel Diary', 'Diary for travel notes', 18, 2);

-- Items for Clothing (category_id = 3)
INSERT INTO item (name, description, quantity, category_id) VALUES ('T-Shirt', '100% cotton t-shirt', 30, 3);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Jeans', 'Slim-fit blue jeans', 14, 3);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Jacket', 'Waterproof winter jacket', 6, 3);
INSERT INTO item (name, description, quantity, category_id) VALUES ('Sneakers', 'Comfortable running sneakers', 22, 3);
