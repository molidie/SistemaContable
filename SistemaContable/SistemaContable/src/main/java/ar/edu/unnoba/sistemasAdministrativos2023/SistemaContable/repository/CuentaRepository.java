package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}
