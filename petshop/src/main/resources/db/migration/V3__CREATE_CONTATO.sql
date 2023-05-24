CREATE TABLE tb_contato (
  contatoUuid UUID PRIMARY KEY,
  tag VARCHAR(255),
  email VARCHAR(255),
  telefone VARCHAR(255),
  valor VARCHAR(255),
  cliente_id UUID,
  FOREIGN KEY (cliente_id) REFERENCES tb_cliente (clienteUuid)
);