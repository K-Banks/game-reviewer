package dao;

import models.Developer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import java.util.List;

import static org.junit.Assert.*;

public class Sql2oDeveloperDaoTest {
    private Sql2oDeveloperDao developerDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        developerDao = new Sql2oDeveloperDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    private Developer setUpDeveloper(){
        return new Developer("Parker Brothers");
    }

    @Test
    public void add_setsId_1() throws Exception {
        Developer developer = setUpDeveloper();
        int originalId = developer.getId();
        developerDao.add(developer);
        assertNotEquals(originalId, developer.getId());
    }

    @Test
    public void getAll_returnsEmptyListIfNoDevelopers_0() {
        assertEquals(0, developerDao.getAll().size());
    }

    @Test
    public void getAll_returnsAllDevelopers_2() {
        Developer developer = setUpDeveloper();
        Developer otherDeveloper = new Developer("Milton Bradley");
        developerDao.add(developer);
        developerDao.add(otherDeveloper);
        assertEquals(2, developerDao.getAll().size());
    }

    @Test
    public void findById_returnsCorrectDeveloper_miltonbradley() {
        Developer developer = setUpDeveloper();
        Developer otherDeveloper = new Developer("Milton Bradley");
        developerDao.add(developer);
        developerDao.add(otherDeveloper);
        assertEquals(otherDeveloper.getName(), developerDao.findById(2).getName());
    }

    @Test
    public void update_changesDeveloperName_true() {
        Developer developer = setUpDeveloper();
        developerDao.add(developer);
        String originalName = developer.getName();
        developerDao.update(1, "Milton Bradley");
        Developer updatedDeveloper = developerDao.findById(1);
        assertNotEquals(originalName, updatedDeveloper.getName());
    }

    @Test
    public void deleteById_deletesCorrectDeveloper() {
        Developer developer = setUpDeveloper();
        Developer otherDeveloper = new Developer("Milton Bradley");
        developerDao.add(developer);
        developerDao.add(otherDeveloper);
        developerDao.deleteById(1);
        List<Developer> developers = developerDao.getAll();
        assertEquals(1, developers.size());
        assertFalse(developers.contains(developer));
        assertTrue(developers.get(0).getName().equals(otherDeveloper.getName()));
    }

    @Test
    public void clearAll_deletesAllDevelopers() {
        Developer developer = setUpDeveloper();
        Developer otherDeveloper = new Developer("Milton Bradley");
        developerDao.add(developer);
        developerDao.add(otherDeveloper);
        developerDao.clearAllDevelopers();
        List<Developer> developers = developerDao.getAll();
        assertEquals(0, developers.size());
        assertFalse(developers.contains(developer));
        assertFalse(developers.contains(otherDeveloper));
    }
}