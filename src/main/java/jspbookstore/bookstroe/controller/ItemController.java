package jspbookstore.bookstroe.controller;

import jspbookstore.bookstroe.domain.item.Book;
import jspbookstore.bookstroe.domain.item.Item;
import jspbookstore.bookstroe.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String inputForm(BookForm bookForm, BindingResult result) {

        /**
         * setter는 최대한 사용하지 않는 방향으로 설계하라
         * createBook이라는 비즈니스 로직을 만들어서
         * 한 번에 parameter를 넘겨서 생성하라.
         * 이 때 기본 생성자는 protected로 막아 놓고
         * 꼭 생성할 땐 비즈니스 로직을 사용하게 하라.
         */
        Book book = Book.createBook(bookForm.getName(), bookForm.getPrice(),
                bookForm.getStockQuantity(), bookForm.getAuthor(), bookForm.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String ItemList(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }
}
