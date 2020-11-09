package br.com.rest.service.impl;

import br.com.rest.dto.BookDTO;
import br.com.rest.model.Book;
import br.com.rest.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private ObjectMapper mapper;


    public BookServiceImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public Book save(Book book) {
//        book.setId(1);
        return book;
    }
}
