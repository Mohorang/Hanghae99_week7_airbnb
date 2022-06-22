package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select this_.category from Category this_ where this_.category in :categories")
    public Optional<List<String>> findByCategories(@Param("categories") List<String> categories);

    @Query("select this_.id from Category this_  where this_.id in:idlist")
    public Integer findByIdList(@Param("idlist") Integer idlist);
}
