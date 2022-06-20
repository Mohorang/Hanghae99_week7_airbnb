package hanghae99.clonecoding.airbnb.service;

import hanghae99.clonecoding.airbnb.repository.HotelRepository;
import hanghae99.clonecoding.airbnb.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final HotelRepository hotelRepo;
    private final ReservationRepository reservationRepo;

}
