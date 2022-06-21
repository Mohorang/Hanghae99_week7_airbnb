package hanghae99.clonecoding.airbnb.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
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
