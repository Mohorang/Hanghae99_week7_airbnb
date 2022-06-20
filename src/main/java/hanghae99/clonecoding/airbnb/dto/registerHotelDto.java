package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.Bed;
import hanghae99.clonecoding.airbnb.entity.BedRoom;
import hanghae99.clonecoding.airbnb.entity.Category;
import hanghae99.clonecoding.airbnb.entity.Facility;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class registerHotelDto {
    private String title;
    private String address;
    private String description;

    private int type;

//    private List<BedRoom> bedRoom = new ArrayList<>();
    //private List<Integer> facilities = new ArrayList<>();
    //private List<Integer> categories = new ArrayList<>();

    private String traffic;
    private String region;
    private int maxGuest;
    private int minGuest;
    private int minDate;
    private int maxDate;

    private int defaultPrice;
    private int cleanPrice;
    private int servicePrice;
    private int checkInTime;
    private int checkOutTime;

    private double score;


}
