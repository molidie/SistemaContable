package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.model.Admin;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admins")
public class AdminController {

    private IAdminService adminService;

    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/new")
    public String adminNew(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/nuevoAdmin"; //retorna el nombre del html
    }

    @GetMapping
    public String index(Model model) {
        List<Admin> admins = adminService.getAll();
        model.addAttribute("admins", admins);
        return "admin/index";
    }

    @PostMapping
    public String create(@ModelAttribute Admin administrator) {
        adminService.create(administrator);
        return "redirect:/admins";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return "redirect:/admins";
    }
}