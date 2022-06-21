package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.BedRoom;
import hanghae99.clonecoding.airbnb.entity.Comment;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseHotelDetailDto {

    private String title;
    private String address;
    private String description;
    private int type;
    private List<BedRoom> bedRooms;
    private List<String> images;
    private List<Integer> facilities;
    private List<Integer> categories;
    private String traffic;
    private String region;
    private int maxGuest;
    private int minGuest;
    private int minDate;
    private int maxDate;
    private int defaultPrice;
    private int cleanPrice;
    private int servicePrice;
    private int totalPrice;
    private String checkInTime;
    private String checkOutTime;
    private double score;
    private List<Comment> comments;



    public static ResponseHotelDetailDto from(Hotel hotel, List<Integer>facilities, List<Integer>categories){
        return ResponseHotelDetailDto.builder()
                .title(hotel.getTitle())
                .address(hotel.getAddress())
                .description(hotel.getDescription())
                .type(hotel.getType())
//                .bedRooms(hotel.getBedRooms())
                .images(hotel.getImages())
                .facilities(facilities)
                .categories(categories)
                .traffic(hotel.getTraffic())
                .region(hotel.getRegion())
                .maxGuest(hotel.getMaxGuest())
                .minGuest(hotel.getMinGuest())
                .minDate(hotel.getMinDate())
                .maxDate(hotel.getMaxDate())
                .defaultPrice(hotel.getDefaultPrice())
                .cleanPrice(hotel.getCleanPrice())
                .servicePrice(hotel.getServicePrice())
                .checkInTime(hotel.getCheckInTime())
                .checkOutTime(hotel.getCheckOutTime())
                .score(hotel.getScore())
                .comments(hotel.getComments())
                .build();
    }
}
