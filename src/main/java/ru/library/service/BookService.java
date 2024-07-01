package ru.library.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.library.models.Book;
import ru.library.models.Person;
import ru.library.repositories.BookRepository;

import java.util.List;
import java.util.Optional;
@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Transactional
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book findById(Long idBook) {
        Optional<Book> byId = bookRepository.findById(idBook);
        return byId.orElse(null);

    }

    @Transactional
    public void save(Book book) {
       bookRepository.save(book);

    }

    @Transactional
    public void update(Book book, Integer idBook) {
        book.setIdBook(Long.valueOf(idBook));
        bookRepository.save(book);
    }

    @Transactional
    public void delete(Integer idBook) {
       bookRepository.deleteById(Long.valueOf(idBook));
    }

    @Transactional
    public Person getBookOwner(Long idBook) {

        return bookRepository.findById(idBook).orElse(null).getOwnerOfBook();
    }

    @Transactional
    public void takeBookReturn(Long idBook) {
        bookRepository.findById(idBook).orElse(null).setOwnerOfBook(null);
    }

    @Transactional
    public void giveBook(Long idBook, Person person) {

        bookRepository.findById(idBook).orElse(null).setOwnerOfBook(person);
    }
}
