package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.BedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedRoomRepository extends JpaRepository<BedRoom, Integer> {
}
