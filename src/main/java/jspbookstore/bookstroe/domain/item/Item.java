package jspbookstore.bookstroe.domain.item;

import jspbookstore.bookstroe.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * 재고 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    // 이렇게 setter가 필요하면
    // 해당 클래스에 비즈니스 로직을 구현 하는 것이
    // 응집도를 높히면서 setter를 난무하지 않게 해준다.
    // Item이 제고를 감소시킨다고 생각하는 것이 아니라
    // Item이 제고에 대한 데이터를 가지고 있으니 여기서
    // 값이 변경 되는 것이 맞다.
}
