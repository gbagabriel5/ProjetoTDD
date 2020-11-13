package br.com.rest.service;

import br.com.rest.exceptions.BusinessException;
import br.com.rest.model.Book;
import br.com.rest.repository.BookRepository;
import br.com.rest.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    BookRepository repository;

    Book book;

    @BeforeEach
    public void setUp(){
        this.service = new BookServiceImpl(repository);
        this.book = Book.builder().title("Meu livro").author("Gui").isbn(123).build();
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBook(){
        Mockito.when(repository.existsByIsbn(Mockito.anyInt())).thenReturn(false);
        Mockito.when(service.save(book)).thenReturn(Book.builder().id(1).title("Meu livro").author("Gui").isbn(123).build());

        Book saved = service.save(book);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Meu livro");
        assertThat(saved.getAuthor()).isEqualTo("Gui");
        assertThat(saved.getIsbn()).isEqualTo(123);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveABookWithDuplicatedISBN(){

        Mockito.when(repository.existsByIsbn(Mockito.anyInt())).thenReturn(true);

        Throwable exception = catchThrowable(()-> service.save(book));

        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastrado","error");

        Mockito.verify(repository, Mockito.never()).save(book);
    }
}
