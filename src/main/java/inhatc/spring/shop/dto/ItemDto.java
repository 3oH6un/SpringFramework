package inhatc.spring.shop.dto;

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
