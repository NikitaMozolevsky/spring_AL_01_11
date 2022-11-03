package com.nikita.lessons.util;

import com.nikita.lessons.dao.impl.PersonDaoImpl;
import com.nikita.lessons.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDaoImpl personDao;

    @Autowired
    public PersonValidator(PersonDaoImpl personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personDao.selectPerson(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }

        //is there a person with the same email in the DB
    }
}
