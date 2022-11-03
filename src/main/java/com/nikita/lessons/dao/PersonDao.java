package com.nikita.lessons.dao;

import com.nikita.lessons.models.Person;

import java.util.List;

public interface PersonDao {
    List<Person> selectPeople();
    Person selectPerson(int id);
    void insertPerson(Person person);
    void updatePerson(int id, Person updatedPerson);
    void deletePerson(int id);
    void testMultipleUpdate();
    void testBatchUpdate();
}
