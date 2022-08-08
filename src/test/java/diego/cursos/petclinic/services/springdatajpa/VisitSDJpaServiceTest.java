package diego.cursos.petclinic.services.springdatajpa;

import diego.cursos.petclinic.model.Visit;
import diego.cursos.petclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@DisplayName("Visits Jpa test")
@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    VisitRepository visitRepository;
    @InjectMocks
    VisitSDJpaService service;
    @DisplayName("Find All test")
    @Test
    void findAll() {
        // given
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);
        // when
        Set<Visit> foundVisits = service.findAll();
        // then
        assertNotNull(foundVisits);
        then(visitRepository).should(times(1)).findAll();
        assertEquals(1,foundVisits.size());
    }
    @DisplayName("Find By ID Test")
    @Test
    void findById() {
        // given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        // when
        Visit foundVisit = service.findById(2L);
        // then
        then(visitRepository).should(times(1)).findById(2L);
        assertNotNull(foundVisit);
    }
    @DisplayName("Save Test")
    @Test
    void save() {
        // given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        // when
        Visit savedVisit = service.save(new Visit());
        // then
        then(visitRepository).should(times(1)).save(any());
        assertNotNull(savedVisit);
    }
    @DisplayName("Delete Test")
    @Test
    void delete() {
        // when
        Visit visit = new Visit();
        service.delete(visit);
        // then
        then(visitRepository).should(times(1)).delete(any());
    }
    @DisplayName("Delete by ID Test")
    @Test
    void deleteById() {
        //when
        service.deleteById(2L);
        // then
        then(visitRepository).should(times(1)).deleteById(2L);
    }
}