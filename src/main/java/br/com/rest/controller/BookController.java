package br.com.rest.controller;

import br.com.rest.dto.BookDTO;
import br.com.rest.model.Book;
import br.com.rest.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO dto){
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = objectMapper.convertValue(dto, Book.class);
        Book novo = service.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.convertValue(novo, BookDTO.class));
    }
}
