package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeveloperTest {

    @Test
    public void newDeveloper_instantiatesCorrectly_true() {
        Developer testDeveloper = new Developer("Parker Brothers");
        assertTrue(testDeveloper instanceof Developer);
    }

    @Test
    public void newDeveloper_instantiatesWithName_parkerborthers() {
        Developer testDeveloper = new Developer("Parker Brothers");
        assertEquals("Parker Brothers", testDeveloper.getName());
    }

    @Test
    public void setId_setsId_1() {
        Developer testDeveloper = new Developer("Parker Brothers");
        testDeveloper.setId(1);
        assertEquals(1, testDeveloper.getId());
    }
}