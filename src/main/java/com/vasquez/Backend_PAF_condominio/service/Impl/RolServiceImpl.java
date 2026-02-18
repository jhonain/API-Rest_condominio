package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.entity.Rol;
import com.vasquez.Backend_PAF_condominio.repository.RolRepository;
import com.vasquez.Backend_PAF_condominio.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    private RolRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<Rol> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rol> search(String texto, Pageable pageable) {
        if (texto == null || texto.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByNombreContainingIgnoreCase(texto, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Rol create(Rol rol) {

        return repository.save(rol);
    }

    @Override
    public Rol update(Long id, Rol rol) {
        Rol existente=findById(id);
        existente.setNombre(rol.getNombre());
        return repository.save(existente);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
