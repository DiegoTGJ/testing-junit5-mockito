package diego.cursos.JUnit5.model;

import diego.cursos.JUnit5.CustomArgsProvider;
import diego.cursos.JUnit5.ModelTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;


class OwnerTest implements ModelTests {
    @Test
    void dependentAssertions() {
        Owner owner = new Owner(1l,"Joe", "Buck");
        owner.setCity("Key West");
        owner.setTelephone("123123123213");

        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Joe", owner.getFirstName(), "First Name did not Match"),
                        () -> assertEquals("Buck", owner.getLastName())),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("Key West", owner.getCity(), "City did not match"),
                        () -> assertEquals("123123123213", owner.getTelephone())
                        ));

        assertThat(owner.getCity(),is("Key West"));
    }

    @DisplayName("Value Source Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ValueSource(strings = {"Spring", "Framework", "Diego"})
    void testValueSource(String val) {
        System.out.println(val);
    }

    @DisplayName("ENUM Source Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void enumTest(OwnerType ownerType){
        System.out.println(ownerType);
    }
    @DisplayName("CSV Source Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvSource({
            "FL, 1, 1",
            "OH, 2, 2",
            "MI, 3, 1"
    })
    void csvInputTest(String stateName, int val1, int val2) {
        System.out.println(stateName+" = "+val1+":"+val2);
    }
    @DisplayName("CSV From File Source Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
    void csvFromFileTest(String stateName, int val1, int val2) {
        System.out.println(stateName+" = "+val1+":"+val2);
    }

    @DisplayName("Method Provider Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @MethodSource("getargs")
    void FromMethodTest(String stateName, int val1, int val2) {
        System.out.println(stateName+" = "+val1+":"+val2);
    }

    static Stream<Arguments> getargs(){
        return Stream.of(Arguments.of("FL", 77, 81),
                         Arguments.of("OH", 1, 12),
                         Arguments.of("CL", 133, 113));
    }

    @DisplayName("Custom Provider Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void FromCustomArgProviderTest(String stateName, int val1, int val2) {
        System.out.println(stateName+" = "+val1+":"+val2);
    }

}