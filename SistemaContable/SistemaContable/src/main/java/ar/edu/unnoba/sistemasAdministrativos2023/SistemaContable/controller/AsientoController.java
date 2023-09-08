package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAsientoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/asiento")
public class AsientoController {
    private IAsientoService asientoService;

    public AsientoController(IAsientoService asientoService) {
        this.asientoService = asientoService;
    }

    @GetMapping("/new")
    public String UserNew(Model model) {
        model.addAttribute("asiento", new Asiento());
        return "admin/asiento/new";
    }

    @PostMapping
    public String create(@ModelAttribute Asiento asiento) {
        asientoService.create(asiento);
        return "redirect:/admins/home";
    }

  /**  @GetMapping("/librodiario")

    }**/


    @GetMapping("/librodiario")
    public String LibroDiario(@RequestParam("desde") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
                                     @RequestParam("hasta") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
                                     Model model) {
        List<Asiento> librodiario = asientoService.obtenerLibroDiarioEntreFechas(fechaDesde, fechaHasta);
        model.addAttribute("libro", librodiario);
        return "/admin/asiento/librodiario";
    }


}
