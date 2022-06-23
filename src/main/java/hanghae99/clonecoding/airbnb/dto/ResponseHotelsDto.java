package hanghae99.clonecoding.airbnb.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHotelsDto {

    public List<MainPageHotelInfoDto> mainPageHotelInfoDtoList;
}
