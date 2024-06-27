package ru.library.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.library.models.Book;
import ru.library.models.Person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return Objects.requireNonNull(sessionFactory).getCurrentSession();
    }

    @Transactional
    public List<Book> getAllBook() {
        return getSession().createQuery("from Book ",Book.class).getResultList();
    }

    @Transactional
    public Book findById(Long idBook) {
        return getSession().get(Book.class, idBook);
    }

    @Transactional
    public void save(Book book) {
        getSession().persist(book);

    }

    @Transactional
    public void update(Book book, Integer idBook) {
        Book bookUpdated = getSession().get(Book.class, idBook);
        bookUpdated.setName(book.getName());
        bookUpdated.setYear(book.getYear());
        bookUpdated.setAuthor(book.getAuthor());
    }

    @Transactional
    public void delete(Integer idBook) {
        Book bookRemoved = getSession().get(Book.class, idBook);
        getSession().remove(bookRemoved);
    }

    @Transactional
    public Person getBookOwner(Long idBook) {
        return getSession().get(Book.class, idBook).getOwnerOfBook();
    }


    @Transactional
    public void takeBookReturn(Long idBook) {
        Book bookReturn = getSession().get(Book.class, idBook);
        bookReturn.setOwnerOfBook(null);
    }

    @Transactional
    public void giveBook(Long idBook, Person person) {
        Book bookGive= getSession().get(Book.class, idBook);
        bookGive.setOwnerOfBook(person);
    }
}

