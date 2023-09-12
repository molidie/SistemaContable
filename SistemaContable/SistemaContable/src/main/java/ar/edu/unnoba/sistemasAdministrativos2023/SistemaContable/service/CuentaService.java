package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;

import java.util.List;

public interface CuentaService {

    public Cuenta create(Cuenta user);
    public List<Cuenta> getAll();
    public void delete(Long id);
    public Cuenta obtenerCuentaPorId(Long cuentaId);

}
