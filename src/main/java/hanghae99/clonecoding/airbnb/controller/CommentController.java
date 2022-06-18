package hanghae99.clonecoding.airbnb.controller;

import hanghae99.clonecoding.airbnb.dto.CommentRequestDto;
import hanghae99.clonecoding.airbnb.security.MemberDetail;
import hanghae99.clonecoding.airbnb.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // TODO: 2022/06/18
    // IllegalArgumentException 처리 handler
    // code : C00
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public static Map<String, String> handle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("code", "C001");
        errorAttributes.put("message", e.getMessage());
        return errorAttributes;
    }

    // TODO: 2022/06/18
    // 예약한 숙소 페이지에 comment 등록 API
    // ExceptionHandler 로 처리
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hotel/{hotel_id}/comment")
    public void registerComment(@AuthenticationPrincipal MemberDetail memberDetail
            , @PathVariable Long hotel_id
            , @RequestBody CommentRequestDto commentRequestDto) {
        commentService.registerComment(hotel_id, memberDetail.getMember(), commentRequestDto);
    }

    //
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/hotel/{hotel_id}/comment/{comment_id}")
    public void deleteComment(@PathVariable long hotel_id
            , @PathVariable long comment_id
            , @AuthenticationPrincipal MemberDetail memberDetail) {
        commentService.deleteComment(hotel_id, comment_id, memberDetail.getMember());
    }
}
