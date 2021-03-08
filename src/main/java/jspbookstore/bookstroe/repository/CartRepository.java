package jspbookstore.bookstroe.repository;

import jspbookstore.bookstroe.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    public Long save(Cart cart) {
        em.persist(cart);
        return cart.getId();
    }

    public List<Cart> findAll() {
        return em.createQuery("select c from Cart c", Cart.class)
                .getResultList();
    }

    public Cart findOne(Long id) {
        return em.find(Cart.class, id);
    }

    public void remove(Cart cart){
        em.remove(cart);
    }
}
