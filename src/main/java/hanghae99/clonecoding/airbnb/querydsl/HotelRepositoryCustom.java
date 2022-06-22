package hanghae99.clonecoding.airbnb.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae99.clonecoding.airbnb.dto.MainPageHotelInfoDto;
import hanghae99.clonecoding.airbnb.dto.QMainPageHotelInfoDto;
import hanghae99.clonecoding.airbnb.dto.RequestHotelsDto;
import hanghae99.clonecoding.airbnb.entity.QFacility;
import hanghae99.clonecoding.airbnb.entity.QHotel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.jaasapi.JaasApiIntegrationFilter;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
                        hotel.title.as("title"),
                        hotel.defaultPrice.as("price"),
                        hotel.score.as("score")
                ))
                .from(hotel)
                .join(hotel.images)
                .join(hotel.categories, category1)
                .join(hotel.facilities, facility)
                .where(
                        eqCategory(condition.getCategory()),
                        betweenPrice(condition.getMinPrice(), condition.getMaxPrice()),
                        innerType(condition.getType()),
                        innerFacilities(condition.getFacilities())
                ).orderBy(hotel.id.desc())
                .fetch();
    }

    private BooleanExpression eqCategory(Integer category) {
        return category != null ? category1.id.eq(category) : null;
    }

    private BooleanExpression betweenPrice(Integer minPrice, Integer maxPrice) {
        if(maxPrice == null && minPrice == null) return null;
        return QHotel.hotel.defaultPrice.between(minPrice, maxPrice);
    }

    private BooleanExpression innerType(List<Integer> type) {
        return type.size() != 0 && !type.contains(null) ? hotel.type.in(type) : null;
    }

    private BooleanExpression innerFacilities(List<Integer> facilities) {
        return facilities.size() != 0 && !facilities.contains(null) ? facility.id.in(facilities) : null;
    }
}
