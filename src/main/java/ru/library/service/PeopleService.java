package ru.library.service;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.library.models.Book;
import ru.library.models.Person;
import ru.library.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository repository;

    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public List<Person> getAllPeople() {
        return repository.findAll();
    }

    @Transactional
    public Person findById(Long id) {
        Optional<Person> byId = repository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        repository.save(person);
    }

    @Transactional
    public void update(Person person, Integer id) {
        person.setIdPerson(Long.valueOf(id));
        repository.save(person);

    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(Long.valueOf(id));
    }


    @Transactional
    public List<Book> bookByPerson(Long idPerson){

        return repository.findById(idPerson).orElse(null).getBooks();
    }
}
