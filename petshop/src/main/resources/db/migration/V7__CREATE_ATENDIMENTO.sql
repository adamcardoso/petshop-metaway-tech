CREATE TABLE tb_atendimento (
  atendimentoUuid UUID PRIMARY KEY,
  pet_id UUID,
  descricaoDoAtendimento VARCHAR(255),
  valorDoAtendimento VARCHAR(255),
  dataDoAtendimento VARCHAR(255),
  FOREIGN KEY (pet_id) REFERENCES tb_pets (petsUuid)
);
