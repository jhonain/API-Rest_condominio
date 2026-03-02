package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.*;
import com.vasquez.Backend_PAF_condominio.entity.*;
import com.vasquez.Backend_PAF_condominio.enums.EstadoReserva;
import com.vasquez.Backend_PAF_condominio.repository.*;
import com.vasquez.Backend_PAF_condominio.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private CondominoRepository condominoRepository;
    @Autowired
    private AreaComunRepository areaComunRepository;
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponseDTO getDashboardResidente(Long personaId) {

        // 1. Obtener persona
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        Condomino asignacion = condominoRepository
                .findByPersonaIdAndEstado(personaId, true)
                .orElseThrow(() -> new RuntimeException("No tienes una unidad asignada"));

        // 3. Obtener unidad y condominio desde la asignación
        Unidad unidad = asignacion.getUnidad();
        Condominio condominio = unidad.getCondominio();

        // 4. Áreas comunes del condominio
        List<AreaComun> areas = areaComunRepository
                .findByCondominioId(condominio.getId());

        // 5. Reservas CONFIRMADAS del residente
        List<Reserva> reservas = reservaRepository
                .findByPersonaIdAndEstado(personaId, EstadoReserva.CONFIRMADA);

        // 6. Armar respuesta
        DashboardResponseDTO dashboard = new DashboardResponseDTO();

        // Persona
        dashboard.setPersonaId(persona.getId());
        dashboard.setNombreCompleto(persona.getNombre() + " " + persona.getApellidos());
        dashboard.setEmail(persona.getEmail());
        dashboard.setTelefono(persona.getTelefono());


        // Unidad
        UnidadInfoDTO unidadDTO = new UnidadInfoDTO();
        unidadDTO.setId(unidad.getId());
        unidadDTO.setCodigo(unidad.getCodigo());
        unidadDTO.setPiso(unidad.getPiso());
        unidadDTO.setAreaM2(unidad.getAreaM2());
        unidadDTO.setPrecioMensual(unidad.getPrecioMensual());
        unidadDTO.setEstado(unidad.getEstado());
        unidadDTO.setImagenUrl(unidad.getImagenUrl());
        dashboard.setUnidad(unidadDTO);

        // Condominio
        CondominioInfoDTO condominioDTO = new CondominioInfoDTO();
        condominioDTO.setId(condominio.getId());
        condominioDTO.setNombre(condominio.getNombre());
        condominioDTO.setDireccion(condominio.getDireccion());
        condominioDTO.setTotalUnidades(condominio.getTotalUnidades());
        condominioDTO.setImagenUrl(condominio.getImagenUrl());
        dashboard.setCondominio(condominioDTO);

        // Áreas comunes
        dashboard.setAreasComunes(areas.stream().map(area -> {
            AreaComunResponseDTO areaDTO = new AreaComunResponseDTO();
            areaDTO.setId(area.getId());
            areaDTO.setNombre(area.getNombre());
            areaDTO.setDescripcion(area.getDescripcion());
            areaDTO.setCapacidad(area.getCapacidad());
            areaDTO.setEstado(area.getEstado());
            areaDTO.setCondominioId(condominio.getId());
            areaDTO.setCondominioNombre(condominio.getNombre());
            areaDTO.setHorarios(area.getHorarios().stream().map(h -> {
                HorarioResponseDTO hDTO = new HorarioResponseDTO();
                hDTO.setId(h.getId());
                hDTO.setHoraInicio(h.getHoraInicio());
                hDTO.setHoraFin(h.getHoraFin());
                return hDTO;
            }).toList());
            return areaDTO;
        }).toList());

        // Mis reservas
        dashboard.setMisReservas(reservas.stream().map(reserva -> {
            ReservaResponseDTO reservaDTO = new ReservaResponseDTO();
            reservaDTO.setId(reserva.getId());
            reservaDTO.setFecha(reserva.getFecha());
            reservaDTO.setEstado(reserva.getEstado());
            reservaDTO.setFechaRegistro(reserva.getFechaRegistro());
            reservaDTO.setPersonaId(persona.getId());
            reservaDTO.setPersonaNombre(persona.getNombre() + " " + persona.getApellidos());
            reservaDTO.setAreaComunId(reserva.getAreaComun().getId());
            reservaDTO.setAreaComunNombre(reserva.getAreaComun().getNombre());
            reservaDTO.setHorarioId(reserva.getHorario().getId());
            reservaDTO.setHorarioInicio(reserva.getHorario().getHoraInicio());
            reservaDTO.setHorarioFin(reserva.getHorario().getHoraFin());
            return reservaDTO;
        }).toList());

        return dashboard;
    }
}

