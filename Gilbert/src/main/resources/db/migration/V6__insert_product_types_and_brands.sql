-- Insert brands
INSERT INTO brands (name) VALUES
                              ('Nike'),
                              ('Adidas'),
                              ('H&M'),
                              ('Zara'),
                              ('Gucci'),
                              ('Louis Vuitton'),
                              ('Uniqlo'),
                              ('The North Face'),
                              ('Supreme'),
                              ('Champion');


INSERT INTO product_types (name, parent_id, order_index)
VALUES
    ('Women', null, 1),
    ('Men', null, 2),
    ('Designers', null, 3),
    ('Home', null, 4),
    ('Beauty', null, 5);


SET @women_id = (SELECT id FROM product_types WHERE name = 'Women');
SET @men_id = (SELECT id FROM product_types WHERE name = 'Men');


INSERT INTO product_types (name, parent_id, order_index)
VALUES
    ('Clothing', @women_id, 10),
    ('Shoes', @women_id, 11),
    ('Accessories', @women_id, 12),
    ('Bags & luggage', @women_id, 13),
    ('Jewelery', @women_id, 14),

    ('Clothing', @men_id, 20),
    ('Shoes', @men_id, 21),
    ('Accessories', @men_id, 22),
    ('Bags & luggage', @men_id, 23),
    ('Jewelery', @men_id, 24);


SET @women_clothing_id = (SELECT id FROM product_types WHERE name = 'Clothing' AND parent_id = @women_id);
SET @men_clothing_id = (SELECT id FROM product_types WHERE name = 'Clothing' AND parent_id = @men_id);


INSERT INTO product_types (name, parent_id, order_index)
VALUES
    ('Dresses', @women_clothing_id, 100),
    ('Tops', @women_clothing_id, 101),
    ('T-shirts', @women_clothing_id, 102),
    ('Sweaters', @women_clothing_id, 103),
    ('Hoodies', @women_clothing_id, 104),
    ('Jackets & Coats', @women_clothing_id, 105),
    ('Pants', @women_clothing_id, 106),
    ('Jeans', @women_clothing_id, 107),
    ('Skirts', @women_clothing_id, 108),
    ('Shorts', @women_clothing_id, 109),
    ('Activewear', @women_clothing_id, 110),
    ('Swimwear', @women_clothing_id, 111);


INSERT INTO product_types (name, parent_id, order_index)
VALUES
    ('T-shirts', @men_clothing_id, 200),
    ('Shirts', @men_clothing_id, 201),
    ('Sweaters', @men_clothing_id, 202),
    ('Hoodies', @men_clothing_id, 203),
    ('Jackets & Coats', @men_clothing_id, 204),
    ('Pants', @men_clothing_id, 205),
    ('Jeans', @men_clothing_id, 206),
    ('Shorts', @men_clothing_id, 207),
    ('Activewear', @men_clothing_id, 208),
    ('Swimwear', @men_clothing_id, 209);