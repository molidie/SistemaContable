package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Admin;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Usuarios;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAdminService;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String UserNew(Model model) {
        model.addAttribute("user", new Usuarios());
        return "user/nuevoUser";
    }

    @GetMapping
    public String index(Model model) {
        List<Usuarios> users = userService.getAll();
        model.addAttribute("user", users);
        return "user/index";
    }

    @PostMapping
    public String create(@ModelAttribute Usuarios usuarios) {
        userService.create(usuarios);
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/user";
    }

}
