package inhatc.spring.shop.repository;

import inhatc.spring.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository  extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    List<Item> findByItemName(String itemName);     // 해당 이름에 대한 상품 리스트 가져오기

    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("SELECT i FROM Item i WHERE i.itemDetail LIKE %:itemDetail% ORDER BY i.price DESC")
    List<Item> findByDetail(@Param("itemDetail") String itemDetail);                // JPQL

    @Query(value = "SELECT * FROM Item WHERE item_detail LIKE %:itemDetail% ORDER BY price DESC", nativeQuery = true)
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
    List<Item> findByStockNumberGreaterThanEqualAndItemNameContaining(Integer stockNumber, String itemName);

    // 2.  JPQL 이용해서 위에 조건
    @Query("SELECT i FROM Item i WHERE i.stockNumber >= :stockNumber AND i.itemName LIKE %:itemName%")
    List<Item> findByStockAndName(@Param("stockNumber") Integer stockNumber, @Param("itemName") String itemName);
    // 문자열을 받아올 때는 %:string%, 숫자를 받아올 때는 :number로 사용한다.

    // 3.  Native 로 위에 조건
    @Query(value = "SELECT * FROM Item i WHERE i.stock_number >= :stockNumber AND i.item_nm LIKE %:itemName%", nativeQuery = true)
    List<Item> findByStockAndNameNative(@Param("stockNumber") Integer stockNumber, @Param("itemName") String itemName);

    // 4.  querydsl로 위에 조건

}
