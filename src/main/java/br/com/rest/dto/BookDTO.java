package br.com.rest.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Integer id;
    private String title;
    private String author;
    private Integer isbn;
}
