CREATE TABLE user_favorites (
    user_id INT NOT NULL REFERENCES users (id),
    post_id INT NOT NULL REFERENCES posts (id),
    PRIMARY KEY (user_id, post_id)
);