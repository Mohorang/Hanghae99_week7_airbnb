package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.Bed;
import hanghae99.clonecoding.airbnb.entity.Category;
import hanghae99.clonecoding.airbnb.entity.Facility;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class registerHotelDto {
    private String title;
    private String address;
    private String discript;
    private int type;

    private List<Bed> bed = new ArrayList<>();

    private List<Facility> facilities = new ArrayList<>();

    private List<Category> category = new ArrayList<>();



}
