CREATE TABLE product_types
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(48) NOT NULL,
    order_index INT NOT NULL DEFAULT 0,
    parent_id INT NULL REFERENCES product_types(id),
    FULLTEXT (name)
);
