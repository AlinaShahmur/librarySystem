package com.alina.library.service;

import org.springframework.stereotype.Service;

import com.alina.library.entity.Visitor;
import com.alina.library.exception.VisitorNotFoundException;
import com.alina.library.repository.VisitorRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VisitorService {
    VisitorRepository visitorRepository;
    public Visitor getVisitor(Long id) {
        return unwrapVisitor(visitorRepository.findById(id), id); 
    }

    public List<Visitor> getVisitors() {
        return (List<Visitor>)visitorRepository.findAll();
    }

    public Visitor saveVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    public void deleteVisitor(Long id) {
        visitorRepository.deleteById(id);
    }

    public Visitor unwrapVisitor(Optional<Visitor> visitor, Long id) {
        if (visitor.isPresent()) {
            return visitor.get();
        } else {
            throw new VisitorNotFoundException(id);
        }
    }

}
