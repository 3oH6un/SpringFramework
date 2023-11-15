package inhatc.spring.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inhatc.spring.shop.constant.ItemSellStatus;
import inhatc.spring.shop.entity.Item;
import inhatc.spring.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static inhatc.spring.shop.entity.QItem.item;

@SpringBootTest
// @Transactional
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    //    @PersistenceContext
    @Autowired
    private EntityManager em;

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = Item.builder()
                    .itemName("테스트 상품 " + i)
                    .price(10000 + i)
                    .stockNumber(100 + i)
                    .itemDetail("테스트 상품 상세 설명 " + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();

            itemRepository.save(item);
        }
    }

    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            itemRepository.save(item);
        }

        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            itemRepository.save(item);
        }
    }

    // 페이징 폼 미쳤다 ㄷㄷ
    @Test
    @DisplayName("querydsl 테스트 2")
    public void querydslTest2() {
        createItemList2();

        BooleanBuilder builder = new BooleanBuilder();
        String itemDetail = "테스트";
        int price = 10002;
        String itemSellStatus = "SELL";

        QItem item = QItem.item;

        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.gt(price));

        if (StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) {
            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);

        Page<Item> page = itemRepository.findAll(builder, pageable);
        List<Item> content = page.getContent();

        content.forEach(System.out::println);

    }

    @Test
    @DisplayName("querydsl 테스트")
    public void querydslTest() {
        createItemList();
        JPAQueryFactory query = new JPAQueryFactory(em);

        List<Item> itemList = query.selectFrom(item)
                .where(item.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(item.itemDetail.like("%" + "1" + "%"))
                .orderBy(item.price.desc())
                .fetch();

        itemList.forEach(item -> System.out.println("======================== querydsl 테스트 : " + item));

    }

    @Test
    @DisplayName("JPQL 테스트")
    public void findByDetailTest() {
        createItemList();
        itemRepository.findByDetail("1")
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("Native 테스트")
    public void findByDetailNativeTest() {
        createItemList();
        itemRepository.findByDetailNative("1")
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("OrderBy 테스트")
    public void findByPriceLessThanOrderByPriceDescTest() {
        createItemList();
        itemRepository.findByPriceLessThanOrderByPriceDesc(10005)
                .forEach((System.out::println));
    }

    @Test
    @DisplayName("OR 테스트")
    public void findByItemNmOrItemDetail() {
        createItemList();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품2", "테스트 상품 상세 설명8");
        itemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 이름 검색 테스트")
    public void findByItemNmTest() {
        createItemList();

        List<Item> itemList = itemRepository.findByItemName("테스트 상품 1");
        itemList.forEach(item -> System.out.println("=======검색 결과: " + item));
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void createItemTest() {
        Item item = Item.builder()
                .itemName("테스트 상품")
                .price(10000)
                .stockNumber(100)
                .itemDetail("테스트 상품 상세 설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .build();

        System.out.println("============================================= item = " + item);
        Item savedItem = itemRepository.save(item);
        System.out.println("============================================= savedItem = " + savedItem);
    }
}
