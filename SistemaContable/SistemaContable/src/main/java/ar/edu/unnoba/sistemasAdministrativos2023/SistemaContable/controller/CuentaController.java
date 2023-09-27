package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaService;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;


    @GetMapping("/new")
    public String UserNew(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        model.addAttribute("cuentas", cuentaService.getAll()); // Obtén todas las cuentas disponibles

        return "/admin/cuenta/newcuenta";
    }

    @PostMapping
    public String create(@ModelAttribute Cuenta cuenta,Long cuentaId) {
        Cuenta cuentaSeleccionada = cuentaService.obtenerCuentaPorId(cuentaId);
        cuenta.setPadre(cuentaSeleccionada);
        cuentaService.create(cuenta);
        return "redirect:/admin/home";
    }

    @GetMapping("/detalle/{cuentaId}")
    public String cuentaDetalle(@PathVariable("cuentaId") Long cuentaId, Model model) {

        Cuenta cuenta = cuentaService.obtenerCuentaPorId(cuentaId);
        model.addAttribute("cuenta", cuenta);
        return "/admin/cuenta/libromayor";
    }
}
