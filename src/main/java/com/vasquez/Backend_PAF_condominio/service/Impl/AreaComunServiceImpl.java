package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.AreaComunResponseDTO;
import com.vasquez.Backend_PAF_condominio.dto.HorarioResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.AreaComun;
import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import com.vasquez.Backend_PAF_condominio.entity.HorarioDisponible;
import com.vasquez.Backend_PAF_condominio.enums.EstadoAreaComun;
import com.vasquez.Backend_PAF_condominio.enums.EstadoReserva;
import com.vasquez.Backend_PAF_condominio.mapper.AreaComunMapper;
import com.vasquez.Backend_PAF_condominio.repository.AreaComunRepository;
import com.vasquez.Backend_PAF_condominio.repository.CondominioRepository;
import com.vasquez.Backend_PAF_condominio.repository.ReservaRepository;
import com.vasquez.Backend_PAF_condominio.service.AreaComunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AreaComunServiceImpl implements AreaComunService {

    @Autowired
    private AreaComunRepository areaComunRepository;

    @Autowired
    private CondominioRepository condominioRepository;

    @Autowired
    private AreaComunMapper areaComunMapper;

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    @Transactional
    public AreaComunResponseDTO  crearAreaComun(AreaComunResponseDTO dto, Long condominioId) {
        Condominio condominio = condominioRepository.findById(condominioId)
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado"));

        AreaComun area = new AreaComun();
        area.setNombre(dto.getNombre());
        area.setDescripcion(dto.getDescripcion());
        area.setCapacidad(dto.getCapacidad());
        area.setCondominio(condominio);
        area.setEstado(EstadoAreaComun.DISPONIBLE); // Estado inicial siempre DISPONIBLE
        area.setImagenUrl(dto.getImagenUrl());

        if (dto.getHorarios() != null) {
            List<HorarioDisponible> horarios = dto.getHorarios().stream()
                    .map(h -> {
                        HorarioDisponible horario = new HorarioDisponible();
                        horario.setHoraInicio(h.getHoraInicio());
                        horario.setHoraFin(h.getHoraFin());
                        horario.setAreaComun(area);
                        return horario;
                    }).toList();
            area.setHorarios(horarios);
        }

        AreaComun saved = areaComunRepository.save(area);
        return areaComunMapper.mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaComunResponseDTO> listarPorCondominio(Long condominioId) {
        return areaComunRepository.findByCondominioId(condominioId)
                .stream().map(areaComunMapper::mapToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaComunResponseDTO> listarDisponiblesPorCondominio(Long condominioId) {
        return areaComunRepository.findByCondominioIdAndEstado(condominioId, EstadoAreaComun.DISPONIBLE)
                .stream().map(areaComunMapper::mapToDTO).toList();
    }

    @Override
    public AreaComun  obtenerPorId(Long id) {
        return areaComunRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área común no encontrada"));
    }

    @Override
    public AreaComunResponseDTO actualizarAreaComun(Long id, AreaComunResponseDTO dto) {
        AreaComun area = obtenerPorId(id);
        area.setNombre(dto.getNombre());
        area.setDescripcion(dto.getDescripcion());
        area.setCapacidad(dto.getCapacidad());

        if (dto.getHorarios() != null) {
            area.getHorarios().clear();
            dto.getHorarios().forEach(h -> {
                HorarioDisponible horario = new HorarioDisponible();
                horario.setHoraInicio(h.getHoraInicio());
                horario.setHoraFin(h.getHoraFin());
                horario.setAreaComun(area);
                area.getHorarios().add(horario);
            });
        }

        // Al actualizar capacidad, re-verificar estado automáticamente
        //verificarYActualizarEstado(area);

        return areaComunMapper.mapToDTO(areaComunRepository.save(area));
    }

    @Override
    public void eliminarAreaComun(Long id) {
        AreaComun area = obtenerPorId(id);
        areaComunRepository.delete(area);
    }

    @Override
    public List<AreaComunResponseDTO> listarTodo() {
        return areaComunRepository.findAll()
                .stream()
                .map(areaComunMapper::mapToDTO)
                .toList();
    }


    @Override
    public void verificarYActualizarEstado(AreaComun area) {
        // Si el admin lo puso en MANTENIMIENTO, no se toca
        if (area.getEstado() == EstadoAreaComun.MANTENIMIENTO) {
            return;
        }

        long reservasActivas = reservaRepository
                .countByAreaComunIdAndEstado(area.getId(), EstadoReserva.CONFIRMADA);

        if (reservasActivas >= area.getCapacidad()) {
            area.setEstado(EstadoAreaComun.LLENA);
        } else {
            area.setEstado(EstadoAreaComun.DISPONIBLE);
        }

        areaComunRepository.save(area);
    }

    @Override
    public AreaComun cambiarEstadoManual(Long id, EstadoAreaComun estado) {
        AreaComun area = obtenerPorId(id);
        area.setEstado(estado);
        return areaComunRepository.save(area);
    }
}
