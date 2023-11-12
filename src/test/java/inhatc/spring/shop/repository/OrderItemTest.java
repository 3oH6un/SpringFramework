package inhatc.spring.shop.repository;

import inhatc.spring.shop.entity.Order;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderItemTest {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = new Order();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

    }
}
