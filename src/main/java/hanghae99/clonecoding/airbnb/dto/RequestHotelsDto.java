package hanghae99.clonecoding.airbnb.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestHotelsDto {

    @NotNull
    private Integer category;

    private Integer maxPrice;
    private Integer minPrice;
    private List<Integer> type;
    private List<Integer> facilities;
}
