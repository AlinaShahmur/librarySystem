package com.alina.library.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Author cannot be blank")
    @NonNull
    @Column(name = "author", nullable = false)
    private String author;


    @Column(name = "is_avaliable", nullable = false)
    private boolean isAvaliable;


    @JsonIgnore
    @OneToMany(mappedBy = "book")
    Set<IssuedBook> issuedBooks;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 == this) return true;
        if (!(arg0 instanceof Book)) return false;
        Book book = (Book)arg0;
        return (this.getAuthor().equals(book.getAuthor()) && 
                this.getId() == book.getId() &&
                this.getTitle().equals(book.getTitle())
        );
    }
}
