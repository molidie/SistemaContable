package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Cuentas")
public class Cuenta implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo")
    private int codigo;

   /** @Column(name = "tipo")
    private String tipo; //activo pasivo patrimonio neto resultado positivo resultado negativo lo podemos hacer una tabla aparte o un dominio
**/

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipo;

    @Column(name = "saldo")
    private boolean saldo;  //recibe saldo es solo las hojas, eje banco y cuenta --> bco provincia bueno solo banco provincia puede recibir saldo, tambien lo podemos pensar como una recursiva

    @Column(name = "saldo_actual")
    private float saldo_actual;


    public Cuenta() {
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Cuenta padre;

    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL)
    private List<Cuenta> hijos;

    @ManyToMany(mappedBy = "cuentas", fetch = FetchType.EAGER) //le cambiamos el tipo de carga para que el post del controller de editar reconozca la lista,si no hinbernate no lo reconoce
    private List<Asiento> asientos;
    public List<Cuenta> getHijos() {
        return hijos;
    }



    public void setHijos(List<Cuenta> hijos) {
        this.hijos = hijos;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public Long getId() {
        return id;
    }

    public Cuenta getPadre() {
        return padre;
    }

    public void setPadre(Cuenta padre) {
        this.padre = padre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    /**
     public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

**/

    public boolean isSaldo() {
        return saldo;
    }

    public void setSaldo(boolean saldo) {
        this.saldo = saldo;
    }

    public float getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(float saldo_actual) {
        this.saldo_actual = saldo_actual;
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
        return ""; //ver porq lo cambie a string para prbar algo para que el codigo del producto sea "A-0000001"
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
