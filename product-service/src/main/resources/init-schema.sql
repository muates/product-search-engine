CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS product (
    id UUID DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DOUBLE PRECISION NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50),
    stock_quantity INTEGER NOT NULL,
    min_stock_quantity INTEGER NOT NULL,
    category VARCHAR(50),
    image_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);
