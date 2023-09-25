package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaService;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAsientoService;
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
        model.addAttribute("cuentas", cuentaService.getAll());
        listaAsientos.clear();
        return "admin/asiento/new";
    }

    @PostMapping
    public String create(@ModelAttribute Asiento asiento, @RequestParam Long cuentaId, Model model) {
        Cuenta cuentaSeleccionada = cuentaService.obtenerCuentaPorId(cuentaId);
        if (asiento.getCuentas() == null) {
            asiento.setCuentas(new ArrayList<>());
        }
        asiento.getCuentas().add(cuentaSeleccionada);
        cuentaSeleccionada.getAsientos().add(asiento);
        asientoService.create(asiento);

        listaAsientos.add(asiento);
        model.addAttribute("cuentas", cuentaService.getAll());
        model.addAttribute("asientos", listaAsientos);

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
    }


}
