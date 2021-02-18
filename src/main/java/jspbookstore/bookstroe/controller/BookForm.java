package jspbookstore.bookstroe.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookForm {


    private Long id;

    @NotEmpty(message = "책 이름 입력은 필수 입니다.")
    private String name;

    @NotNull(message = "책 가격 입력은 필수 입니다.")
    private int price;

    @NotNull(message = "재고 수량 입력은 필수 입니다.")
    private int stockQuantity;

    private String author;
    private String isbn;
}
