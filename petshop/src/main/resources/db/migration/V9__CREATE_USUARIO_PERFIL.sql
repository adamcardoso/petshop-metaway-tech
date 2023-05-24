CREATE TABLE tb_usuario_perfil (
  usuario_id UUID,
  perfil_id UUID,
  FOREIGN KEY (usuario_id) REFERENCES tb_usuario (uuid),
  FOREIGN KEY (perfil_id) REFERENCES tb_perfil (uuid)
);