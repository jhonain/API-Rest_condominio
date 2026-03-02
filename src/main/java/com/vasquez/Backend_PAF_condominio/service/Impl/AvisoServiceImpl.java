package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.entity.Aviso;
import com.vasquez.Backend_PAF_condominio.repository.AvisoRepository;
import com.vasquez.Backend_PAF_condominio.service.AvisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AvisoServiceImpl implements AvisoService {

    @Autowired
    private AvisoRepository repository;

    @Override
    public List<Aviso> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Aviso> findImportantes() {
        return repository.findByImportanteTrueOrderByIdDesc();
    }

    @Override
    public Aviso findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado: " + id));
    }

    @Override
    public Aviso save(Aviso aviso) {
        return repository.save(aviso);
    }

    @Override
    public Aviso update(Long id, Aviso avisoRequest) {
        Aviso existente = findById(id);
        existente.setTitulo(avisoRequest.getTitulo());
        existente.setDescripcion(avisoRequest.getDescripcion());
        existente.setTipoAviso(avisoRequest.getTipoAviso());
        existente.setImportante(avisoRequest.getImportante());
        return repository.save(existente);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
