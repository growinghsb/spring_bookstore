package jspbookstore.bookstroe.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
public class BookForm {


    private Long id;

    @NotEmpty(message = "책 이름 입력은 필수 입니다.")
    private String name;

    @Positive(message = "가격을 입력해주세요.")
    private int price;

    @NotNull(message = "재고 수량 입력은 필수 입니다.")
    private int stockQuantity;

    private String author;
    private String isbn;
}
