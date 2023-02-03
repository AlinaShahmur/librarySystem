package com.alina.library.entity;
import java.time.LocalDate;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visitor")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NonNull
    @Column(name = "first_name", nullable =false)
    private String firstName;

    @NotBlank
    @NonNull
    @Column(name = "last_name", nullable =false)
    private String lastName;


    @Past(message = "The birth date must be in the past")
    @NonNull
    @Column(name = "birth_date", nullable =false)
    private LocalDate birthDate;

    @JsonIgnore
    @OneToMany(mappedBy = "visitor")
    Set<IssuedBook> issuedBooks;


    @Override
    public boolean equals(Object arg0) {
        if (arg0 == this) return true;
        if (!(arg0 instanceof Book)) return false;
        Visitor visitor = (Visitor)arg0;
        return (this.getFirstName().equals(visitor.getFirstName()) && 
                this.getId() == visitor.getId() &&
                this.getLastName().equals(visitor.getLastName()) &&
                this.getBirthDate() == visitor.getBirthDate()
        );
    }
}
