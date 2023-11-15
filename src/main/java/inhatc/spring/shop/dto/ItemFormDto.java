package inhatc.spring.shop.dto;

import inhatc.spring.shop.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 항목 입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 항목 입니다.")
    private int price;

    @NotNull(message = "수량은 필수 항목 입니다.")
    private int stockNumber;

    // Lob -> database랑 관련된 애, 자료형 타입
    @NotBlank(message = "상품 상세 설명은 필수 항목 입니다.")
    private String itemDetail;

    private String itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();

    // 모델 매핑 시 이름이 겹치는 내용만 매핑한다.
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {

        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {

        return modelMapper.map(item, ItemFormDto.class);
    }
}
