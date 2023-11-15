package inhatc.spring.shop.dto;

import inhatc.spring.shop.entity.ItemImg;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImgDto {

    private Long id;
    private String imgName;             // 저장된 파일명
    private String oriImgName;          // 원본 파일명
    private String imgUrl;              // 이미지 URL
    private String repImgYn;            // 대표 이미지 여부

    @Builder
    public ItemImgDto(Long id, String imgName, String oriImgName, String imgUrl, String repImgYn) {

        this.id = id;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {

        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
