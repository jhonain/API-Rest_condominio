package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.entity.Rol;
import com.vasquez.Backend_PAF_condominio.entity.TipoDocumento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TipoDocumentoService {

    Page<TipoDocumento> findAll(Pageable pageable);
    Page<TipoDocumento> search(String texto, Pageable pageable);
    TipoDocumento findById(Long id);
    TipoDocumento create(TipoDocumento tpDoc);
    TipoDocumento update(Long id, TipoDocumento tpDoc);
    void deleteById(Long id);
}
