package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.Facility;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FacilityTypeDto {
    private String type;
    private List<FacilityDto> data = new ArrayList<>();

    public void addFacility(Facility facility) {
        data.add(new FacilityDto(facility));
    }

    public FacilityTypeDto(String type) {
        this.type = type;
    }
}
