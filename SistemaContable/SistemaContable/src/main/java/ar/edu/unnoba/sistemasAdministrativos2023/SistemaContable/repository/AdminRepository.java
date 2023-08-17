package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    public Admin findByEmail(String Email);
}
