package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import com.vasquez.Backend_PAF_condominio.repository.CondominioRepository;
import com.vasquez.Backend_PAF_condominio.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CondominioServiceImpl implements CondominioService {


    @Autowired
    private CondominioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<Condominio> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Condominio findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Condominio create(Condominio condominio) {
        return repository.save(condominio);
    }

    @Override
    public Condominio update(Long id, Condominio condominio) {
        Condominio existe = findById(id);
        existe.setNombre(condominio.getNombre());
        existe.setDireccion(condominio.getDireccion());
        existe.setTotalUnidades(condominio.getTotalUnidades());
        existe.setImagenUrl(condominio.getImagenUrl());
        existe.setEstado(condominio.getEstado());
        return repository.save(condominio);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
