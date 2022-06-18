package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
//    Optional<Reservation> findBy
    @Query("select count(r) from Reservation r where r.member.id = :memberId and r.hotel.id = :hotelId")
    int findByUserIdAndHotelId(int memberId, long hotelId);
}