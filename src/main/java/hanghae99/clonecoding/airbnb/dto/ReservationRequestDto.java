package hanghae99.clonecoding.airbnb.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

}
