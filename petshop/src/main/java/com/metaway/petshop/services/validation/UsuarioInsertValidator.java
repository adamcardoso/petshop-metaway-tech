package com.metaway.petshop.services.validation;

import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.entities.Usuario;
import com.metaway.petshop.exceptions.FieldMessage;
import com.metaway.petshop.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsertValid, UsuarioInsertDTO> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(UsuarioInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        Usuario usuario = usuarioRepository.findByNomeDoUsuario(dto.getNomeDoUsuario());
        if (usuario != null) {
            list.add(new FieldMessage("usuário", "Usuário já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
