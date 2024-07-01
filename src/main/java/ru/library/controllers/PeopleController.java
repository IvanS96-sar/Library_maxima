package ru.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.library.models.Person;
import ru.library.service.BookService;
import ru.library.service.PeopleService;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BookService bookService;


    @Autowired
    public PeopleController(@Qualifier("peopleService")PeopleService peopleService, @Qualifier("bookService")BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        List<Person> allPeople = peopleService.getAllPeople();
        model.addAttribute("allPeople", allPeople);

        return "people/all-people";
    }


    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("personById", peopleService.findById(id));
        model.addAttribute("bookPerson",peopleService.bookByPerson(id));
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

        peopleService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditPerson(Model model, @PathVariable("id") Integer idPerson) {
        model.addAttribute("editedPerson",peopleService.findById(Long.valueOf(idPerson)));
        return "people/edit-person";
    }

    @PostMapping("/{id}")
    public String editPerson(@PathVariable("id")Integer idPerson,
                             @ModelAttribute("editedPerson") @Valid Person person,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/edit-person";
        }
        peopleService.update(person, idPerson);

        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Integer idPerson) {
        peopleService.delete(idPerson);

        return "redirect:/people";
    }

}
