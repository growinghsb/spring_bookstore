package jspbookstore.bookstroe.service;

import jspbookstore.bookstroe.domain.Delivery;
import jspbookstore.bookstroe.domain.Member;
import jspbookstore.bookstroe.domain.Order;
import jspbookstore.bookstroe.domain.OrderItem;
import jspbookstore.bookstroe.domain.item.Item;
import jspbookstore.bookstroe.repository.ItemRepository;
import jspbookstore.bookstroe.repository.MemberRepository;
import jspbookstore.bookstroe.repository.OrderRepository;
import jspbookstore.bookstroe.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 생성
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //log.error("item = " + item + " itemId" + item.getId());

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
