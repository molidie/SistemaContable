package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;

import java.time.LocalDate;
import java.util.List;

public interface IAsientoService  {
    public Asiento create(Asiento asiento);
    public List<Asiento> getAll();
    public void delete(Long id);
    public List<Asiento>asientoList(LocalDate fechaInicion , LocalDate fechaFin);
    public List<Asiento>asientos(LocalDate fechaInicion , LocalDate fechaFin);
    public List<Asiento> obtenerLibroDiarioEntreFechas(LocalDate fechaInicio, LocalDate fechaFin);
}
