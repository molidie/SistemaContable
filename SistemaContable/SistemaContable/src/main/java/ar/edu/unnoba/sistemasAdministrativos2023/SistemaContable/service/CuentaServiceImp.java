package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.AsientoRepository;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.CuentaRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            if(c.getHijos().size()== 0 && !c.isSaldo()){

                cuentasHijas.add(c);
            }
        }
        return cuentasHijas;
    }
    public List<Cuenta> cuentasActivas(){
        List<Cuenta> cuentas = getAll();
        List<Cuenta> cuentasA = new ArrayList<>();

        for(Cuenta c : cuentas){
            if(c.getTipo().name() == "ACTIVO"){

                cuentasA.add(c);
            }
        }
        return cuentasA;
    }
    public List<Cuenta> cuentasPasivas(){
        List<Cuenta> cuentas = getAll();
        List<Cuenta> cuentasPasivas = new ArrayList<>();

        for(Cuenta c : cuentas){
            if(c.getTipo().name() == "PASIVO"){

                cuentasPasivas.add(c);
            }
        }
        return cuentasPasivas;
    }

    public List<Cuenta> cuentasPN(){
        List<Cuenta> cuentas = getAll();
        List<Cuenta> cuentasPN = new ArrayList<>();

        for(Cuenta c : cuentas){
            if(c.getTipo().name() == "PATRIMONIO_NETO"){

                cuentasPN.add(c);
            }
        }
        return cuentasPN;
    }
    public List<Cuenta> cuentasRN(){
        List<Cuenta> cuentas = getAll();
        List<Cuenta> cuentasRN = new ArrayList<>();

        for(Cuenta c : cuentas){
            if(c.getTipo().name() == "RESULTADO_NEGATIVO"){

                cuentasRN.add(c);
            }
        }
        return cuentasRN;
    }
    public List<Cuenta> cuentasRP(){
        List<Cuenta> cuentas = getAll();
        List<Cuenta> cuentasRP = new ArrayList<>();

        for(Cuenta c : cuentas){
            if(c.getTipo().name() == "RESULTADO_POSITIVO"){

                cuentasRP.add(c);
            }
        }
        return cuentasRP;
    }
    @Override
    public List<Cuenta> obtenerCuentasJerarquicas(int codigoBase) {//es para mostrar el plan de cuenta vemos si lo dejamos
        List<Cuenta> cuentasRaiz = cuentaRepository.obtenerCuentasPorCodigo(codigoBase); // Supongamos que tienes un método en tu repositorio para obtener cuentas con un código base.

        for (Cuenta cuenta : cuentasRaiz) {
            cuenta.setHijos(recursivamenteObtenerHijos(cuenta.getId()));
        }

        return cuentasRaiz;
    }
    @Override
    public List<Cuenta> recursivamenteObtenerHijos(Long cuentaId) { //es para mostrar el plan de cuenta vemos si lo dejamos
        List<Cuenta> hijos = cuentaRepository.obtenerCuentasPorCuentaPadre(cuentaId); // Supongamos que tienes un método en tu repositorio para obtener cuentas hijas de una cuenta padre.

        for (Cuenta hijo : hijos) {
            hijo.setHijos(recursivamenteObtenerHijos(hijo.getId()));
        }

        return hijos;
    }


}
