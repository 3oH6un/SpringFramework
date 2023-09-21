package inhatc.spring.shop.repository;

import inhatc.spring.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository  extends JpaRepository<Item, Long> {

    List<Item> findByItemNm(String itemNm);     // 해당 이름에 대한 상품 리스트 가져오기

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("SELECT i FROM Item i WHERE i.itemDetail LIKE %:itemDetail% ORDER BY i.price DESC")
    List<Item> findByDetail(@Param("itemDetail") String itemDetail);                // JPQL

    @Query(value = "SELECT * FROM Item WHERE item_detail LIKE %:itemDetail% ORDER BY price ASC", nativeQuery = true)
    List<Item> findByDetailNative(@Param("itemDetail") String itemDetail);                // Native

    /**
     * Report
     * 아래 조건에 맞게 테스트 코드를 작성하고 결과를 캡쳐해서 제출 하세요.
     * 조건 : 재고량 -> 160개 이상 이면서 상품명에 5가 들어 있는 내용을 추출하시오.
     * 1.  쿼리 메소드 (재고량과 이름으로 검색)
     * 2.  JPQL 이용해서 위에 조건
     * 3.  Native 로 위에 조건
     * 4.  querydsl로 위에 조건
     */
    // 1.  쿼리 메소드 (재고량과 이름으로 검색)
    List<Item> findByStockNumberGreaterThanEqualAndItemNmContaining(Integer stockNumber, String itemNm);

    // 2.  JPQL 이용해서 위에 조건
    @Query("SELECT i FROM Item i WHERE i.stockNumber >= %:stockNumber% AND i.itemNm LIKE %:itemNm%")
    List<Item> findByStockAndName(@Param("stockNumber") Integer stockNumber, @Param("itemNm") String itemNm);

    // 3.  Native 로 위에 조건
    @Query(value = "SELECT * FROM Item WHERE stock_number >= %:stockNumber% AND item_nm LIKE %:itemNm%", nativeQuery = true)
    List<Item> findByStockAndNameNative(@Param("stockNumber") Integer stockNumber, @Param("itemNm") String itemNm);

    // 4.  querydsl로 위에 조건

}
