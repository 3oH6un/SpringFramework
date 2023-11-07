package inhatc.spring.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inhatc.spring.shop.constant.ItemSellStatus;
import inhatc.spring.shop.entity.Item;
import inhatc.spring.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ReportItemRepositoryTest {

    int quantity = 160;
    String filterString = "5";

    @Autowired
    ReportItemRepository reportItemRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void createData(){
        for(int i = 1; i <=100; i++){
            Item item = Item.builder()
                .itemName("테스트 상품" + i)
                .price(10000 + i)
                .stockNumber(100 + i)
                .itemDetail("테스트 상품 상세 설명" + i)
                .itemSellStatus(ItemSellStatus.SELL)
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

            reportItemRepository.save(item);
        }
    }

    @Test
    @DisplayName("쿼리 메소드 테스트")
    void queryMethodTest() {
        reportItemRepository
                .findByStockNumberGreaterThanEqualAndItemNameContaining(quantity, filterString)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("JPQL 테스트")
    void jpqlTest(){
        reportItemRepository.findByStockNumberAndItemName(quantity, filterString)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("JPQL Native 테스트")
    void jpqlNativeTest(){
        reportItemRepository.findByStockNumberAndItemNameNative(quantity, filterString)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("QueryDSL 테스트")
    void queryDslTest(){

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        queryFactory.selectFrom(QItem.item)
                .where(QItem.item.stockNumber.goe(quantity)
                        .and(QItem.item.itemName.like("%" + filterString + "%")))
                .fetch()
                .forEach(System.out::println);
    }

}
