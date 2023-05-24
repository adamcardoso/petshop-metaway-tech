CREATE TABLE tb_endereco (
  uuid UUID PRIMARY KEY,
  cliente_id UUID,
  logradouro VARCHAR(255),
  cidade VARCHAR(255),
  bairro VARCHAR(255),
  complemento VARCHAR(255),
  tag VARCHAR(255),
  FOREIGN KEY (cliente_id) REFERENCES tb_cliente (uuid)
);
