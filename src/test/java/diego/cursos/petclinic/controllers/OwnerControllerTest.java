package diego.cursos.petclinic.controllers;

import diego.cursos.petclinic.fauxspring.BindingResult;
import diego.cursos.petclinic.model.Owner;
import diego.cursos.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;
    @Mock
    BindingResult bindingResult;

    OwnerController ownerController;

    Owner owner;

    private static final String hasErrorReturn = "owners/createOrUpdateOwnerForm";
    @BeforeEach
    void setUp() {
        ownerController = new OwnerController(ownerService);
        owner = new Owner(1L,"name","last name");
    }

    @Test
    void testProcessCreationFormHasNoErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(true);
        // when
        String result = ownerController.processCreationForm(owner,bindingResult);
        // then
        then(ownerService).should(never()).save(any());
        assertEquals(hasErrorReturn, result);
    }
    @Test
    void testProcessCreationFormHasErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);
        // when
        String result = ownerController.processCreationForm(owner,bindingResult);
        // then
        then(ownerService).should(times(1)).save(any());
        assertEquals("redirect:/owners/"+owner.getId(), result);
    }
}