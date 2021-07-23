package diego.cursos.controllers;

import diego.cursos.services.VetService;
import diego.cursos.fauxspring.Model;

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
