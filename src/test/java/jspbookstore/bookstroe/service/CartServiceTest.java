package jspbookstore.bookstroe.service;

import jspbookstore.bookstroe.domain.Address;
import jspbookstore.bookstroe.domain.Cart;
import jspbookstore.bookstroe.domain.Member;
import jspbookstore.bookstroe.domain.item.Book;
import jspbookstore.bookstroe.domain.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    EntityManager em;

    int insertCount = 4;


    @Test
    public void 장바구니_저장() {
        //given (~ 가정했을 때)
        Member member = getMember("승범 한", new Address("서울시", "양천구", "08002"));

        Item item = getItem("스프링 책", 10000, 100, "프링", "14162354123");

        //when (~ 할 때), then (그렇다면)
        Long cartId = cartService.insert(member.getId(), item.getId(), insertCount);
        Cart cart = cartService.findOne(cartId);

        assertNotNull(cart);
        assertEquals(cartId, cart.getId(), "이 둘이 같아야 한다.");
    }



    @Test
    public void 장바구니_전체조회() {
        //given (~ 가정했을 때)
        Member member = getMember("김김범", new Address("강원시", "강릉로", "15622"));
        Item item = getItem("jpa", 10000, 100, "jj", "46412635412");
        Item item2 = getItem("jpa", 10000, 100, "jj", "46412635412");
        Item item3 = getItem("jpa", 10000, 100, "jj", "46412635412");
        Item item4 = getItem("jpa", 10000, 100, "jj", "46412635412");

        //when (~ 할 때), then (그렇다면)
        cartService.insert(member.getId(), item.getId(), insertCount);
        cartService.insert(member.getId(), item2.getId(), insertCount);
        cartService.insert(member.getId(), item3.getId(), insertCount);
        cartService.insert(member.getId(), item4.getId(), insertCount);
        List<Cart> carts = cartService.findAll();

        assertEquals(4, carts.size());
    }
    
    @Test
    public void 장바구나_내역_삭제() {
        //given (~ 가정했을 때)
        Member member = getMember("김김범", new Address("강원시", "강릉로", "15622"));
        Item item = getItem("jpa", 10000, 100, "jj", "46412635412");

        Long cartId = cartService.insert(member.getId(), item.getId(), insertCount);

        //when (~ 할 때), then (그렇다면)
        cartService.remove(cartId);
        assertEquals(0, cartService.findAll().size());

    }


    private Item getItem(String title, int price, int stockQuantity, String author, String isbn) {
        Item item = Book.createBook(title, price, stockQuantity, author, isbn);
        em.persist(item);
        return item;
    }

    private Member getMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}