package inhatc.spring.shop.dto;

import inhatc.spring.shop.constant.ItemSellStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long id;

    private String itemNm;

    private int price;

    private int stockNumber;

    private String itemDetail;

    private String itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


}
