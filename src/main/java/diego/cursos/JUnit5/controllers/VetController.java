package diego.cursos.JUnit5.controllers;

import diego.cursos.JUnit5.services.VetService;
import diego.cursos.JUnit5.fauxspring.Model;

public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    public String listVets(Model model){

        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}
