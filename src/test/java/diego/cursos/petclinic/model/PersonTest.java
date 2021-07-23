package diego.cursos.petclinic.model;


import diego.cursos.petclinic.ModelTests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class PersonTest implements ModelTests {
    @Test
    void groupedAssertions() {
        //given
        Person person = new Person(1L, "Joe", "Buck");
        //then
        assertAll("Test Props Set",
                () -> assertEquals("Joe", person.getFirstName()),
                () -> assertEquals("Buck", person.getLastName())
        );

    }

    @Test
    void groupedAssertionMsgs() {
        //given
        Person person = new Person(1L, "Joe", "Buck");
        //then
        assertAll("Test Props Set",
                () -> assertEquals("Joe", person.getFirstName(), "First Name Failed"),
                () -> assertEquals("Buck", person.getLastName(), "Last Name Failed")
        );

    }


}