package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CuentaService {

    public Cuenta create(Cuenta user);
    public List<Cuenta> getAll();
    public void delete(Long id);
    public Cuenta obtenerCuentaPorId(Long cuentaId);
    public List<Cuenta> cuentasHijas();

    public List<Cuenta> cuentasHijasNoPadre();

    float CalcularSaldo(Cuenta cuenta);


}
