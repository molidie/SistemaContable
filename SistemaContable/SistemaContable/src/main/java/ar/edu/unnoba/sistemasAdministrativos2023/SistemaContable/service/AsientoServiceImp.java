package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.AsientoRepository;
import net.bytebuddy.asm.Advice;
import org.checkerframework.checker.formatter.qual.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AsientoServiceImp implements IAsientoService, UserDetailsService {
    @Autowired
    private AsientoRepository asientoRepository;


    @Override
    public Asiento create(Asiento asiento) {
        asiento = asientoRepository.save(asiento);
        return asiento;
    }

    @Override
    public List<Asiento> getAll() {
        return asientoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        asientoRepository.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public List<Asiento> asientoList(LocalDate fechaInicion, LocalDate fechaFin) {
        return null;
    }

    @Override
    public List<Asiento> asientos(LocalDate fechaInicion, LocalDate fechaFin) {
        return null;
    }


    @Override
    public List<Asiento> obtenerLibroDiarioEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return asientoRepository.findByFechaBetweenOrderByFechaAsc(fechaDesde, fechaHasta);
    }

    @Override
    public Asiento editarAsiento(Asiento a) {
        return asientoRepository.save(a);
    }
}
