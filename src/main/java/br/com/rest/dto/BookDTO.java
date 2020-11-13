package br.com.rest.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @Positive
    @NotNull
    private Integer isbn;
}
