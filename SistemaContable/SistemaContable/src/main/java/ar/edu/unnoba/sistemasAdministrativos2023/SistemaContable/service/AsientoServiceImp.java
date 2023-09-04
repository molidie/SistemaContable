package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
