ALTER TABLE IF EXISTS ONLY public.product
    DROP CONSTRAINT IF EXISTS fk_product_category CASCADE,
    DROP CONSTRAINT IF EXISTS fk_product_category CASCADE;

DROP TABLE IF EXISTS public.product;
DROP TABLE IF EXISTS public.category;
DROP TABLE IF EXISTS public.supplier;
DROP TABLE IF EXISTS public.customer;


CREATE TABLE public.product
(
    id               int GENERATED ALWAYS AS IDENTITY,
    default_price    float,
    name             text,
    default_currency text,
    description      text,
    category         int,
    supplier         int,
    PRIMARY KEY (id)
);

CREATE TABLE public.category
(
    id          int GENERATED ALWAYS AS IDENTITY,
    name        text,
    department  text,
    description text,
    PRIMARY KEY (id)
);

CREATE TABLE public.supplier
(
    id          int GENERATED ALWAYS AS IDENTITY,
    name        text,
    description text,
    PRIMARY KEY (id)
);

CREATE TABLE public.customer
(
    id       int GENERATED ALWAYS AS IDENTITY,
    name     text,
    email    VARCHAR(60),
    password VARCHAR(100),
    PRIMARY KEY (id)
);

ALTER TABLE public.product
    ADD CONSTRAINT fk_product_category FOREIGN KEY (category) REFERENCES category (id),
    ADD CONSTRAINT fk_product_supplier FOREIGN KEY (supplier) REFERENCES supplier (id);


INSERT INTO public.supplier (name, description)
VALUES ('Paks2', 'Completely normal phenomenon');
INSERT INTO public.supplier (name, description)
VALUES ('NutKing', 'The best nuts in town');
INSERT INTO public.supplier (name, description)
VALUES ('Linus Torvalds', 'The biggest brain in town');
INSERT INTO public.supplier (name, description)
VALUES ('Gym Bro', 'Big health,big muscle');

INSERT INTO public.category (name, department, description)
VALUES ('Linux kernel', 'Hardware', 'The world''s most popular open-source operating systems.');
INSERT INTO public.category (name, department, description)
VALUES ('Edibles', 'Nut', 'A nut you can eat.');
INSERT INTO public.category (name, department, description)
VALUES ('Nuclear cores', 'Nuclear', 'A nut you cannot eat.');
INSERT INTO public.category (name, department, description)
VALUES ('Nutrients', 'Muscle', 'FOCUS ON YOUR HEALTH. YOUR LIFE DEPENDS ON IT');

INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (1149.8, 'Linux Kernel 4.13', 'USD', 'The stuff with your favourite penguin sliding game.', 1, 3);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (979.8, 'Linux Kernel 5.0', 'USD', 'Szamitson szojatekra.', 1, 3);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (666.6, 'Linux Kernel 5.8', 'USD', 'Good as hell.', 1, 3);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (44.4, 'Linux Kernel 5.12', 'USD', 'Fresh and reliable as a nut.', 1, 3);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (249.8, 'Regular nut', 'USD', 'For regular occasions.', 2, 2);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (4345.8, 'Christmas nut', 'USD', 'Share it with your family', 2, 2);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (3429.8, 'Oily nut', 'USD', 'Good for your skin', 2, 2);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (1000.8, 'Misterious nutto', 'USD', 'Try me - Misterious Nutto', 2, 2);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (300.8, 'Type 1 Nuclear Core', 'USD', 'Don''t ask me', 3, 1);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (475900.8, 'Not so Xplody Core', 'USD', 'For the nation.', 3, 1);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (26546.8, 'Leaky Core', 'USD', 'Don''t ask don''t tell.', 3, 1);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (1000.8, 'Indestructible Core', 'USD', 'Barely used.', 3, 1);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (469.1, 'Whey', 'USD', 'This the whey', 4, 4);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (649.1, 'Grains', 'USD', 'This is the grai... it doesn''t work', 4, 4);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (496.1, 'Vegetables', 'USD', 'It''s more likely a food, than a table.', 4, 4);
INSERT INTO public.product (default_price, name, default_currency, description, category, supplier)
VALUES (694.1, 'Fruits', 'USD', 'Smooth some juice!', 4, 4);
