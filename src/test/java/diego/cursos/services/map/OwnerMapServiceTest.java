package diego.cursos.services.map;


import diego.cursos.model.Owner;
import diego.cursos.model.PetType;
import diego.cursos.services.PetService;
import diego.cursos.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Owner Map Service Test - ")
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    PetTypeService petTypeService;
    PetService petService;

    @BeforeEach
    void setUp() {
        petService = new PetMapService();
        petTypeService = new PetTypeMapService();
        ownerMapService = new OwnerMapService(petTypeService, petService);

        System.out.println("First Before Each");
    }

    @DisplayName("Verify Zero Owners")
    @Test
    void ownersAreZero() {
        assertEquals(0, ownerMapService.findAll().size());
    }

    @DisplayName("Pet Type - ")
    @Nested
    class TestCreatePetTypes {

        @BeforeEach
        void setUp() {
            PetType petType1 = new PetType(1L, "Dog");
            PetType petType2 = new PetType(2L, "Cat");
            petTypeService.save(petType1);
            petTypeService.save(petType2);

            System.out.println("Nested Before Each");
        }

        @DisplayName("Test Pet Type Count")
        @Test
        void testPetCount() {
            assertEquals(2, petTypeService.findAll().size());
        }

        @DisplayName("Save Owners  Tests - ")
        @Nested
        class SaveOwnersTests {
            @BeforeEach
            void setUp() {
                ownerMapService.save(new Owner(1L, "Before", "Each"));

                System.out.println("Saved Owners Before Each");
            }

            @DisplayName("Test Save Owners")
            @Test
            void saveOwner() {
                Owner owner = new Owner(2L, "Joe", "Buck");

                Owner savedOwner = ownerMapService.save(owner);

                assertNotEquals(null, savedOwner);
            }

            @DisplayName("Find Owners Tests - ")
            @Nested
            class FindOwnersTests {

                @DisplayName("Find Owner")
                @Test
                void testFindOwner() {
                    Owner foundOwner = ownerMapService.findById(1L);

                    assertNotNull(foundOwner);
                }

                @DisplayName("Find Owner Not Found")
                @Test
                void testFindOwnerNotFound() {
                    Owner foundOwner = ownerMapService.findById(2L);

                    assertNull(foundOwner);
                }
            }
        }
    }

    @DisplayName("Verify Still Zero Owners")
    @Test
    void ownersAreStillZero() {
        assertEquals(0, ownerMapService.findAll().size());
    }

}