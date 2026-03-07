CREATE TABLE IF NOT EXISTS users_adm (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS clientes (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    data_nasc DATE
    );

CREATE TABLE IF NOT EXISTS motos (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     modelo VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    preco_por_dia DECIMAL(10,2) NOT NULL,
    disponivel BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS alugueis (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        cliente_id BIGINT NOT NULL,
                                        moto_id BIGINT NOT NULL,
                                        data_inicio DATE NOT NULL,
                                        data_fim DATE,
                                        status VARCHAR(20) NOT NULL,
    total_pago DECIMAL(10,2),
    CONSTRAINT fk_aluguel_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_aluguel_moto FOREIGN KEY (moto_id) REFERENCES motos(id)
    );