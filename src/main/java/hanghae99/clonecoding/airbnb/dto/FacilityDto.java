package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.Facility;
import lombok.Getter;

@Getter
public class FacilityDto {
    private int id;
    private String name;
    public FacilityDto(Facility facility){
        this.id = facility.getId();
        this.name = facility.getName();
    }
}
