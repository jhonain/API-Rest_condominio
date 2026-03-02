package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.entity.Aviso;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AvisoService {

    List<Aviso> findAll();
    List<Aviso> findImportantes();
    Aviso findById(Long id);
    Aviso save(Aviso aviso);
    Aviso update(Long id, Aviso aviso);
    void deleteById(Long id);

}
