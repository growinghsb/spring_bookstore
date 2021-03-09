package jspbookstore.bookstroe.controller;

import jspbookstore.bookstroe.domain.Cart;
import jspbookstore.bookstroe.domain.item.Book;
import jspbookstore.bookstroe.domain.item.Item;
import jspbookstore.bookstroe.service.CartService;
import jspbookstore.bookstroe.service.ItemService;
import jspbookstore.bookstroe.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("cart")
    public String cart(Model model) {
        model.addAttribute("carts", cartService.findAll());
        return "cart/cart";
    }

    @PostMapping("cart")
    public String cart(@ModelAttribute("bookForm") BookForm bookForm, @RequestParam("memberId") Long id) {
        cartService.insert(id, bookForm.getId(), bookForm.getStockQuantity());
        return "redirect:/cart";
    }

    @GetMapping("items/{id}/insert")
    public String insertItemForm(@PathVariable Long id, Model model) {
        Book book = (Book) itemService.findOne(id);
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setAuthor(book.getAuthor());
        form.setPrice(book.getPrice());
        form.setName(book.getName());
        form.setStockQuantity(book.getStockQuantity());
        model.addAttribute("bookForm", form);
        model.addAttribute("members", memberService.findMembers());
        return "cart/itemInsert";
    }

    @GetMapping("cart/remove")
    public String remove(@RequestParam("id") Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

}
