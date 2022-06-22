package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BedRepository extends JpaRepository<Bed, Integer> {
    @Query("Select this_.bedType from Bed this_ where this_.bedType in :bedTypes")
    public Optional<List<String>> findByBedTypes(@Param("bedTypes") List<String> bedTypes);

    @Query("select this_.id from Bed this_  where this_.bedType in:bedType")
    public Integer findByBedType(@Param("bedType") String bedType);
}
