CREATE TABLE IF NOT EXISTS motos (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     modelo VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    preco_por_dia DOUBLE NOT NULL,
    disponivel BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    endereco VARCHAR(255),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
