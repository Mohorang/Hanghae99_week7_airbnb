package hanghae99.clonecoding.airbnb.dto;


import com.querydsl.core.annotations.QueryProjection;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import hanghae99.clonecoding.airbnb.entity.Images;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class MainPageHotelInfoDto {

    private Long id;
    private String mainImage;
    private List<String> images;
    private String title;
    private int price;  // 1 박당 defaultPrice 값
    private double score;

    @QueryProjection
    public MainPageHotelInfoDto(Long id, String mainImage, List<String> images, String title, int price, double score) {
        this.id = id;
        this.mainImage = mainImage;
        this.images = images;
        this.title = title;
        this.price = price;
        this.score = score;
    }

    @Builder
    public static MainPageHotelInfoDto of(Hotel hotel) {
        return MainPageHotelInfoDto.builder()
                .id(hotel.getId())
                .mainImage(hotel.getMainImage())
                .images(hotel.getImages())
                .title(hotel.getTitle())
                .price(hotel.getDefaultPrice())
                .score(hotel.getScore())
                .build();
    }
}
