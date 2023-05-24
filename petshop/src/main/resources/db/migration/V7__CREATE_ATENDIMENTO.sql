CREATE TABLE tb_atendimento (
  atendimento_Uuid UUID PRIMARY KEY,
  pet_id UUID,
  descricao_Do_Atendimento VARCHAR(255),
  valor_Do_Atendimento VARCHAR(255),
  data_Do_Atendimento VARCHAR(255),
  FOREIGN KEY (pet_id) REFERENCES tb_pets (pets_Uuid)
);
