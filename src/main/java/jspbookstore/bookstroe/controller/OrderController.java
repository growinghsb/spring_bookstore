package jspbookstore.bookstroe.controller;

import jspbookstore.bookstroe.domain.Member;
import jspbookstore.bookstroe.domain.Order;
import jspbookstore.bookstroe.domain.item.Item;
import jspbookstore.bookstroe.repository.OrderSearch;
import jspbookstore.bookstroe.service.CartService;
import jspbookstore.bookstroe.service.ItemService;
import jspbookstore.bookstroe.service.MemberService;
import jspbookstore.bookstroe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final CartService cartService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) { // 해당 코드는 @ModelAttrinbute로 바꿔 객체에 맵핑할 수 있다.
        // 매개변수를 일일이 받는 거 보다 오브젝트로 받는게 더 낫다. 자동 맵핑도 되고.
        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @PostMapping("/order/{memberId}/{itemId}/{count}/{cartId}")
    public String order(@PathVariable("memberId") Long memberId,
                        @PathVariable("count") int count,
                        @PathVariable("itemId") Long itemId,
                        @PathVariable("cartId") Long cartId
    ) { // 해당 코드는 @ModelAttrinbute로 바꿔 객체에 맵핑할 수 있다.
        // 매개변수를 일일이 받는 거 보다 오브젝트로 받는게 더 낫다. 자동 맵핑도 되고.

        orderService.order(memberId, itemId, count);
        cartService.remove(cartId);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
