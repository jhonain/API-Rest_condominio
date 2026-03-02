package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.entity.Condomino;
import com.vasquez.Backend_PAF_condominio.entity.Cuota;
import com.vasquez.Backend_PAF_condominio.enums.EstadoCuota;
import com.vasquez.Backend_PAF_condominio.repository.AvisoRepository;
import com.vasquez.Backend_PAF_condominio.repository.CondominoRepository;
import com.vasquez.Backend_PAF_condominio.repository.CuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvisoCleanupService {

    @Autowired
    private AvisoRepository repository;

    @Autowired
    private CondominoRepository condominoRepository;

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private CuotaService cuotaService;

    // ✅ Sin @Override
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void limpiarAvisosAntiguos() {
        int eliminados = repository.deleteByFechaCreacionBefore(
                LocalDateTime.now().minusDays(7)
        );
        System.out.println("Eliminados: " + eliminados + " avisos");
    }

    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void generarCuotaMensual() {
        LocalDate hoy = LocalDate.now();

        // Obtener todos los condominos activos sin fecha fin
        List<Condomino> activos = condominoRepository
                .findByEstadoTrueAndFechaFinIsNull();

        for (Condomino condomino : activos) {
            LocalDate fechaVencimiento = hoy.plusMonths(1)
                    .withDayOfMonth(condomino.getFechaInicio().getDayOfMonth());


            // Verificar que no exista ya esa cuota
            boolean yaExiste = cuotaRepository
                    .existsByCondominoIdAndFechaVencimiento(
                            condomino.getId(), fechaVencimiento);

            if (!yaExiste) {
                Cuota cuota = new Cuota();
                cuota.setCondomino(condomino);
                cuota.setMonto(condomino.getUnidad().getPrecioMensual());
                cuota.setFechaVencimiento(fechaVencimiento);
                cuota.setEstado(EstadoCuota.PENDIENTE);
                cuota.setFechaRegistro(hoy);

                // Número de cuota = cuotas anteriores + 1
                int total = cuotaRepository.countByCondominoId(condomino.getId());
                cuota.setNumeroCuota(total + 1);

                cuotaRepository.save(cuota);
            }
        }
    }


    @Scheduled(cron = "0 0 0 * * *") // todos los días a medianoche
    @Transactional
    public void marcarVencidas() {
        cuotaService.marcarVencidas();
    }

}
