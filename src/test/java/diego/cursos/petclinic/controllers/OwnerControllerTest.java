package diego.cursos.petclinic.controllers;

import diego.cursos.petclinic.fauxspring.BindingResult;
import diego.cursos.petclinic.fauxspring.Model;
import diego.cursos.petclinic.model.Owner;
import diego.cursos.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;
    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    Owner owner = new Owner(1L,"name","last name");;

    private static final String hasErrorReturn = "owners/createOrUpdateOwnerForm";

    @BeforeEach
    void setUp() {
        lenient().when(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).thenAnswer(invocation -> {
            String name = invocation.getArgument(0);
            List<Owner> ownerList = new ArrayList<>();
            if (name.equals("%last name%")){
                ownerList.add(new Owner(1L,"name","last name"));
                return ownerList;
            }
            if (name.equals("%returnnotfound%")){
                return ownerList;
            }
            if (name.equals("%findmany%")){
                ownerList.add(new Owner(1L,"name","last name"));
                ownerList.add(new Owner(1L,"name2","last name2"));
                return ownerList;
            }
            throw new RuntimeException("Invalid Argument");
        });
    }

    @Test
    void processFindFormFoundOne() {
        // given
//        List<Owner> ownerList = new ArrayList<>();
//        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);
        // when
        String result = ownerController.processFindForm(owner,bindingResult,null);
        //then
        assertThat("%last name%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/"+owner.getId()).isEqualToIgnoringCase(result);
        verifyNoInteractions(model);
    }
    @Test
    void processFindFormFoundNone() {
        // given
//        List<Owner> ownerList = new ArrayList<>();
//        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);
        owner.setLastName("returnnotfound");
        // when
        String result = ownerController.processFindForm(owner,bindingResult,model);
        //then
        assertThat("%returnnotfound%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(result);
        verifyNoInteractions(model);
    }
    @Test
    void processFindFormFindMany() {
        // given
//        List<Owner> ownerList = new ArrayList<>();
//        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);
        owner.setLastName("findmany");
        InOrder order= inOrder(ownerService, model);
        // when
        String result = ownerController.processFindForm(owner,bindingResult,model);
        //then
        assertThat("%findmany%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(result);

        order.verify(ownerService).findAllByLastNameLike(anyString());
        order.verify(model,times(1)).addAttribute(anyString(),anyList());
        verifyNoMoreInteractions(model);
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