package br.com.rest.service.impl;

import br.com.rest.exceptions.BusinessException;
import br.com.rest.model.Book;
import br.com.rest.repository.BookRepository;
import br.com.rest.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private ObjectMapper mapper;

    private BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
        mapper = new ObjectMapper();
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn()))
            throw new BusinessException("Isbn já cadastrado","Isbn já foi encontrado no nosso banco de dados");
        return repository.save(book);
    }
}
