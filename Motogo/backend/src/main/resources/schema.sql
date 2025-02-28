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

CREATE TABLE IF NOT EXISTS alugueis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    moto_id BIGINT NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    status ENUM('EM_ANDAMENTO', 'FINALIZADO', 'CANCELADO') NOT NULL DEFAULT 'EM_ANDAMENTO',
    total_pago DOUBLE,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (moto_id) REFERENCES motos(id)
);

CREATE OR REPLACE VIEW alugueis_com_detalhes AS
SELECT
    a.id AS aluguel_id,
    a.cliente_id,
    c.nome AS cliente_nome,
    a.moto_id,
    m.marca AS moto_marca,
    m.modelo AS moto_modelo,
    a.data_inicio,
    a.data_fim,
    a.status,
    a.total_pago
FROM alugueis a
         JOIN clientes c ON a.cliente_id = c.id
         JOIN motos m ON a.moto_id = m.id;

