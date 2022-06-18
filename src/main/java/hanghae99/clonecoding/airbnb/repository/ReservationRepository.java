package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    /*
        사용 예시

        Object[] date = reserRepo.test(3).get(0);
        System.out.println(date[0]); // team1의 체크아웃 날짜
        System.out.println(date[1]); // 다음팀의 체크 인 날짜
    */
    // mysql
    @Query( nativeQuery = true,
            value = "Select t1.check_out_date,t2.check_in_date from airbnb.reservation t1 left join airbnb.reservation t2 on t2.check_in_date = (select min(this.check_in_date) from airbnb.reservation this where this.check_in_date > t1.check_out_date and this.hotel_id = t1.hotel_id) where t1.hotel_id=:hotel and :date+2 < DATEDIFF(t2.check_in_date,t1.check_out_date) limit 1")
    public List<Date[]> findAbleReservationDate(@Param("hotel")int hotelid,@Param("date") int date);
    // h2 database
//    @Query(nativeQuery = true,
//    value = "SELECT T1.CHECK_OUT_DATE,T2.CHECK_IN_DATE FROM RESERVATION T1 LEFT JOIN RESERVATION T2 ON T2.CHECK_IN_DATE = (SELECT MIN(THIS.CHECK_IN_DATE) FROM RESERVATION THIS WHERE THIS.CHECK_IN_DATE > T1.CHECK_OUT_DATE AND THIS.HOTEL_ID = T1.HOTEL_ID) WHERE T1.HOTEL_ID=:hotel AND :date+2 < DATEDIFF(DAY,T2.CHECK_IN_DATE,T1.CHECK_OUT_DATE) LIMIT 1")
//    public List<Date[]> findAbleReservationDate(@Param("hotel")int hotelid,@Param("date") int date);
}
