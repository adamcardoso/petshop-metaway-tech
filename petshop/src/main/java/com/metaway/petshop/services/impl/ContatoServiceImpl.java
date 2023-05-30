package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.ContatoDTO;
import com.metaway.petshop.entities.Contato;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.ContatoRepository;
import com.metaway.petshop.services.interfaces.ContatoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContatoServiceImpl implements ContatoService {

    private final ContatoRepository contatoRepository;

    public ContatoServiceImpl(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    @Override
    public ContatoDTO findById(UUID uuid) {
        Contato entity = contatoRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Contato not found: " + uuid));
        return new ContatoDTO(entity);
    }

    @Override
    public List<ContatoDTO> findAll() {
        Iterable<Contato> contatos = contatoRepository.findAll();
        return StreamSupport.stream(contatos.spliterator(), false)
                .map(ContatoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ContatoDTO insert(ContatoDTO dto) {
        Contato entity = new Contato();
        copyDtoToEntity(dto, entity);
        entity = contatoRepository.save(entity);
        return new ContatoDTO(entity);
    }

    @Override
    public ContatoDTO update(UUID uuid, ContatoDTO contatoDTO) {
        Optional<Contato> optionalContato = contatoRepository.findById(uuid);
        Contato entity = optionalContato.orElseThrow(() -> new ResourceNotFoundException("Contato not found: " + uuid));
        copyDtoToEntity(contatoDTO, entity);
        entity = contatoRepository.save(entity);
        return new ContatoDTO(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!contatoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contato not found: " + id);
        }
        try {
            contatoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error deleting contato: " + id);
        }
    }

    private void copyDtoToEntity(ContatoDTO dto, Contato entity) {
        entity.setTag(dto.tag());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setValor(dto.valor());
        entity.setContatoUuid(dto.contatoUuid());
    }
}
