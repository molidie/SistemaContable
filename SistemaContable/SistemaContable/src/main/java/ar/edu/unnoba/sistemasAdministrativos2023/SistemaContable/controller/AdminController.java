package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Admin;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private IAdminService adminService;

    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/new")
    public String adminNew(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/newAdmin"; //retorna el nombre del html
    }

    @GetMapping("/home")
    public String index(Model model) {
        List<Admin> admins = adminService.getAll();
        model.addAttribute("admin", admins);
        return "admin/home";
    }

    @PostMapping
    public String create(@ModelAttribute Admin administrator) {
        adminService.create(administrator);
        return "redirect:/admin/home"; /*esto es enviado por url*/ 
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return "redirect:/admin/home";
    }
}