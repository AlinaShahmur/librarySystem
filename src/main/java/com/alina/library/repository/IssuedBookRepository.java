package com.alina.library.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alina.library.entity.IssuedBook;

public interface IssuedBookRepository extends CrudRepository<IssuedBook, Long> {
    List<IssuedBook> findByVisitorId(Long visitorId);
    List<IssuedBook> findByBookId(Long bookId);
    @Query(value = "DELETE FROM visitor_book; DBCC CHECKIDENT (visitor_book, RESEED, 0)", nativeQuery=true)
    @Modifying
    @Transactional
    void deleteAllWithResetIds();
}
