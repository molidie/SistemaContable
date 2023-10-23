package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.CuentaRepository;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaService;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAsientoService;

import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/asiento")
public class AsientoController {
    private IAsientoService asientoService;
    @Autowired
    private CuentaService cuentaService;
    private int contador = 0;
    private float debe;
    private float haber;
    private float diferencia = debe - haber;


    public AsientoController(IAsientoService asientoService) {
        this.asientoService = asientoService;
    }

    List<Asiento> listaAsientos = new ArrayList<>();
    List<Cuenta> listacuentas = new ArrayList<>();

    private CuentaRepository cuentaRepository;



    @Autowired
    public AsientoController(IAsientoService asientoService, CuentaService cuentaService,
                             CuentaRepository cuentaRepository) {
        this.asientoService = asientoService;
        this.cuentaService = cuentaService;
        this.cuentaRepository = cuentaRepository;
    }


    @GetMapping("/new")
    public String UserNew(Model model, @CookieValue(value = "selectedDate", required = false) String selectedDate, HttpServletResponse response) {
        if (selectedDate != null) {
            model.addAttribute("selectedDate", selectedDate);
        }

        // Filtra las cuentas disponibles eliminando las cuentas seleccionadas
        List<Cuenta> cuentasDisponibles = cuentaService.cuentasHijasNoPadre();
        for (Asiento a : listaAsientos) {
            for (Cuenta cuenta : a.getCuentas()) {
                cuentasDisponibles.removeIf(c -> c.getId().equals(cuenta.getId()));
            }
        }

        model.addAttribute("asiento", new Asiento());
        model.addAttribute("cuentas", cuentasDisponibles);
        model.addAttribute("asientos", listaAsientos);
        model.addAttribute("diferencia", diferencia);

        return "admin/asiento/new";
    }

    @PostMapping
    public String create(@ModelAttribute Asiento asiento, @RequestParam Long cuentaId, Model model, HttpServletResponse response) {
        float debe1 = 0;
        float haber1 = 0;

        Cuenta cuentaSeleccionada = cuentaService.obtenerCuentaPorId(cuentaId);
        if (asiento.getCuentas() == null) {
            asiento.setCuentas(new ArrayList<>());
        }
        asiento.getCuentas().add(cuentaSeleccionada);
        cuentaSeleccionada.getAsientos().add(asiento);

        listaAsientos.add(asiento);
        Asiento fecha = listaAsientos.get(0); // hace que la fecha sea siempre la misma

        model.addAttribute("cuentas", cuentaService.cuentasHijasNoPadre());
        model.addAttribute("asientos", listaAsientos);

        asiento.setCodigo(contador);
        contador += 1;

        for (Asiento a : listaAsientos) {
            debe1 += a.getDebe();
        }
        for (Asiento a : listaAsientos) {
            haber1 += a.getHaber();
        }

        diferencia = debe1 - haber1;
        model.addAttribute("diferencia", diferencia);

        // Guarda la fecha seleccionada en una cookie
        if (asiento.getFecha() != null) {
            Cookie cookie = new Cookie("selectedDate", asiento.getFecha().toString());
            cookie.setMaxAge(Integer.MAX_VALUE); // Cookie persistente
            response.addCookie(cookie);
        }

        for (Asiento a : listaAsientos) {
            a.setFecha(fecha.getFecha());
        }
        for (Asiento a : listaAsientos) {
            listacuentas.removeAll(a.getCuentas());
        }

        // Filtra las cuentas disponibles eliminando las cuentas seleccionadas
        List<Cuenta> cuentasDisponibles = cuentaService.cuentasHijasNoPadre();
        for (Asiento a : listaAsientos) {
            for (Cuenta cuenta : a.getCuentas()) {
                cuentasDisponibles.removeIf(c -> c.getId().equals(cuenta.getId()));
            }
        }

        // Actualiza la lista de cuentas disponibles en el modelo
        model.addAttribute("cuentas", cuentasDisponibles);

        return "admin/asiento/new";
    }

    @GetMapping("/librodiario")
    public String LibroDiario(@RequestParam("desde") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
                              @RequestParam("hasta") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
                              Model model) {
        List<Asiento> librodiario = asientoService.obtenerLibroDiarioEntreFechas(fechaDesde, fechaHasta);

        // Agrupa los asientos por fecha utilizando un Map
        Map<LocalDate, List<Asiento>> asientosAgrupados = librodiario.stream()
                .collect(Collectors.groupingBy(Asiento::getFecha));

        // Ordena las fechas de menor a mayor
        List<LocalDate> fechasOrdenadas = asientosAgrupados.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        // Crea un nuevo mapa con las fechas ordenadas
        LinkedHashMap<LocalDate, List<Asiento>> asientosAgrupadosOrdenados = new LinkedHashMap<>();
        fechasOrdenadas.forEach(fecha -> asientosAgrupadosOrdenados.put(fecha, asientosAgrupados.get(fecha)));

        model.addAttribute("libroAgrupado", asientosAgrupadosOrdenados);
        return "/admin/asiento/librodiario";
    }

/**
    @GetMapping("/librodiario")
    public String LibroDiario(@RequestParam("desde") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
                                     @RequestParam("hasta") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
                                     Model model) {
        List<Asiento> librodiario = asientoService.obtenerLibroDiarioEntreFechas(fechaDesde, fechaHasta);
        model.addAttribute("libro", librodiario);
        return "/admin/asiento/librodiario";
    }**/

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
            for(Asiento asiento1 : listaAsientos){
                asiento1.setFecha(a.getFecha());
            }

        }

        return "redirect:/admin/asiento/new";
    }

}
