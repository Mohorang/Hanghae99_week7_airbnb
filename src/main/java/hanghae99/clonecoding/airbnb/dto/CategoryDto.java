package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CategoryDto {
    private int id;
    private String category;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
    }
}
