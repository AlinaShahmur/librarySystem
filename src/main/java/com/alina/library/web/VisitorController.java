package com.alina.library.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alina.library.entity.Visitor;
import com.alina.library.service.VisitorService;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/visitor")
public class VisitorController {
    VisitorService visitorService;
    
    @GetMapping("/all")
    public ResponseEntity<List<Visitor>> getVisitors() {
        return new ResponseEntity<>(visitorService.getVisitors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitor(@PathVariable Long id) {
        return new ResponseEntity<>(visitorService.getVisitor(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Visitor> addVisitor(@Valid @RequestBody Visitor visitor) {
        return new ResponseEntity<>(visitorService.saveVisitor(visitor), HttpStatus.CREATED);
    }
}
