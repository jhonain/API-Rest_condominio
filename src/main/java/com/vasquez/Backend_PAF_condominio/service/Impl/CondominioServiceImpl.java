package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import com.vasquez.Backend_PAF_condominio.repository.CondominioRepository;
import com.vasquez.Backend_PAF_condominio.service.CloudinaryService;
import com.vasquez.Backend_PAF_condominio.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CondominioServiceImpl implements CondominioService {


    @Autowired
    private CondominioRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

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
    public Condominio create(Condominio condominio, MultipartFile archivo) throws IOException {
        if (archivo != null && !archivo.isEmpty()) {
            String url = cloudinaryService.subirImagen(archivo, "condominios");
            condominio.setImagenUrl(url);
        }
        return repository.save(condominio);
    }

    @Override
    @Transactional
    public Condominio update(Long id, Condominio condominio, MultipartFile archivo) throws IOException {
        Condominio existe = findById(id);
        existe.setNombre(condominio.getNombre());
        existe.setDireccion(condominio.getDireccion());
        existe.setTotalUnidades(condominio.getTotalUnidades());
        existe.setEstado(condominio.getEstado());

        if (archivo != null && !archivo.isEmpty()) {
            String url = cloudinaryService.subirImagen(archivo, "condominios");
            existe.setImagenUrl(url);
        }
        return repository.save(existe);
    }


    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Condominio subirFoto(Long id, MultipartFile archivo) throws IOException {
        Condominio condominio = findById(id);
        String url = cloudinaryService.subirImagen(archivo, "condominios"); // carpeta en Cloudinary
        condominio.setImagenUrl(url);
        return repository.save(condominio);
    }
}
