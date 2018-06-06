package dao;

import models.Developer;

import java.util.List;

public interface DeveloperDao {

    List<Developer> getAll();

    // CREATE
    void add(Developer developer);

    // READ
    Developer findById(int id);

    //UPDATE
    void update(int id, String name);

    // DELETE
    void deleteById(int id);
    void clearAllDevelopers();
    
}
