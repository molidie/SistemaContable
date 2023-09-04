package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;

import java.util.List;

public interface IAsientoService  {
    public Asiento create(Asiento asiento);
    public List<Asiento> getAll();
    public void delete(Long id);
}
