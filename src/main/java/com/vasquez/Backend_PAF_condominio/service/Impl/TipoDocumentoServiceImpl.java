package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.entity.TipoDocumento;
import com.vasquez.Backend_PAF_condominio.repository.TipoDocumentoRepository;
import com.vasquez.Backend_PAF_condominio.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository repository;


    @Override
    public Page<TipoDocumento> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<TipoDocumento> search(String texto, Pageable pageable) {
        if (texto == null || texto.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByNombreContainingIgnoreCase(texto, pageable);
    }

    @Override
    public TipoDocumento findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public TipoDocumento create(TipoDocumento tpDoc) {
        return repository.save(tpDoc);
    }

    @Override
    public TipoDocumento update(Long id, TipoDocumento tpDoc) {
        TipoDocumento existe = findById(id);
        existe.setNombre(tpDoc.getNombre());

        return repository.save(existe);
    }

    @Override
    public void deleteById(Long id) {

        repository.deleteById(id);
    }
}
