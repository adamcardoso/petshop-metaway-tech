package com.metaway.petshop.services.validation;

import com.metaway.petshop.dto.UsuarioUpdateDTO;
import com.metaway.petshop.entities.Usuario;
import com.metaway.petshop.exceptions.FieldMessage;
import com.metaway.petshop.repositories.UsuarioRepository;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdateValid, UsuarioUpdateDTO> {

    private final HttpServletRequest request;

    private final UsuarioRepository usuarioRepository;

    public UsuarioUpdateValidator(HttpServletRequest request, UsuarioRepository repository) {
        this.request = request;
        this.usuarioRepository = repository;
    }

    @Override
    public boolean isValid(UsuarioUpdateDTO dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Usuario user = usuarioRepository.findByNomeDoUsuario(dto.nomeDoUsuario());
        if (user != null && !Long.valueOf(userId).equals(user.getUsuarioUuid().getMostSignificantBits())) {
            list.add(new FieldMessage("username", "Username already exists"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}
