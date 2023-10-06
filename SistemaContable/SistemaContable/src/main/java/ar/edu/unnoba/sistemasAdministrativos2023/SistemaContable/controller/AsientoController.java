package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.CuentaRepository;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaService;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAsientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/asiento")
public class AsientoController {
    private IAsientoService asientoService;
    private CuentaService cuentaService;
    private int contador= 0;
    private float debe ;
    private float haber ;
    private float diferencia =debe-haber;


    public AsientoController(IAsientoService asientoService) {
        this.asientoService = asientoService;
    }
    List<Asiento> listaAsientos = new ArrayList<>();
    private  CuentaRepository cuentaRepository;

    @Autowired
    public AsientoController(IAsientoService asientoService, CuentaService cuentaService,
                             CuentaRepository cuentaRepository) {
        this.asientoService = asientoService;
        this.cuentaService = cuentaService;
        this.cuentaRepository = cuentaRepository;
    }

    @GetMapping("/new")
    public String UserNew(Model model) {

        model.addAttribute("asiento", new Asiento());
        model.addAttribute("cuentas", cuentaService.cuentasHijas());
        //listaAsientos.clear();
        model.addAttribute("asientos", listaAsientos);
        model.addAttribute("diferencia", diferencia);
        return "admin/asiento/new";
    }

    @PostMapping
    public String create(@ModelAttribute Asiento asiento, @RequestParam Long cuentaId, Model model) {
        float debe1 =0;
        float haber1 =0;

        Cuenta cuentaSeleccionada = cuentaService.obtenerCuentaPorId(cuentaId);
        if (asiento.getCuentas() == null) {
            asiento.setCuentas(new ArrayList<>());
        }
        asiento.getCuentas().add(cuentaSeleccionada);
        cuentaSeleccionada.getAsientos().add(asiento);



        listaAsientos.add(asiento);
        model.addAttribute("cuentas", cuentaService.cuentasHijas());
        model.addAttribute("asientos", listaAsientos);

        asiento.setCodigo(contador);
        contador+=1;

        for(Asiento a : listaAsientos){
            debe1+= a.getDebe();
        }
        for(Asiento a : listaAsientos){
            haber1+= a.getHaber();
        }
        diferencia=debe1-haber1;
        model.addAttribute("diferencia", diferencia);

        return "admin/asiento/new";
    }




    @GetMapping("/librodiario")
    public String LibroDiario(@RequestParam("desde") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
                                     @RequestParam("hasta") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
                                     Model model) {
        List<Asiento> librodiario = asientoService.obtenerLibroDiarioEntreFechas(fechaDesde, fechaHasta);
        model.addAttribute("libro", librodiario);
        return "/admin/asiento/librodiario";
    }

/**
    @GetMapping("/cancelar")
    public String cancelar(Model model) {

        for(Asiento a : listaAsientos){
            for (Cuenta cuenta : cuentaService.getAll()) {
                cuenta.getAsientos().remove(a.getId());
            }
            asientoService.delete(a.getId());
        }
        listaAsientos.clear(); // Limpia la lista en memoria.
        return "redirect:/admin/asiento/new";
    }funcion que deje por las dudas**/

    @GetMapping("/agregar")
    public String agregar(Model model) {
        if(listaAsientos.size() <= 1){
            return "redirect:/admin/asiento/new";
        }
        for (Asiento asiento: listaAsientos){

            asientoService.create(asiento);
        }
        listaAsientos.clear();
        return "redirect:/admin/home";
    }

    @GetMapping("/cancelar")
    public String cancelar(Model model) {
        listaAsientos.clear();
        return "redirect:/admin/home";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model, Asiento asiento) {
        List<Asiento> asientosParaEliminar = new ArrayList<>();

        for (Asiento a : listaAsientos) {
            if (a.getCodigo() == id) {
                diferencia-=a.getDebe();
                diferencia+=a.getHaber();
                asientosParaEliminar.add(a);
            }
        }
        for(Asiento a : asientosParaEliminar) {
            listaAsientos.remove(a);
        }
        model.addAttribute("asientos", listaAsientos);
        model.addAttribute("diferencia", diferencia);
        return "redirect:/admin/asiento/new";
    }

    @GetMapping ("/editar/{idE}")
    public String editarProducto(@PathVariable("idE") Long id, Model  model,Asiento asiento){
        for (Asiento a : listaAsientos) {
            if (a.getCodigo() == id) {
                asiento=a;
            }
        }
        model.addAttribute("asiento",asiento);
        model.addAttribute("cuentas", cuentaService.cuentasHijas());

        return "/admin/asiento/edit";
    }



    @PostMapping("/editarAsiento/{idE}")
    public String actualizarProdcuto(@PathVariable("idE") Long id, @ModelAttribute Asiento asiento, Model model) {
        float debe2 =0;
        float haber2 =0;
        model.addAttribute("cuentas", cuentaService.cuentasHijas());
        model.addAttribute("asiento", asiento);

        for (Asiento a : listaAsientos) {
            if (a.getCodigo() == id) {
                a.setDescripcion(asiento.getDescripcion());
                a.setDebe(asiento.getDebe());
                a.setHaber(asiento.getHaber());
                a.setFecha(asiento.getFecha());
                for(Cuenta cuenta: a.getCuentas()){
                    cuenta.getAsientos().remove(a);
                }
                a.getCuentas().clear();
                a.getCuentas().addAll(asiento.getCuentas());
                for (Cuenta c : a.getCuentas()) {
                    c.getAsientos().add(a);
                }

                //asientoService.editarAsiento(a); pasa que si no lo guarda en la bd vio
                for(Asiento asiento1: listaAsientos){
                    debe2+=asiento1.getDebe();
                }
                for(Asiento asiento1: listaAsientos){
                    haber2+=asiento1.getHaber();
                }
                diferencia=debe2-haber2;
            }
        }

        return "redirect:/admin/asiento/new";
    }

}
