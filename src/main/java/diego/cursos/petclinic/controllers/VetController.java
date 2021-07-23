package diego.cursos.petclinic.controllers;

import diego.cursos.petclinic.services.VetService;
import diego.cursos.petclinic.fauxspring.Model;

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
