CREATE TABLE tb_pets (
  uuid UUID PRIMARY KEY,
  cliente_id UUID,
  raca_id UUID,
  dataDeNascimentoDoPet DATE,
  nomeDoPet VARCHAR(255),
  FOREIGN KEY (cliente_id) REFERENCES tb_cliente (uuid),
  FOREIGN KEY (raca_id) REFERENCES tb_raca (uuid)
);