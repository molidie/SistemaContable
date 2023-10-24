package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.AsientoRepository;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaServiceImp implements CuentaService, UserDetailsService {
    @Autowired
    private CuentaRepository cuentaRepository;
    private final AsientoRepository asientoRepository;

    public CuentaServiceImp(CuentaRepository cuentaRepository,
                            AsientoRepository asientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.asientoRepository = asientoRepository;
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
         padre.getHijos().add(cuenta); //se asigna omo hijos a su padre

         return cuenta1;
    }

    @Override
    public List<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        asientoRepository.deleteById(id);
    }

    @Override
    public Cuenta obtenerCuentaPorId(Long cuentaId) {
        return cuentaRepository.findById(cuentaId).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public List<Cuenta> cuentasHijas(){
        List<Cuenta> cuentas = getAll();
        List<Cuenta> cuentasHijas = new ArrayList<>();

        for(Cuenta c : cuentas){
            if(c.getHijos().size()== 0){
                cuentasHijas.add(c);
            }
        }
        return cuentasHijas;
    }
    @Override
    public List<Cuenta> cuentasHijasNoPadre(){
        List<Cuenta> cuentas = getAll();
        List<Cuenta> cuentasHijas = new ArrayList<>();

        for(Cuenta c : cuentas){
            if(c.isSaldo()){
                cuentasHijas.add(c);
            }
        }
        return cuentasHijas;
    }

    @Override
    public float CalcularSaldo(Cuenta cuenta){
        float saldo =0;

        if(cuenta.getTipo().name() == "ACTIVO" || cuenta.getTipo().name() == "RESULTADO_NEGATIVO"  ){
            for(Asiento a : cuenta.getAsientos()){
                saldo += a.getDebe();
                saldo -= a.getHaber();
            }
        }
        if(cuenta.getTipo().name() == "PASIVO" || cuenta.getTipo().name() == "RESULTADO_POSITIVO"  ){
            for(Asiento a : cuenta.getAsientos()){
                saldo -= a.getDebe();
                saldo += a.getHaber();
            }
        }
        return saldo;
    }




}
