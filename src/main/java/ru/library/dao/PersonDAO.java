package ru.library.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.models.Book;
import ru.library.models.Person;

import java.util.List;
import java.util.Objects;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return Objects.requireNonNull(sessionFactory).getCurrentSession();
    }
    @Transactional
    public List<Person> getAllPeople() {
        List<Person> people = getSession().createQuery("select p from Person p", Person.class).getResultList();
        return people;
    }

    @Transactional
    public Person findById(Long id) {
        return getSession().get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        getSession().persist(person);
    }

    @Transactional
    public void update(Person person, Integer id) {
        Person personToBeUpdated = getSession().get(Person.class, id);
        personToBeUpdated.setFullName(person.getFullName());
        personToBeUpdated.setYearOfBirth(person.getYearOfBirth());

    }

    @Transactional
    public void delete(Integer id) {
        Person personToBeRemoved = getSession().get(Person.class, id);
        getSession().remove(personToBeRemoved);
    }

    @Transactional
    public List<Book> bookByPerson(Long idPerson){
//        return jdbcTemplate.query("select book. * from person join book on person.idPerson = book.p_id where idPerson=?",new Object[]{idPerson},new BookMapper());
        return getSession().get(Person.class, idPerson).getBooks();
    }
}