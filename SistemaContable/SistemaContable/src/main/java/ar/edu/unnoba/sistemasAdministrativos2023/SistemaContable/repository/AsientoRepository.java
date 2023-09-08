package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {
public Asiento findByFecha(LocalDate fe);
public List<Asiento> findByFechaBetweenOrderByFechaAsc(LocalDate fechaInicio, LocalDate fechaFin);


}
