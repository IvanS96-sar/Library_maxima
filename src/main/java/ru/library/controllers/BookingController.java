package ru.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.models.Book;
import ru.library.models.Person;
import ru.library.service.BookService;
import ru.library.service.PeopleService;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookingController {
    private final PeopleService peopleService;
    private final BookService bookService;


    @Autowired
    public BookingController(@Qualifier("peopleService")PeopleService peopleService, @Qualifier("bookService")BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }
    @GetMapping()
    public String showAllBook(Model model) {
        List<Book> allBook = bookService.getAllBook();

        model.addAttribute("allBook", allBook);

        return "book/all-book";
    }
    @GetMapping("/{id}")
    public String showBookById(@PathVariable("id") Long id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("bookById", bookService.findById(id));

        if (bookService.getBookOwner(id) != null) {
            model.addAttribute("ownerById",bookService.getBookOwner(id));
        } else {
            model.addAttribute("allPerson", peopleService.getAllPeople());
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

        bookService.save(book);

        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditBook(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("editedBook", bookService.findById(Long.valueOf(id)));
        return "book/edit-book";
    }

    @PostMapping("/{id}")
    public String editBook(@PathVariable("id") Integer idBook,
                             @ModelAttribute("editedBook") @Valid Book book,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/edit-book";
        }
       bookService.update(book, idBook);

        return "redirect:/book";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Integer id) {
        bookService.delete(id);

        return "redirect:/book";
    }

    @PostMapping("/{id}/give")
    public String giveBook(@ModelAttribute("person") Person person, @PathVariable("id") Long id) {
        bookService.giveBook(id,person);
        return "redirect:/book/{id}";
    }

    @PostMapping("/{id}/return")
    public String returnBook(@PathVariable("id") Long id) {
        bookService.takeBookReturn(id);
        return "redirect:/book/{id}";
    }
}
