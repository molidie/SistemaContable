package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaService;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAsientoService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    public AsientoController(IAsientoService asientoService, CuentaService cuentaService) {
        this.asientoService = asientoService;
        this.cuentaService = cuentaService;
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
        model.addAttribute("cuentas", cuentaService.getAll());
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
        model.addAttribute("cuentas", cuentaService.getAll());

        return "/admin/asiento/edit";
    }

    @PostMapping("/editarAsiento/{idE}")
    public String actualizarProdcuto (@PathVariable("idE") Long id, @ModelAttribute("asiento") Asiento asiento, Model model){
        model.addAttribute("cuentas", cuentaService.getAll());
        model.addAttribute("asiento",asiento);

        for (Asiento a : listaAsientos) {
            if (a.getCodigo() == id) {
                a.setDescripcion(asiento.getDescripcion());
                a.setCodigo(asiento.getCodigo());
                a.setDebe(asiento.getDebe());
                a.setHaber(asiento.getHaber());
                a.setCuentas(asiento.getCuentas());
                a.setId(id);
                a.setFecha(a.getFecha());

            }
        asientoService.editarAsiento(a);
    }
        return "redirect:/admin/asiento/new";

    }


}
