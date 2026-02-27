package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.UnidadRequestDTO;
import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import com.vasquez.Backend_PAF_condominio.entity.Unidad;
import com.vasquez.Backend_PAF_condominio.repository.CondominioRepository;
import com.vasquez.Backend_PAF_condominio.repository.UnidadRepository;
import com.vasquez.Backend_PAF_condominio.service.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadServiceImpl implements UnidadService {

    @Autowired
    private UnidadRepository unidadRepository;

    @Autowired
    private CondominioRepository condominioRepository;


    @Override
    public List<Unidad> findByCondominio(Long condominioId) {
        return unidadRepository.findByCondominioId(condominioId);
    }

    @Override
    public Unidad findById(Long id) {
        return unidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada"));
    }

    @Override
    public Unidad create(UnidadRequestDTO r) {
        Condominio condominio = condominioRepository.findById(r.getCondominioId())
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado"));

        Unidad u = new Unidad();
        u.setCondominio(condominio);
        u.setCodigo(r.getCodigo());
        u.setPiso(r.getPiso());
        u.setAreaM2(r.getAreaM2());
        u.setImagenUrl(r.getImagenUrl());
        u.setEstado(r.getEstado() != null ? r.getEstado() : "DISPONIBLE");
        u.setPrecioMensual(r.getPrecioMensual());

        return unidadRepository.save(u);
    }

    @Override
    public Unidad update(Long id, UnidadRequestDTO r) {
        Unidad u = findById(id);

        if (r.getCondominioId() != null &&
                !r.getCondominioId().equals(u.getCondominio().getId())) {
            Condominio condominio = condominioRepository.findById(r.getCondominioId())
                    .orElseThrow(() -> new RuntimeException("Condominio no encontrado"));
            u.setCondominio(condominio);
        }

        u.setCodigo(r.getCodigo());
        u.setPiso(r.getPiso());
        u.setAreaM2(r.getAreaM2());
        u.setImagenUrl(r.getImagenUrl());
        u.setEstado(r.getEstado() != null ? r.getEstado() : u.getEstado());
        u.setPrecioMensual(r.getPrecioMensual());

        return unidadRepository.save(u);
    }

    @Override
    public void deleteById(Long id) {
        unidadRepository.deleteById(id);
    }
}
