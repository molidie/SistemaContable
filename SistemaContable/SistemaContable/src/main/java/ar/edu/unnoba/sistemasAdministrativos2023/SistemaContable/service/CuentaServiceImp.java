package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.CuentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImp implements CuentaService, UserDetailsService {
    @Autowired
    private CuentaRepository cuentaRepository;

    public CuentaServiceImp(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Cuenta create(Cuenta cuenta) {
        // Validar si la cuenta padre existe
        if (cuenta.getPadre() != null && !cuentaRepository.existsById(cuenta.getPadre().getId())) {
            throw new UsernameNotFoundException("La cuenta padre especificada no existe.");
        }

        // Asignar la cuenta padre si es proporcionada
        if (cuenta.getPadre() != null) {
            cuenta.setPadre(cuentaRepository.getOne(cuenta.getPadre().getId()));
        }

        // Guardar la cuenta en la base de datos
        Cuenta cuenta1 = cuentaRepository.save(cuenta);
        Cuenta padre = cuenta1.getPadre();
        padre.getHijos().add(cuenta); // se asigna omo hijos a su padre

        return cuenta1;
    }

    @Override
    public List<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Cuenta obtenerCuentaPorId(Long cuentaId) {
        return cuentaRepository.findById(cuentaId).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
