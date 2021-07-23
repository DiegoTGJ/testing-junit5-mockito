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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        when(visitRepository.findAll()).thenReturn(visits);
        Set<Visit> foundVisits = service.findAll();
        assertNotNull(foundVisits);
        verify(visitRepository).findAll();
        assertEquals(1,foundVisits.size());
    }
    @DisplayName("Find By ID Test")
    @Test
    void findById() {
        Visit visit = new Visit();
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        Visit foundVisit = service.findById(2L);
        verify(visitRepository).findById(anyLong());
        assertNotNull(foundVisit);
    }
    @DisplayName("Save Test")
    @Test
    void save() {
        Visit visit = new Visit();
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = service.save(new Visit());
        verify(visitRepository).save(any(Visit.class));
        assertNotNull(savedVisit);
    }
    @DisplayName("Delete Test")
    @Test
    void delete() {
        Visit visit = new Visit();
        service.delete(visit);

        verify(visitRepository).delete(any(Visit.class));
    }
    @DisplayName("Delete by ID Test")
    @Test
    void deleteById() {
        service.deleteById(2L);
        verify(visitRepository).deleteById(anyLong());
    }
}