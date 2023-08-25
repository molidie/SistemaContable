package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UseerRepository extends JpaRepository<Usuarios,Long>{
    public Usuarios findByUsername(String Email);
}
