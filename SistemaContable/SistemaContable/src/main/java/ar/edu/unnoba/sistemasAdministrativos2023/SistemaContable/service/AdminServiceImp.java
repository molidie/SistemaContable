package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Admin;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImp implements IAdminService, UserDetailsService{
 @Autowired
    private AdminRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) repository.findByUsername(email);
    }

    @Override
    public Admin create(Admin admin) {
        if (repository.findByUsername(admin.getUsername()) == null) {
            admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
            admin = repository.save(admin);
        }
        return admin;
    }

    @Override
    public List<Admin> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Admin buscarAdmin(String email){
        return repository.findByUsername(email);
    }

}

