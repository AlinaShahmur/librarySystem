package com.alina.library.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alina.library.entity.Visitor;


public interface VisitorRepository extends CrudRepository<Visitor, Long> {
    @Query(value = "DELETE FROM visitor; DBCC CHECKIDENT (visitor, RESEED, 0)", nativeQuery=true)
    @Modifying
    @Transactional
    void deleteAllWithResetIds();
}
