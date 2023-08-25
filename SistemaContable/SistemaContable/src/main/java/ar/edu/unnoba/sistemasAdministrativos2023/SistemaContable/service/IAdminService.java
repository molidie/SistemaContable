package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Admin;

import java.util.List;

public interface IAdminService {
    public Admin create(Admin admin);
    public List<Admin> getAll();
    public void delete(Long id);

    public Admin buscarAdmin(String email);
}
