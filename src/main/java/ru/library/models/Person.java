package ru.library.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;

    @NotEmpty(message = "Name should not not be empty")
    @Size(min = 2, max = 50, message = "Name should be min 2 symbols and max 50 symbols")
    private String fullName;
    @Min(value = 0, message = "yearOfBirth should be min 1900 years")
    private Integer yearOfBirth;
    @OneToMany(mappedBy = "ownerOfBook", fetch = FetchType.EAGER)
    private List<Book> books;

    @Override
    public String toString() {
        return "Person{" +
                "idPerson=" + idPerson +
                ", fullName='" + fullName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}