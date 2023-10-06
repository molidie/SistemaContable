package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Asientos")
public class Asiento implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private int codigo ;

    @Column(name = "debe")
    private float debe;


    @Column(name = "haber")
    private float haber;


    @Column(name = "saldo")
    private float saldo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha")
    private LocalDate fecha ;

    @Column(name = "descripcion")
    private  String descripcion;



    @ManyToMany
    @JoinTable(
            name = "Asiento_Cuenta",
            joinColumns = @JoinColumn(name = "asiento_id"),
            inverseJoinColumns = @JoinColumn(name = "cuenta_id")
    )
    private List<Cuenta> cuentas;

    public Asiento() {
        this.cuentas = new ArrayList<>();

    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public float getDebe() {
        return debe;
    }

    public void setDebe(float debe) {
        this.debe = debe;
    }

    public float getHaber() {
        return haber;
    }

    public void setHaber(float haber) {
        this.haber = haber;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
