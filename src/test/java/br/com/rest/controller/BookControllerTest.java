package br.com.rest.controller;

import br.com.rest.dto.BookDTO;
import br.com.rest.model.Book;
import br.com.rest.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    public MockMvc mock;
    @MockBean
    public BookService service;
    private String URL = "/book";
    private BookDTO dto;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();

        dto = BookDTO.builder()
                .title("Meu Livro")
                .author("Autor")
                .isbn(1)
                .build();
    }

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    public void createBookTest() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        Book book = objectMapper.convertValue(dto, Book.class);
        book.setId(1);

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .accept(MediaType.APPLICATION_JSON)
                                                                    .content(json);
        mock.perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("title").value(dto.getTitle()))
            .andExpect(jsonPath("author").value(dto.getAuthor()))
            .andExpect(jsonPath("isbn").value(dto.getIsbn()));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para a criação do livro")
    public void createWithInvalidArguments(){

    }
}
