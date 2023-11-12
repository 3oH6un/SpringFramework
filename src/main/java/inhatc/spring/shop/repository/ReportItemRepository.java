package inhatc.spring.shop.repository;

import inhatc.spring.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportItemRepository extends JpaRepository<Item, Long> {

    // 쿼리 메소드
    List<Item> findByStockNumberGreaterThanEqualAndItemNameContaining(int stockNumber, String itemName);

    // JPQL
    @Query("select i from Item i where i.stockNumber >= :stockNumber and i.itemName like %:itemName%")
    List<Item> findByStockNumberAndItemName(
            @Param("stockNumber") int stockNumber, @Param("itemName") String itemName
    );

    // JPQL Native
    @Query(value = "select * from item i where i.stock_number >= :stockNumber and i.item_name like %:itemName%", nativeQuery = true)
    List<Item> findByStockNumberAndItemNameNative(
            @Param("stockNumber") int stockNumber, @Param("itemName") String itemName
    );
}
