package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
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

    public AsientoController(IAsientoService asientoService) {
        this.asientoService = asientoService;
    }

    @Autowired
    public AsientoController(IAsientoService asientoService, CuentaService cuentaService) {
        this.asientoService = asientoService;
        this.cuentaService = cuentaService;
    }

    @GetMapping("/new")
    public String UserNew(Model model) {

        model.addAttribute("asiento", new Asiento());
        model.addAttribute("cuentas", cuentaService.getAll()); // Obtén todas las cuentas disponibles
        return "admin/asiento/nuevoAsiento";
    }

    @PostMapping
    public String create(@ModelAttribute Asiento asiento, Long cuentaId) {
        Cuenta cuentaSeleccionada = cuentaService.obtenerCuentaPorId(cuentaId);
        if (asiento.getCuentas() == null) {
            asiento.setCuentas(new ArrayList<>());
        }
        asiento.getCuentas().add(cuentaSeleccionada);
        cuentaSeleccionada.getAsientos().add(asiento);
        asientoService.create(asiento);

        return "redirect:/admin/home";
    }

    /**
     * @GetMapping("/librodiario")
     * 
     * }
     **/

    @GetMapping("/librodiario")
    public String LibroDiario(@RequestParam("desde") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
            @RequestParam("hasta") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
            Model model) {
        List<Asiento> librodiario = asientoService.obtenerLibroDiarioEntreFechas(fechaDesde, fechaHasta);
        model.addAttribute("libro", librodiario);
        return "/admin/asiento/librodiario";
    }

}
