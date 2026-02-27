package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CondominioService {

    Page<Condominio> findAll(Pageable pageable);

    Condominio findById(Long id);

    Condominio create(Condominio condominio);

    Condominio update(Long id, Condominio condominio);

    void deleteById(Long id);

}
