package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Cuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.TipoCuenta;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.CuentaService;
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
        TipoCuenta tipoCuentaSeleccionado = cuenta.getTipo();
        cuenta.setPadre(cuentaSeleccionada);
        int contadorPadres = 0;
        int codigotemp = 0;
        Cuenta cuentatemp = new Cuenta();

        // Asignar el código según el tipo de cuenta
        int codigo = 0;
        switch (tipoCuentaSeleccionado) {
            case ACTIVO:
                codigo = 100;
                break;
            case PASIVO:
                codigo = 200;
                break;
            case PATRIMONIO_NETO:
                codigo = 300;
                break;
            case RESULTADO_NEGATIVO:
                codigo = 400;
                break;
            case RESULTADO_POSITIVO:
                codigo = calcularCodigoResultadoPositivo(cuenta);
                if(codigo == 510){
                    codigotemp = 51+cuentaSeleccionada.getHijos().size();
                    codigo = codigotemp * 10;
                }
                if(codigo == 511){
                    codigotemp = 1+cuentaSeleccionada.getHijos().size();
                    codigo=510+codigotemp;
                }
                break;
            default:
                // Puedes manejar un valor predeterminado si es necesario
                break;
        }

        // Asignar el código a la cuenta
        cuenta.setCodigo(codigo);
        cuenta.setCodigo(cuentatemp.getCodigo());
        cuentaService.create(cuenta);
        return "redirect:/admin/home";
    }

    @GetMapping("/detalle/{cuentaId}")
    public String cuentaDetalle(@PathVariable("cuentaId") Long cuentaId, Model model) {

        Cuenta cuenta = cuentaService.obtenerCuentaPorId(cuentaId);
        model.addAttribute("cuenta", cuenta);
        return "/admin/cuenta/libromayor";
    }


    private int calcularCodigoResultadoPositivo(Cuenta cuenta) {
        int codigo = 500; // Valor base para RESULTADO_POSITIVO
        int contador = 0; // Valor base para RESULTADO_POSITIVO
        Cuenta cuentaPadre = cuenta.getPadre();

        while (cuentaPadre != null) {

            cuentaPadre = cuentaPadre.getPadre();
            contador+=1;

        }

        if(contador == 2){
            codigo=510;
        }
        if(contador == 3){
            codigo=511;
        }

        return codigo;
    }
    /**while(cuentatemp.getPadre().getPadre() != null){
     codigotemp=contadorPadres*10;
     contadorPadres+=1;
     cuentatemp = cuenta.getPadre();
     }

     codigo = 500+(codigotemp*contadorPadres*cuentaSeleccionada.getHijos().size());**/

}
