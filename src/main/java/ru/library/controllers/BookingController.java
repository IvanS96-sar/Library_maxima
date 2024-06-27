package ru.library.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.dao.BookDAO;
import ru.library.dao.PersonDAO;
import ru.library.models.Book;
import ru.library.models.Person;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookingController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BookingController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String showAllBook(Model model) {
        List<Book> allBook = bookDAO.getAllBook();

        model.addAttribute("allBook", allBook);

        return "book/all-book";
    }
    @GetMapping("/{id}")
    public String showBookById(@PathVariable("id") Long id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("bookById", bookDAO.findById(id));

        if (bookDAO.getBookOwner(id) != null) {
            model.addAttribute("ownerById", bookDAO.getBookOwner(id));
        } else {
            model.addAttribute("allPerson", personDAO.getAllPeople());
        }
        return "book/book-by-id";
    }
    @GetMapping("/create")
    public String getPageToCreateNewBook(Model model) {
        model.addAttribute("newBook", new Book());
        return "book/create-new-book";
    }

    @PostMapping()
    public String createNewBook(@ModelAttribute("newBook") @Valid Book book,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/create-new-book";
        }

        bookDAO.save(book);

        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditBook(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("editedBook", bookDAO.findById(Long.valueOf(id)));
        return "book/edit-book";
    }

    @PostMapping("/{id}")
    public String editBook(@PathVariable("id") Integer idBook,
                             @ModelAttribute("editedBook") @Valid Book book,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/edit-book";
        }
        bookDAO.update(book, idBook);

        return "redirect:/book";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Integer id) {
        bookDAO.delete(id);

        return "redirect:/book";
    }

    @PostMapping("/{id}/give")
    public String giveBook(@ModelAttribute("person") Person person, @PathVariable("id") Long id) {
        bookDAO.giveBook(id,person);
        return "redirect:/book/{id}";
    }

    @PostMapping("/{id}/return")
    public String returnBook(@PathVariable("id") Long id) {
        bookDAO.takeBookReturn(id);
        return "redirect:/book/{id}";
    }
}
