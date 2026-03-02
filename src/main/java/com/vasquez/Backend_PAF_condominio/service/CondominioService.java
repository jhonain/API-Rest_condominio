package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CondominioService {

    Page<Condominio> findAll(Pageable pageable);

    Condominio findById(Long id);

    Condominio create(Condominio condominio, MultipartFile archivo) throws IOException;


    Condominio update(Long id, Condominio condominio, MultipartFile archivo) throws IOException;

    void deleteById(Long id);

    Condominio subirFoto(Long id, MultipartFile archivo) throws IOException;

}
