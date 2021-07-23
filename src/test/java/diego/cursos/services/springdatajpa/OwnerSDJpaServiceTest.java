package diego.cursos.services.springdatajpa;

import diego.cursos.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


@Disabled(value = "disabled until we learn Mocking")
class OwnerSDJpaServiceTest {
    OwnerSDJpaService service;
    @BeforeEach
    void setUp() {
        service = new OwnerSDJpaService(null,null,null);
    }

    @Test
    void findByLastName() {
        Owner foundOwner = service.findByLastName("Buck");
    }

    @Test
    void findAllByLastNameLike() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}