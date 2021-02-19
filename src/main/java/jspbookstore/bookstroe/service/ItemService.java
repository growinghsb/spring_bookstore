package jspbookstore.bookstroe.service;

import jspbookstore.bookstroe.controller.BookForm;
import jspbookstore.bookstroe.domain.item.Book;
import jspbookstore.bookstroe.domain.item.Item;
import jspbookstore.bookstroe.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, BookForm form) {
        Book book = (Book)itemRepository.findOne(itemId);
        book.setName(form.getName());
        book.setIsbn(form.getIsbn());
        book.setAuthor(form.getAuthor());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

}
