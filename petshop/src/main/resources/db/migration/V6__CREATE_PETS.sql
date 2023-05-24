CREATE TABLE tb_pets (
  pets_Uuid UUID PRIMARY KEY,
  cliente_id UUID,
  raca_id UUID,
  data_De_Nascimento_Do_Pet DATE,
  nome_Do_Pet VARCHAR(255),
  FOREIGN KEY (cliente_id) REFERENCES tb_cliente (cliente_Uuid),
  FOREIGN KEY (raca_id) REFERENCES tb_raca (raca_Uuid)
);