package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Service
public class AsientoServiceImp implements IAsientoService, UserDetailsService {
    @Autowired
    private AsientoRepository asientoRepository;


    @Override
    public Asiento create(Asiento asiento) {
        asiento = asientoRepository.save(asiento);
        return asiento;
    }

    @Override
    public List<Asiento> getAll() {
        return asientoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        asientoRepository.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public List<Asiento> asientoList(LocalDate fechaInicion, LocalDate fechaFin) {
        return null;
    }

    @Override
    public List<Asiento> asientos(LocalDate fechaInicion, LocalDate fechaFin) {
        return null;
    }


    @Override
    public List<Asiento> obtenerLibroDiarioEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return asientoRepository.findByFechaBetweenOrderByFechaAsc(fechaDesde, fechaHasta);
    }

    @Override
    public Asiento editarAsiento(Asiento a) {
        return asientoRepository.save(a);
    }

    @PersistenceContext
    private EntityManager entityManager;

    // Otras implementaciones de métodos del servicio...

    @Override
    public int contarAsientosEnElMesActual() {
        LocalDate fechaInicio = LocalDate.now().withDayOfMonth(1); // Primer día del mes actual
        LocalDate fechaFin = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1); // Último día del mes actual

        Query query = entityManager.createQuery(
                "SELECT COUNT(a) FROM Asiento a WHERE a.fecha BETWEEN :fechaInicio AND :fechaFin",
                Long.class
        );
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);

        Long resultado = (Long) query.getSingleResult();

        return resultado.intValue();
    }
}
