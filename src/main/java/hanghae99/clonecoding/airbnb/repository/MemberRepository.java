package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    @Query("Select this_ from Member this_ where this_.email=:email")
    public Member findByEmail(@Param("email") String email);

    @Query("Select count(this_) from Member this_ where this_.email=:email")
    public int checkEmail(@Param("email") String email);
}
