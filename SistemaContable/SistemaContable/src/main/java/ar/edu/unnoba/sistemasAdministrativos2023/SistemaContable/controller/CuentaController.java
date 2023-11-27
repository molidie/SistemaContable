package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Asiento;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.TipoCuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.CuentaRepository;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
    private CuentaRepository cuentaRepository;


    @GetMapping("/new")
    public String UserNew(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        model.addAttribute("cuentas", cuentaService.getAll()); // Obtén todas las cuentas disponibles

        return "/admin/cuenta/newcuenta";
    }

    @PostMapping
    public String create(@ModelAttribute Cuenta cuenta,Long cuentaId,Model model) {
        Cuenta cuentaSeleccionada = cuentaService.obtenerCuentaPorId(cuentaId);
        TipoCuenta tipoCuentaSeleccionado = cuenta.getTipo();
        cuenta.setPadre(cuentaSeleccionada);
        int codigotemp = 0;


        int codigo = 0;
        switch (tipoCuentaSeleccionado) {
            case ACTIVO:
                codigo = 100;
                codigo = calcularCodigoResultadoPositivo(cuentaSeleccionada,codigo);
                break;
            case PASIVO:
                codigo = 200;
                codigo = calcularCodigoResultadoPositivo(cuentaSeleccionada,codigo);
                break;
            case PATRIMONIO_NETO:
                codigo = 300;
                codigo = calcularCodigoResultadoPositivo(cuentaSeleccionada,codigo);

                break;
            case RESULTADO_NEGATIVO:
                codigo = 400;
                codigo = calcularCodigoResultadoPositivo(cuentaSeleccionada,codigo);

                break;
            case RESULTADO_POSITIVO:
                codigo = 500;
                codigo = calcularCodigoResultadoPositivo(cuentaSeleccionada,codigo);

                break;
            default:

                break;
        }

        if (codigo == 190 || codigo == 290 || codigo == 390 || codigo == 490 || codigo == 590 || codigo == 199 || codigo == 299 || codigo == 399 || codigo == 499 || codigo == 599 || codigo == 600) {
            model.addAttribute("error", "No se puede crear una cuenta con el código " + codigo);

            return "/admin/cuenta/newcuenta";
        }
        cuenta.setCodigo(codigo);
        cuentaService.create(cuenta);
        return "redirect:/admin/cuenta/plan";
    }

    @GetMapping("/detalle")
    public String cuentaDetalle(@RequestParam(name = "cuentaId", required = false) Long cuentaId, Model model) {

        Cuenta cuenta = cuentaService.obtenerCuentaPorId(cuentaId);
        cuenta.setSaldo_actual(cuentaService.CalcularSaldo(cuenta));
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("asientos", cuenta.getAsientos());
        model.addAttribute("cuentas", cuentaService.getAll());
        return "/admin/cuenta/libromayor";
    }


    private int calcularCodigoResultadoPositivo(Cuenta cuenta,int codigo) {

        int contador = 0;
        Cuenta cuentaPadre = cuenta;
        Cuenta padre = cuenta;

        while (cuentaPadre != null){
            cuentaPadre = cuentaPadre.getPadre();
            contador++;
        }
        if(contador == 1){
            return  codigo;
        }

        if (contador == 2){
            int hijos = cuenta.getHijos().size() * 10;
            return padre.getCodigo()+ 10 + hijos;
        }

        if (contador == 3){
            int hijos = cuenta.getHijos().size();
            return codigo =padre.getCodigo()+hijos+1;
        }

        return codigo;
    }


    @GetMapping("/plan")
    public String mostrarPlanDeCuentas(Model model) {
        List<Cuenta> cuentas = cuentaService.getAll(); // Obtener todas las cuentas disponibles
        cuentas.sort(Comparator.comparing(Cuenta::getCodigo)); // Ordenar las cuentas por el código
        model.addAttribute("cuentas", cuentas);
        return "/admin/cuenta/plan"; // Devuelve el nombre de la plantilla HTML
    }



    @GetMapping ("/editar/{idE}")
    public String editarProducto(@PathVariable("idE") Long id, Model  model){
        Cuenta cuenta = cuentaService.obtenerCuentaPorId(id);

        if (cuenta.getHijos().size() == 0) {
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("cuentas", cuentaService.getAll());
        return "/admin/cuenta/edit";

    }
        return "redirect:/admin/cuenta/plan";
    }

    @PostMapping("/editarCuenta/{idE}")
    public String actualizarProdcuto(@PathVariable("idE") Long id, @ModelAttribute Cuenta cuenta, Model model) {
        model.addAttribute("cuenta",cuenta);
        Cuenta cuentaSeleccionada= cuentaService.obtenerCuentaPorId(id);
        List<Cuenta> cuentaList = cuentaService.getAll();
        for(Cuenta cuenta1 : cuentaList){
            if(cuenta1.getCodigo() == cuenta.getCodigo()){
                return "redirect:/admin/cuenta/plan";
            }

        }
        int cod = cuenta.getCodigo()-1;
        Cuenta cuentaHermana = cuentaService.obtenerCuentaPorCod(cod);
        cuenta.setPadre(cuentaHermana.getPadre());
        cuenta.setTipo(cuentaHermana.getTipo());

        cuentaSeleccionada.setPadre(cuenta.getPadre());
        cuentaSeleccionada.setTipo(cuenta.getTipo());
        cuentaSeleccionada.setCodigo(cuenta.getCodigo());
        cuentaService.create(cuentaSeleccionada);
        return "redirect:/admin/cuenta/plan";
    }

}
