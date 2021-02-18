package jspbookstore.bookstroe.service;

import jspbookstore.bookstroe.domain.Address;
import jspbookstore.bookstroe.domain.Member;
import jspbookstore.bookstroe.domain.Order;
import jspbookstore.bookstroe.domain.OrderStatus;
import jspbookstore.bookstroe.domain.item.Book;
import jspbookstore.bookstroe.domain.item.Item;
import jspbookstore.bookstroe.exception.NotEnoughStockException;
import jspbookstore.bookstroe.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    // 주문
    @Test
    public void orderTest() {
        Member member = getMember("김승범", new Address("서울시 양천구", "목동로186", "08002"));

        Item book = getBook("객체지향적 설계에 대한 고찰", 20000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //than
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(getOrder.getOrderItems().size(), 1, "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(20000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 숫자가 줄어야 한다.");
    }


    //주문 취소
    @Test
    public void orderCancelTest() {
        Member member = getMember("윤승범", new Address("서울", "목동", "123123"));
        Item book = getBook("객체지향 사실과 오해", 10000, 10);
        int orderCount = 4;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, order.getStatus(), "취소된 orderId로 조회한 Order의 상태는 CANCEL이어야 한다.");
        assertEquals(10, book.getStockQuantity(), "취소된 상품의 개수는 원복되어야 한다.");

    }

    // 상품 주문 재고 수량 초과
    @Test
    public void orderOverTest() throws NotEnoughStockException {
        Member member = getMember("한승범", new Address("서울시 양천구", "목동로186", "08002"));
        Item book = getBook("스프링 프레임워크", 30000, 10);

        int orderCount = 11;

        //@Test(expected = 예외 클래스) 와 같은 효과다. 안에 있는 구문에서 예외가 나면 테스트 성공이다.
        //assertThrows 메서드의 매개변수로 지정한 예외와 같은 예외가 발생하면 테스트 성공이다.
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    private Item getBook(String title, int price, int quantity) {
        Item book = new Book();
        book.setName(title);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member getMember(String name, Address address) {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}
