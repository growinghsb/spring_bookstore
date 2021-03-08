package jspbookstore.bookstroe.domain;

import jspbookstore.bookstroe.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    public static Cart createCart(Member member, Item item, int count) {
        Cart cart = new Cart();
        cart.member = member;
        cart.item = item;
        cart.count = count;
        return cart;
    }
}
