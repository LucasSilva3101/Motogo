INSERT INTO motos (modelo, marca, preco_por_dia, disponivel) VALUES
('CG 160', 'Honda', 50.00, true),
('CB 500X', 'Honda', 300.00, true),
('MT-07', 'Yamaha', 450.00, true),
('Z400', 'Kawasaki', 350.00, true),
('Duke 390', 'KTM', 350.00, true);

INSERT INTO clientes (nome, email, telefone, endereco) VALUES
('João Silva', 'joao.silva@example.com', '11987654321', 'Rua A, 123, São Paulo, SP'),
('Maria Oliveira', 'maria.oliveira@example.com', '11912345678', 'Avenida B, 456, Rio de Janeiro, RJ');

INSERT INTO alugueis (cliente_id, moto_id, data_inicio, status, total_pago)
VALUES (
           (SELECT id FROM clientes WHERE email = 'joao.silva@example.com'),
           (SELECT id FROM motos WHERE modelo = 'CG 160' AND marca = 'Honda'),
           CURDATE(),
           'EM_ANDAMENTO',
           50
       );
