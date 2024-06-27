package ru.library.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;
    @NotEmpty(message = "Name should not not be empty")
    @Size(min = 2, max = 50, message = "Name should be min 2 symbols and max 50 symbols")
    private String name;
    @Size(min = 2, max = 50, message = "Name author should be min 2 symbols and max 50 symbols")
    private String author;
    @Min(value = 1900, message = "year should be min 1900 years")
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "p_id", referencedColumnName = "idPerson")
    private Person ownerOfBook;



}
