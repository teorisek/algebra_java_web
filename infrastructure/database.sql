CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(25),
    description VARCHAR(100),
    quantity INT,
    price NUMERIC(10,2) NOT NULL,
    category_id INT REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    role_id INT REFERENCES roles(id)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    order_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    item_id INT,
    quantity INT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    item_name VARCHAR(255),
    item_description VARCHAR(255),
    CONSTRAINT fk_order FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_item FOREIGN KEY(item_id) REFERENCES item(id) ON DELETE SET NULL
);

CREATE TABLE login (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip VARCHAR(45) NOT NULL
);


-- INSERT

-- Categories
INSERT INTO category (name, description) VALUES ('Electronics', 'Electronic devices and gadgets');
INSERT INTO category (name, description) VALUES ('Books', 'Various genres of books');
INSERT INTO category (name, description) VALUES ('Clothing', 'Apparel and accessories');

-- Items for Electronics (category_id = 1)
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Smartphone', 'Latest model smartphone', 15, 699.99, 1);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Laptop', 'High-performance laptop', 7, 1199.00, 1);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Headphones', 'Noise-cancelling headphones', 20, 129.50, 1);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Smartwatch', 'Water-resistant smartwatch', 12, 249.99, 1);

-- Items for Books (category_id = 2)
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Spring Boot Guide', 'Comprehensive guide to Spring Boot', 10, 39.90, 2);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Novel', 'Bestselling fiction novel', 25, 18.50, 2);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Cookbook', 'Healthy recipes cookbook', 8, 27.75, 2);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Travel Diary', 'Diary for travel notes', 18, 12.99, 2);

-- Items for Clothing (category_id = 3)
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('T-Shirt', '100% cotton t-shirt', 30, 14.99, 3);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Jeans', 'Slim-fit blue jeans', 14, 49.90, 3);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Jacket', 'Waterproof winter jacket', 6, 89.00, 3);
INSERT INTO item (name, description, quantity, price, category_id) VALUES ('Sneakers', 'Comfortable running sneakers', 22, 59.95, 3);


INSERT INTO roles (name) VALUES ('user'), ('admin');
INSERT INTO users (username, password, enabled, role_id) VALUES
('user',  '$2a$10$5t7K5Q3w8b1R2v9yZ6t8OeWH3VpM1xtN.B7Ob2JlYY.DTH9xirVOy', true, 1),
('admin', '$2a$10$Qb8P6e1Z4v2R3y7T5w0L8eBZsEhzRTqvOq7rbdl/Bf0GtsHLMw0Vm', true, 2);
