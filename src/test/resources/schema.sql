CREATE TABLE category
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE item (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(25),
                      description VARCHAR(100),
                      quantity INT,
                      category_id INT,
                      CONSTRAINT fk_item_category
                          FOREIGN KEY (category_id)
                              REFERENCES category(id)
);