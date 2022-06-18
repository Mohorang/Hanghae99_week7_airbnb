package hanghae99.clonecoding.airbnb.controller;


import hanghae99.clonecoding.airbnb.dto.ReservationRequestDto;
import hanghae99.clonecoding.airbnb.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hotel/reservation/{id}") // 예약할 숙소의 페이지 id
    public void makeReservation(@PathVariable Long id
            , @RequestBody ReservationRequestDto reservationRequestDto) {
//        reservationService.makeReservation(id, );
    }
}
