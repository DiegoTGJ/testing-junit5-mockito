package diego.cursos.controllers;

import diego.cursos.ControllerTests;
import diego.cursos.fauxspring.Model;
import diego.cursos.fauxspring.ModelMapImpl;
import diego.cursos.model.Vet;
import diego.cursos.services.SpecialtyService;
import diego.cursos.services.VetService;
import diego.cursos.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest implements ControllerTests {

    VetController vetController;
    VetService vetService;
    SpecialtyService specialtyService;
    Model model;
    @BeforeEach
    void setUp() {
        vetService = new VetMapService(specialtyService);
        vetController = new VetController(vetService);
        model = new ModelMapImpl();

        Vet vet1 = new Vet(1L,"Diego","Tapia",null);
        Vet vet2 = new Vet(2L,"Pedrito","Gonzalez",null);

        vetService.save(vet1);
        vetService.save(vet2);
    }

    @Test
    void listVets() {
        assertEquals("vets/index",vetController.listVets(model));

        Set modelAttribute = (Set) ((ModelMapImpl) model).getMap().get("vets");
        assertEquals(2,modelAttribute.size());
    }
}