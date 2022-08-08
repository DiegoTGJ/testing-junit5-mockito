package diego.cursos.petclinic.controllers;

import diego.cursos.petclinic.fauxspring.BindingResult;
import diego.cursos.petclinic.model.Owner;
import diego.cursos.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @InjectMocks
    OwnerController ownerController;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    Owner owner = new Owner(1L,"name","last name");;

    private static final String hasErrorReturn = "owners/createOrUpdateOwnerForm";

    @Test
    void processFindFormWildcardString() {
        // given
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);
        // when
        String result = ownerController.processFindForm(owner,bindingResult,null);
        //then
        assertThat("%last name%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(result);
    }


    @Test
    void testProcessCreationFormHasErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(true);
        // when
        String result = ownerController.processCreationForm(owner,bindingResult);
        // then
        then(ownerService).should(never()).save(any());
        assertEquals(hasErrorReturn, result);
    }
    @Test
    void testProcessCreationFormHasNoErrors() {
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