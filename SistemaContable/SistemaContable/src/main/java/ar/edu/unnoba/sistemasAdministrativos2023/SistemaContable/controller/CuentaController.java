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
    public String create(@ModelAttribute Cuenta cuenta,Long cuentaId,Model model) {
        Cuenta cuentaSeleccionada = cuentaService.obtenerCuentaPorId(cuentaId);
        TipoCuenta tipoCuentaSeleccionado = cuenta.getTipo();
        cuenta.setPadre(cuentaSeleccionada);
        int codigotemp = 0;
        Cuenta cuentatemp = new Cuenta();

        int codigo = 0;
        switch (tipoCuentaSeleccionado) {
            case ACTIVO:
                codigo = 100;
                codigo = calcularCodigoResultadoPositivo(cuenta,codigo);
                if(codigo == 110){
                    codigotemp = 11+cuentaSeleccionada.getHijos().size();
                    codigo = codigotemp * 10;
                }
                if(codigo == cuenta.getPadre().getCodigo()){
                    codigotemp = 1 + cuentaSeleccionada.getHijos().size();
                    codigo=cuenta.getPadre().getCodigo()+codigotemp;
                }
                break;
            case PASIVO:
                codigo = 200;
                codigo = calcularCodigoResultadoPositivo(cuenta,codigo);
                if(codigo == 210){
                    codigotemp = 21+cuentaSeleccionada.getHijos().size();
                    codigo = codigotemp * 10;
                }
                if(codigo == cuenta.getPadre().getCodigo()){
                    codigotemp = 1 + cuentaSeleccionada.getHijos().size();
                    codigo=cuenta.getPadre().getCodigo()+codigotemp;
                }
                break;
            case PATRIMONIO_NETO:
                codigo = 300;
                codigo = calcularCodigoResultadoPositivo(cuenta,codigo);
                if(codigo == 310){
                    codigotemp = 31+cuentaSeleccionada.getHijos().size();
                    codigo = codigotemp * 10;
                }
                if(codigo == cuenta.getPadre().getCodigo()){
                    codigotemp = 1 + cuentaSeleccionada.getHijos().size();
                    codigo=cuenta.getPadre().getCodigo()+codigotemp;
                }
                break;
            case RESULTADO_NEGATIVO:
                codigo = 400;
                codigo = calcularCodigoResultadoPositivo(cuenta,codigo);
                if(codigo == 410){
                    codigotemp = 41+cuentaSeleccionada.getHijos().size();
                    codigo = codigotemp * 10;
                }
                if(codigo == cuenta.getPadre().getCodigo()){
                    codigotemp = 1 + cuentaSeleccionada.getHijos().size();
                    codigo=cuenta.getPadre().getCodigo()+codigotemp;
                }
                break;
            case RESULTADO_POSITIVO:
                codigo = 500;
                codigo = calcularCodigoResultadoPositivo(cuenta,codigo);
                if(codigo == 510){
                    codigotemp = 51+cuentaSeleccionada.getHijos().size();
                    codigo = codigotemp * 10;
                }
                if(codigo == cuenta.getPadre().getCodigo()){
                    codigotemp = 1 + cuentaSeleccionada.getHijos().size();
                    codigo=cuenta.getPadre().getCodigo()+codigotemp;
                }
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
        return "redirect:/admin/home";
    }

    @GetMapping("/detalle/{cuentaId}")
    public String cuentaDetalle(@PathVariable("cuentaId") Long cuentaId, Model model) {

        Cuenta cuenta = cuentaService.obtenerCuentaPorId(cuentaId);
        model.addAttribute("cuenta", cuenta);
        return "/admin/cuenta/libromayor";
    }


    private int calcularCodigoResultadoPositivo(Cuenta cuenta,int codigo) {

        int contador = 0; // Valor base para RESULTADO_POSITIVO
        Cuenta cuentaPadre = cuenta.getPadre();

        while (cuentaPadre != null) {

            cuentaPadre = cuentaPadre.getPadre();
            contador+=1;

        }

        if(contador == 2){
            codigo=cuenta.getPadre().getCodigo()+10;
        }
        if(contador == 3){
            codigo=cuenta.getPadre().getCodigo();
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
