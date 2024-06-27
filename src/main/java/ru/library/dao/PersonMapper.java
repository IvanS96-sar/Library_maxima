package ru.library.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.library.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setIdPerson(rs.getLong("idPerson"));
        person.setFullName(rs.getString("fullName"));
        person.setYearOfBirth(rs.getInt("yearOfBirth"));

        return person;
    }
}