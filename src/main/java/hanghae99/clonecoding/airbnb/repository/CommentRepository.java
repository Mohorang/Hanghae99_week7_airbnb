package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.id =:commentId and c.member.id =:memberId")
    public Optional<Comment> findByIdAndMemberId(@Param("commentId") long commentId, @Param("memberId") int memberId);
}
