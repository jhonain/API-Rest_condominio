package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.CuotaResponseDTO;
import com.vasquez.Backend_PAF_condominio.dto.PagoRequest;
import com.vasquez.Backend_PAF_condominio.entity.Condomino;
import com.vasquez.Backend_PAF_condominio.entity.Cuota;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import com.vasquez.Backend_PAF_condominio.enums.EstadoCuota;
import com.vasquez.Backend_PAF_condominio.enums.MetodoPago;
import com.vasquez.Backend_PAF_condominio.repository.CondominoRepository;
import com.vasquez.Backend_PAF_condominio.repository.CuotaRepository;
import com.vasquez.Backend_PAF_condominio.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuotaServiceImpl implements CuotaService {


    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private CondominoRepository condominoRepository;

    @Override
    public List<Cuota> listarTodas() {
        return cuotaRepository.findAll();
    }

    @Override
    public List<Cuota> listarPorCondomino(Long condominoId) {
        return cuotaRepository.findByCondominoId(condominoId);
    }

    @Override
    public Cuota buscarPorId(Long id) {
        return cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada con id: " + id));
    }

    @Override
    public Cuota registrarPago(Long id, PagoRequest request) {
        Cuota cuota = buscarPorId(id);

        if (cuota.getEstado() == EstadoCuota.PAGADA) {
            throw new RuntimeException("La cuota ya fue pagada.");
        }

        cuota.setEstado(EstadoCuota.PAGADA);
        cuota.setFechaPago(LocalDate.now());
        cuota.setMetodoPago(request.getMetodoPago());
        cuota.setObservacion(request.getObservacion());

        return cuotaRepository.save(cuota);
    }


    @Override
    public void marcarVencidas() {
        List<Cuota> pendientes = cuotaRepository.findByEstadoAndFechaVencimientoBefore(
                EstadoCuota.PENDIENTE, LocalDate.now());

        pendientes.forEach(cuota -> cuota.setEstado(EstadoCuota.VENCIDA));

        cuotaRepository.saveAll(pendientes);

    }

    @Override
    public List<Cuota> listarMisCuotas(Long personaId) {
        List<Condomino> condominos = condominoRepository.findByPersonaId(personaId);

        Optional<Condomino> activo = condominos.stream()
                .filter(c -> Boolean.TRUE.equals(c.getEstado()))  // solo estado activo
                .findFirst();

        if (activo.isEmpty()) return List.of();

        return cuotaRepository.findByCondominoId(activo.get().getId());
    }

    @Override
    public List<CuotaResponseDTO> listarTodasConResidente() {
        return cuotaRepository.findAll().stream().map(cuota -> {
            CuotaResponseDTO dto = new CuotaResponseDTO();
            dto.setId(cuota.getId());
            dto.setNumeroCuota(cuota.getNumeroCuota());
            dto.setMonto(cuota.getMonto());
            dto.setFechaVencimiento(cuota.getFechaVencimiento());
            dto.setFechaPago(cuota.getFechaPago());
            dto.setFechaRegistro(cuota.getFechaRegistro());
            dto.setEstado(cuota.getEstado());
            dto.setMetodoPago(cuota.getMetodoPago());
            dto.setObservacion(cuota.getObservacion());

            // Datos del residente desde condomino → persona
            Persona persona = cuota.getCondomino().getPersona();
            dto.setNombreResidente(persona.getNombre());
            dto.setApellidosResidente(persona.getApellidos());
            dto.setUnidadCodigo(cuota.getCondomino().getUnidad().getCodigo());

            return dto;
        }).collect(Collectors.toList());
    }

}
