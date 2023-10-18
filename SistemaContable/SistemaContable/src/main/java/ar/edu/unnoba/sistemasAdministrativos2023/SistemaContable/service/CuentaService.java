package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;

import java.util.List;

public interface CuentaService {

    public Cuenta create(Cuenta user);
    public List<Cuenta> getAll();
    public void delete(Long id);
    public Cuenta obtenerCuentaPorId(Long cuentaId);
    public List<Cuenta> cuentasHijas();

    public List<Cuenta> cuentasHijasNoPadre();
    public List<Cuenta> obtenerCuentasJerarquicas(int codigoBase); //es para mostrar el plan de cuenta vemos si lo dejamos
     List<Cuenta> recursivamenteObtenerHijos(Long cuentaId); //es para mostrar el plan de cuenta vemos si lo dejamos


}
