package hanghae99.clonecoding.airbnb.dto;

import hanghae99.clonecoding.airbnb.entity.Comment;
import hanghae99.clonecoding.airbnb.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    private String comment;
    private double score;


    public Comment ToEntity(Member member) {
        return Comment.builder()
                .comment(this.comment)
                .member(member)
                .score(this.score)
                .build();
    }
}
