package com.alina.library.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alina.library.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Query(value = "DELETE FROM book; DBCC CHECKIDENT (book, RESEED, 0)", nativeQuery=true)
    @Modifying
    @Transactional
    void deleteAllWithResetIds();
}
