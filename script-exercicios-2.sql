
-- Tabela de clientes
CREATE TABLE clientes (
    cliente_id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL
);

-- Tabela de pedidos
CREATE TABLE pedidos (
    pedido_id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    data_pedido TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id)
);

-- Tabela de produtos
CREATE TABLE produtos (
    produto_id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco_base DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL
);

-- Tabela de itens do pedido
CREATE TABLE itens_pedido (
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (pedido_id, produto_id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(pedido_id),
    FOREIGN KEY (produto_id) REFERENCES produtos(produto_id)
);

-- Clientes
INSERT INTO clientes (nome, cpf) VALUES
('João Silva', '111.111.111-11'),
('Maria Oliveira', '222.222.222-22'),
('Carlos Pereira', '333.333.333-33');

-- Produtos
INSERT INTO produtos (nome, preco_base, estoque) VALUES
('Camisa', 50.00, 100),
('Tênis', 200.00, 50),
('Mochila', 120.00, 30),
('Relógio', 300.00, 20);

-- Pedidos
INSERT INTO pedidos (cliente_id, data_pedido) VALUES
(1, '2025-08-01'),
(2, '2025-08-02'),
(1, '2025-08-03');

-- Itens dos pedidos
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
-- Pedido 1 (João)
(1, 1, 2, 50.00),  -- 2 Camisas
(1, 2, 1, 200.00), -- 1 Tênis
-- Pedido 2 (Maria)
(2, 3, 1, 120.00), -- 1 Mochila
-- Pedido 3 (João)
(3, 4, 1, 300.00), -- 1 Relógio
(3, 1, 1, 50.00);  -- 1 Camisa



SELECT 
    p.pedido_id,
    c.cliente_id,
    c.nome AS cliente_nome,
    c.cpf,
    i.produto_id,
    pr.nome AS produto_nome,
    i.quantidade,
    i.preco_unitario,
    (i.quantidade * i.preco_unitario) AS total_item
FROM pedidos p
INNER JOIN clientes c 
    ON p.cliente_id = c.cliente_id
INNER JOIN itens_pedido i 
    ON p.pedido_id = i.pedido_id
INNER JOIN produtos pr 
    ON i.produto_id = pr.produto_id
ORDER BY p.pedido_id, i.produto_id;