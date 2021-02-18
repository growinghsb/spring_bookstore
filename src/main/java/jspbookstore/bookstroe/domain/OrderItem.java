package jspbookstore.bookstroe.domain;

import jspbookstore.bookstroe.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
/**
 * 아래 어노테이션은 new 인스턴스 생성을 막는 것이다.
 * 기본생성자를 하나 protected 접근제어자로 생성 한 것과 마찬가지이다
 * 이렇게 생성을 제한하는 이유는 아래 생성하는 method가 있기 때문에
 * 생성을 획일화 하기 위해서이다.
 * 이렇게 코드를 제한하는 방식으로 코딩을 하는게
 * 추후 유지보수 측면에서 좋다.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    // 생성 메서드

    /**
     * 클래스로 바로 접근해서 사용할 수 있게 하려고
     * static을 사용해 스태틱 영역에 해당 메서드가 등록될 수 있도록 했다.
     * 이렇게 하면 Order 객체를 만들지 않고도 클래스 명만으로 바로 참조가 가능하다.
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
    // 비즈니스 로직
    public void cancel() {
        getItem().addStock(count);
    }

    // 조회 로직
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
