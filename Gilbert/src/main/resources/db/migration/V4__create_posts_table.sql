CREATE TABLE posts
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    owner_id        INT          NOT NULL,
    name            VARCHAR(255) NOT NULL,
    description     TEXT,
    price           DOUBLE       NOT NULL,
    item_condition  VARCHAR(50)  NOT NULL,
    size            VARCHAR(50),
    location        VARCHAR(100),
    status          VARCHAR(50)  NOT NULL,
    image_id        VARCHAR(255),
    brands_id       INT,
    product_type_id INT,
    date_posted_at  DATE         NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (id),
    FOREIGN KEY (brands_id) REFERENCES brands (id),
    FOREIGN KEY (product_type_id) REFERENCES product_types (id)
);

CREATE TABLE post_assets
(
    post_id  INT         NOT NULL REFERENCES posts (id),
    asset_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (post_id)
);