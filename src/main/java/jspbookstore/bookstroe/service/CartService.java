package jspbookstore.bookstroe.service;

import jspbookstore.bookstroe.domain.Cart;
import jspbookstore.bookstroe.domain.Member;
import jspbookstore.bookstroe.domain.item.Item;
import jspbookstore.bookstroe.repository.CartRepository;
import jspbookstore.bookstroe.repository.ItemRepository;
import jspbookstore.bookstroe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long insert(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Cart cart = Cart.createCart(member, item, count);

        return cartRepository.save(cart);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findOne(Long id) {
        return cartRepository.findOne(id);
    }

    public void remove(Long id) {
        Cart cart = cartRepository.findOne(id);
        cartRepository.remove(cart);

    }
}
