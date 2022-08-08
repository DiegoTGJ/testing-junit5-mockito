package diego.cursos.petclinic.services.springdatajpa;

import diego.cursos.petclinic.model.Speciality;
import diego.cursos.petclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;
    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void deleteById() {
        // given - none
        // when
        service.deleteById(1L);
        service.deleteById(1L);
        // then
        then(specialtyRepository).should(times(2)).deleteById(any());
    }

    @Test
    void deleteByIdAtLeast() {
        // given - none
        // when
        service.deleteById(1L);
        service.deleteById(1L);
        // then
        then(specialtyRepository).should(atLeastOnce()).deleteById(any());
    }

    @Test
    void deleteByIdAtMost() {
        // when
        service.deleteById(1L);
        service.deleteById(1L);
        // then
        then(specialtyRepository).should(atMost(2)).deleteById(any());
    }


    @Test
    void deleteByIdNever() {
        // when
        service.deleteById(1L);
        service.deleteById(1L);
        // then
        then(specialtyRepository).should(atMost(2)).deleteById(1L);
        then(specialtyRepository).should(never()).deleteById(2L);
    }

    @Test
    void testDelete() {
        // when
        service.delete(new Speciality());

        //then
        then(specialtyRepository).should().delete(any());
    }


    @Test
    void testDeleteByObject() {
        //given
        Speciality speciality = new Speciality();
        //when
        service.delete(speciality);
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        // Given
        Speciality speciality = new Speciality();

        given(specialtyRepository.findById(anyLong())).willReturn(Optional.of(speciality));
        //When
        Speciality foundSpecialty = service.findById(2L);
        //Then
        assertNotNull(foundSpecialty);

        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("ERROEREEROERROEROEROEROE")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class,() -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIdThrows() {
        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("EEROEROEOROEORE"));

        assertThrows(RuntimeException.class, () -> service.findById(1L));

        then(specialtyRepository).should(times(1)).findById(1L);
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException("EREOREOREOR")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testSaveLambda(){
        // given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("not match");

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        // need mock to only return on match MATCH_ME string

        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

        // when
        Speciality returnedSpecialty = service.save(speciality);
        // then
        assertNull(returnedSpecialty);
    }
}