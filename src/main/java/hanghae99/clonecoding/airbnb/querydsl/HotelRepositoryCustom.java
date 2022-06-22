package hanghae99.clonecoding.airbnb.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae99.clonecoding.airbnb.dto.MainPageHotelInfoDto;
import hanghae99.clonecoding.airbnb.dto.QMainPageHotelInfoDto;
import hanghae99.clonecoding.airbnb.dto.RequestHotelsDto;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hanghae99.clonecoding.airbnb.entity.QCategory.category1;
import static hanghae99.clonecoding.airbnb.entity.QFacility.facility;
import static hanghae99.clonecoding.airbnb.entity.QHotel.hotel;

@Repository
@RequiredArgsConstructor
public class HotelRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public List<MainPageHotelInfoDto> filteringHotels(RequestHotelsDto condition) {
        return jpaQueryFactory
                .select(new QMainPageHotelInfoDto(
                hotel.id.as("hotelId"),
                hotel.mainImage.as("mainImage"),
                hotel.images.as("images"),
                hotel.title.as("title"),
                hotel.defaultPrice.as("price"),
                hotel.score.as("score")
                ))
                .from(hotel)
//                .join(hotel.images)
                .join(hotel.categories, category1)
                .join(hotel.facilities, facility)
                .where(
                        category1.id.eq(condition.getCategory()),
                        hotel.defaultPrice.between(condition.getMaxPrice(), condition.getMinPrice()),
                        hotel.type.in(condition.getType()),
                        facility.id.in(condition.getFacilities()))
                .orderBy(hotel.id.desc())
                .fetch();
    }
}
