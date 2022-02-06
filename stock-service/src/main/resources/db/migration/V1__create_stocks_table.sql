CREATE TABLE IF NOT EXISTS STOCK (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    symbol         varchar(10) not null,
    price          decimal(15,2) not null,
    currency       varchar (10) not null,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );