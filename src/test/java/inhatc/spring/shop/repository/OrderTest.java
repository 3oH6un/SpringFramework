package inhatc.spring.shop.repository;

import inhatc.spring.shop.entity.Item;
import inhatc.spring.shop.entity.Order;
import inhatc.spring.shop.entity.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderTest {

    @Autowired
    private ItemRepository itemRepository;

    private Item createItem() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setStockNumber(100);
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {

        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
//            OrderItem
        }
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {

    }
}
