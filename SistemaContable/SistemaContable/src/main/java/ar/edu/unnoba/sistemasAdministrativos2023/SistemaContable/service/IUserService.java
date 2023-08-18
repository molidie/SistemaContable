package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Admin;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;

import java.util.List;

public interface IUserService {
    public Usuarios create(Usuarios user);
    public List<Usuarios> getAll();
    public void delete(Long id);
}
