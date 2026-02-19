package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.PersonaUserDTO;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import com.vasquez.Backend_PAF_condominio.entity.Rol;
import com.vasquez.Backend_PAF_condominio.entity.TipoDocumento;
import com.vasquez.Backend_PAF_condominio.entity.Usuario;
import com.vasquez.Backend_PAF_condominio.repository.PersonaRepository;
import com.vasquez.Backend_PAF_condominio.repository.RolRepository;
import com.vasquez.Backend_PAF_condominio.repository.TipoDocumentoRepository;
import com.vasquez.Backend_PAF_condominio.repository.UsuarioRepository;
import com.vasquez.Backend_PAF_condominio.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepo; // Necesitas crear este Repo

    @Autowired
    private RolRepository rolRepo; // Necesitas crear este Repo

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //@Autowired
    //private PasswordEncoder passwordEncoder; // Para seguridad

    @Override
    @Transactional(readOnly = true)
    public Page<Persona> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Persona> search(String texto, Pageable pageable) {
        if (texto == null || texto.isBlank()){
            repository.findAll(pageable);
        }
        return repository.findByNombreContainingIgnoreCase(texto, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Persona update(Long id, Persona per) {
        Persona existe = findById(id);
        existe.setNombre(per.getNombre());
        existe.setApellidos(per.getApellidos());
        existe.setFechaNac(per.getFechaNac());
        existe.setEmail(per.getEmail());
        existe.setTelefono(per.getTelefono());
        existe.setEstado(per.isEstado());
        existe.setFechaRegis(per.getFechaRegis());
        return repository.save(existe);
    }

    @Override
    @Transactional
    public Persona create(Persona per) {
        return repository.save(per);
    }

    @Override
    public void deleteById(Long id) {
        Persona persona = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        // Borra los roles del usuario primero (tabla usuario_rol)
        if (persona.getUsuario() != null) {
            persona.getUsuario().getRoles().clear();
            usuarioRepo.save(persona.getUsuario()); // ← actualiza usuario_rol
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void guardarPersonaUser(PersonaUserDTO dto) {

        // 1. Mapear y guardar Persona
        Persona persona = new Persona();
        persona.setNombre(dto.getNombre());
        persona.setApellidos(dto.getApellidos());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNac(dto.getFechaNac());
        persona.setEstado(true);
        persona.setFechaRegis(LocalDate.now());

        // Dentro de registrarNuevoResidente
        TipoDocumento td = tipoDocumentoRepo.findById(dto.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no válido"));

        persona.setTipoDocumento(td);
        persona.setNumeroDocumento(dto.getNumeroDoc());

        Persona personaGuardada = repository.save(persona);

        // 2. Mapear y guardar Usuario vinculado
        Usuario usuario = new Usuario();
        usuario.setPersona(personaGuardada);
        usuario.setUsername(dto.getUsername());
        //usuario.setPassword(dto.getPassword());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setActivo(true);

        // 3. Asignar Roles (puedes poner uno por defecto si el DTO no trae)
        Set<Rol> roles;
        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            Rol rolDefault = rolRepo.findByNombre("RESIDENTE")
                    .orElseThrow(() -> new RuntimeException("Rol RESIDENTE no encontrado en BD"));
            roles = Set.of(rolDefault);
        } else {
            roles = dto.getRoles().stream()
                    .map(nombre -> rolRepo.findByNombre(nombre)
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre)))
                    .collect(Collectors.toSet());
        }

        usuario.setRoles(roles);
        usuarioRepo.save(usuario);
    }

    @Override
    @Transactional
    public void actualizarPersonaUser(Long usuarioId, PersonaUserDTO dto) {

        // 1. Obtener usuario y persona existentes
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Persona persona = usuario.getPersona();

        // 2. Actualizar datos de Persona
        persona.setNombre(dto.getNombre());
        persona.setApellidos(dto.getApellidos());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNac(dto.getFechaNac());

        TipoDocumento td = tipoDocumentoRepo.findById(dto.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no válido"));

        persona.setTipoDocumento(td);
        persona.setNumeroDocumento(dto.getNumeroDoc());

        repository.save(persona);

        // 3. Actualizar datos de Usuario
        usuario.setUsername(dto.getUsername());

        // Solo si envías una nueva contraseña la cambia (y la hashea)
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // 4. Actualizar roles si el DTO trae roles
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Rol> roles = dto.getRoles().stream()
                    .map(nombre -> rolRepo.findByNombre(nombre)
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre)))
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }

        usuarioRepo.save(usuario);
    }

    @Override
    @Transactional
    public Usuario findOrCreateByGoogle(String email, String nombre, String apellidos) {
            // Si ya existe un usuario con ese email, retórnalo directamente
            return repository.findByEmail(email).map(Persona::getUsuario).orElseGet(() -> {

                // 1. Crear Persona con lo que Google da
                Persona persona = new Persona();
                persona.setNombre(nombre != null ? nombre : "Sin nombre");
                persona.setApellidos(apellidos != null ? apellidos : "Sin apellidos");
                persona.setEmail(email);
                persona.setEstado(true);
                persona.setFechaRegis(LocalDate.now());
                // Campos que Google no da → null
                persona.setTelefono(null);
                persona.setFechaNac(null);
                persona.setNumeroDocumento(null);
                persona.setTipoDocumento(null);

                Persona personaGuardada = repository.save(persona);

                // 2. Crear Usuario
                Usuario usuario = new Usuario();
                usuario.setPersona(personaGuardada);
                usuario.setUsername(email);
                usuario.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                usuario.setActivo(true);

                // 3. Rol RESIDENTE por defecto
                Rol rolDefault = rolRepo.findByNombre("RESIDENTE")
                        .orElseThrow(() -> new RuntimeException("Rol RESIDENTE no encontrado"));
                usuario.setRoles(Set.of(rolDefault));

                return usuarioRepo.save(usuario);
            });
    }


}
