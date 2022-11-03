package com.nikita.lessons.controllers;


import com.nikita.lessons.dao.impl.PersonDaoImpl;
import com.nikita.lessons.models.Person;
import com.nikita.lessons.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDaoImpl personDao;
    private final PersonValidator personValidator;
    public static final String PEOPLE_REDIRECT_PAGE = "redirect:/people";

    @Autowired
    public PeopleController(PersonDaoImpl personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    String getAllPeople(Model model) {
        model.addAttribute("people", personDao.selectPeople());
        return "people/all_people";
    }

    @GetMapping("/{id}")
    public String getPersonById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.selectPerson(id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            return "people/new";
        }
        personDao.insertPerson(person);
        return PEOPLE_REDIRECT_PAGE;
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.selectPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable int id) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(this.getClass() + "Error");
            return "people/edit";
        }
        personDao.updatePerson(id, person);
        return PEOPLE_REDIRECT_PAGE;
    }

    @DeleteMapping("{id}")
    public String deletePerson(@PathVariable int id) {
        personDao.deletePerson(id);
        return PEOPLE_REDIRECT_PAGE;
    }
}
