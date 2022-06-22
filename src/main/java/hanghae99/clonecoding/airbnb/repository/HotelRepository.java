package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("Select this_ from Hotel this_ join this_.categories c where c.id = :category and this_.defaultPrice between :maxPrice and :minPrice")
    public List<Hotel> searchAllHotels(@Param("category") int category, @Param("maxPrice") int maxPrice, @Param("minPrice") int minPrice);
}