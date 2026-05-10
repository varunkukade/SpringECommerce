-- Baseline schema aligned with JPA entities (BaseEntity audit + soft delete).

CREATE TABLE addresses (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL,
    lattitude DOUBLE NULL,
    longitude DOUBLE NULL,
    description VARCHAR(255) NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    image_url VARCHAR(255) NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id)
) ENGINE=InnoDB;

CREATE TABLE orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL,
    address_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES addresses (id)
) ENGINE=InnoDB;

CREATE TABLE order_products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    count INT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_products_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_order_products_product FOREIGN KEY (product_id) REFERENCES products (id)
) ENGINE=InnoDB;
