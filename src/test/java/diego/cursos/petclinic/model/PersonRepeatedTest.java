package diego.cursos.petclinic.model;

import diego.cursos.petclinic.ModelRepeatedTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

public class PersonRepeatedTest implements ModelRepeatedTests {

    @RepeatedTest(value = 10, name = "This is the {displayName}")
    @DisplayName("My Repeated Test")
    void myRepeatedTest() {

    }

    @RepeatedTest(value = 7, name = "This is the {displayName} ")
    @DisplayName("Repeated Test with DI")
    void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {
//        System.out.println(testInfo.getDisplayName() + ": " + repetitionInfo.getCurrentRepetition());
    }

    @RepeatedTest(value = 3, name = "This is the {displayName} ")
    @DisplayName("Assignment Repeated Test")
    void assignmentTest(){

    }
}
