package ru.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.dao.BookDAO;
import ru.library.dao.PersonDAO;
import ru.library.models.Person;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        List<Person> allPeople = personDAO.getAllPeople();
        model.addAttribute("allPeople", allPeople);

        return "people/all-people";
    }


    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("personById", personDAO.findById(id));
        model.addAttribute("bookPerson",personDAO.bookByPerson(id));
        return "people/person-by-id";
    }

    @GetMapping("/create")
    public String getPageToCreateNewPerson(Model model) {
        model.addAttribute("newPerson", new Person());
        return "people/create-new-person";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("newPerson") @Valid Person person,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/create-new-person";
        }

        personDAO.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditPerson(Model model, @PathVariable("id") Integer idPerson) {
        model.addAttribute("editedPerson", personDAO.findById(Long.valueOf(idPerson)));
        return "people/edit-person";
    }

    @PostMapping("/{id}")
    public String editPerson(@PathVariable("id")Integer idPerson,
                             @ModelAttribute("editedPerson") @Valid Person person,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/edit-person";
        }
        personDAO.update(person, idPerson);

        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Integer idPerson) {
        personDAO.delete(idPerson);

        return "redirect:/people";
    }

}
