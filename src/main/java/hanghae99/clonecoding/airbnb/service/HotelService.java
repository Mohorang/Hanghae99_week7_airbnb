package hanghae99.clonecoding.airbnb.service;


import hanghae99.clonecoding.airbnb.dto.ResponseHotelDetailDto;
import hanghae99.clonecoding.airbnb.entity.Category;
import hanghae99.clonecoding.airbnb.entity.Facility;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import hanghae99.clonecoding.airbnb.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepo;

    public ResponseHotelDetailDto searchHotelDetail(long id) {
        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not Find the Registered hotel"));
        List<Integer> facilities = new ArrayList<>();
        List<Integer> categories = new ArrayList<>();

        for (Facility facility : hotel.getFacilities()) {
            facilities.add(facility.getType());
        }

        for (Category category : hotel.getCategories()) {
            categories.add(category.getId());
        }

        return ResponseHotelDetailDto.from(hotel, facilities, categories);
    }
}
