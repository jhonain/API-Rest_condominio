package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.ReservaDTO;
import com.vasquez.Backend_PAF_condominio.dto.ReservaResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.AreaComun;
import com.vasquez.Backend_PAF_condominio.entity.HorarioDisponible;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import com.vasquez.Backend_PAF_condominio.entity.Reserva;
import com.vasquez.Backend_PAF_condominio.enums.EstadoAreaComun;
import com.vasquez.Backend_PAF_condominio.enums.EstadoReserva;
import com.vasquez.Backend_PAF_condominio.mapper.ReservaMapper;
import com.vasquez.Backend_PAF_condominio.repository.AreaComunRepository;
import com.vasquez.Backend_PAF_condominio.repository.HorarioDisponibleRepository;
import com.vasquez.Backend_PAF_condominio.repository.PersonaRepository;
import com.vasquez.Backend_PAF_condominio.repository.ReservaRepository;
import com.vasquez.Backend_PAF_condominio.service.AreaComunService;
import com.vasquez.Backend_PAF_condominio.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private AreaComunRepository areaComunRepository;

    @Autowired
    private HorarioDisponibleRepository horarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private AreaComunService areaComunService;


    @Override
    public ReservaResponseDTO crearReserva(ReservaDTO dto) {

        Persona persona = personaRepository.findById(dto.getPersonaId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        AreaComun area = areaComunRepository.findById(dto.getAreaComunId())
                .orElseThrow(() -> new RuntimeException("Área común no encontrada"));

        HorarioDisponible horario = horarioRepository.findById(dto.getHorarioId())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        // ✅ Validar que el área no esté en MANTENIMIENTO
        if (area.getEstado() == EstadoAreaComun.MANTENIMIENTO) {
            throw new RuntimeException("El área está en mantenimiento");
        }

        // ✅ Validar que el área no esté LLENA (Opción B)
        long reservasActivas = reservaRepository
                .countByAreaComunIdAndHorarioIdAndFechaAndEstado(
                        area.getId(), horario.getId(), dto.getFecha(), EstadoReserva.CONFIRMADA
                );

        if (reservasActivas >= area.getCapacidad()) {
            throw new RuntimeException("Capacidad máxima alcanzada para este horario");
        }

        // ✅ Validar que no sea una fecha pasada
        if (dto.getFecha().isBefore(LocalDate.now())) {
            throw new RuntimeException("No puedes reservar en una fecha pasada");
        }

        Reserva reserva = new Reserva();
        reserva.setFecha(dto.getFecha());
        reserva.setPersona(persona);
        reserva.setAreaComun(area);
        reserva.setHorario(horario);
        reserva.setEstado(EstadoReserva.CONFIRMADA);

        Reserva saved = reservaRepository.save(reserva);

        // ✅ Verificar si el área se llenó
        areaComunService.verificarYActualizarEstado(area);

        return reservaMapper.ToDTO(saved);
    }

    @Override
    public ReservaResponseDTO obtenerPorId(Long id) {
        return reservaMapper.ToDTO(reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada")));
    }

    @Override
    public List<ReservaResponseDTO> listarPorPersona(Long personaId) {
        return reservaRepository.findByPersonaId(personaId)
                .stream().map(reservaMapper::ToDTO).toList();
    }

    @Override
    public List<ReservaResponseDTO> listarPorAreaComun(Long areaComunId) {
        return reservaRepository.findByAreaComunId(areaComunId)
                .stream().map(reservaMapper::ToDTO).toList();
    }

    @Override
    public ReservaResponseDTO cancelarReserva(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new RuntimeException("La reserva ya está cancelada");
        }

        if (reserva.getEstado() == EstadoReserva.FINALIZADA) {
            throw new RuntimeException("No puedes cancelar una reserva finalizada");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);

        // ✅ Liberar cupo en el área
        areaComunService.verificarYActualizarEstado(reserva.getAreaComun());

        return reservaMapper.ToDTO(reserva);

    }

    @Override
    @Scheduled(cron = "0 0/30 * * * ?") // Cada 1 minuto
    @Transactional
    public void finalizarReservasVencidas() {

        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        reservaRepository.findByEstado(EstadoReserva.CONFIRMADA).forEach(reserva -> {
            LocalTime horaFin = reserva.getHorario().getHoraFin();
            LocalDate fechaReserva = reserva.getFecha();

            boolean yaTermino;

            // Horario cruza medianoche (ej: 20:00 - 01:00)
            if (horaFin.isBefore(reserva.getHorario().getHoraInicio())) {
                LocalDate fechaFin = fechaReserva.plusDays(1);
                yaTermino = fechaFin.isBefore(hoy) ||
                        (fechaFin.isEqual(hoy) && horaFin.isBefore(ahora));
            } else {
                yaTermino = fechaReserva.isBefore(hoy) ||
                        (fechaReserva.isEqual(hoy) && horaFin.isBefore(ahora));
            }

            if (yaTermino) {
                reserva.setEstado(EstadoReserva.FINALIZADA);
                reservaRepository.save(reserva);
                areaComunService.verificarYActualizarEstado(reserva.getAreaComun());
            }
        });
    }
}
