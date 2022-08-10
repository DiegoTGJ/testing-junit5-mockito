package diego.cursos.petclinic.controllers;

import diego.cursos.petclinic.model.Pet;
import diego.cursos.petclinic.model.Visit;
import diego.cursos.petclinic.services.PetService;
import diego.cursos.petclinic.services.VisitService;
import diego.cursos.petclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy
    PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        Map<String, Object> model = new HashMap<>();

        Pet pet = new Pet(1L);
        Pet pet2 = new Pet(2L);

        petService.save(pet);
        petService.save(pet2);

        given(petService.findById(anyLong())).willCallRealMethod();

        Visit visit = visitController.loadPetWithVisit(1L, model);

        // then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        verify(petService, times(1)).findById(anyLong());
    }
    @Test
    void loadPetWithVisitWithStubbing() {
        // given
        Map<String, Object> model = new HashMap<>();

        Pet pet = new Pet(1L);
        Pet pet2 = new Pet(2L);

        petService.save(pet);
        petService.save(pet2);

        given(petService.findById(anyLong())).willReturn(pet2);
        // when
        Visit visit = visitController.loadPetWithVisit(1L, model);

        // then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(2L);
        verify(petService, times(1)).findById(anyLong());
    }
}