package hanghae99.clonecoding.airbnb.service;

import hanghae99.clonecoding.airbnb.dto.CommentRequestDto;
import hanghae99.clonecoding.airbnb.entity.Comment;
import hanghae99.clonecoding.airbnb.entity.Member;
import hanghae99.clonecoding.airbnb.entity.Reservation;
import hanghae99.clonecoding.airbnb.repository.CommentRepository;
import hanghae99.clonecoding.airbnb.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final ReservationRepository reservationRepo;
    private final CommentRepository commentRepo;

    // TODO: 2022/06/18
    // 해당 숙소에 예약 기록이 있는 유저만 댓글을 달 수 있어야 한다.
    @Transactional
    public void registerComment(Long id, Member member, CommentRequestDto commentRequestDto) { // id : 예약한 숙소의 게시글의 id
        int reservationCheck = reservationRepo.cntMemberReservation(member.getId(), id);
        if (reservationCheck == 0) throw new IllegalArgumentException("Not exist reservation information");
        else {
            commentRepo.save(commentRequestDto.ToEntity(member));
        }
    }

    @Transactional
    public void deleteComment(long hotel_id, long comment_id, Member member) {
        // 예약한 이력이 없다면 당연히 등록한 comment 도 없다.
        int memberId = member.getId();
        Reservation memberReservation = reservationRepo.searchMemberReservation(member.getId(), hotel_id).orElse(null);
        if (memberReservation == null) throw new IllegalArgumentException("Not Find Your Comment");
        else {
            Comment comment = commentRepo.findByIdAndMemberId(comment_id, memberId).orElseThrow(
                    () -> new IllegalArgumentException("Not find your comment")
            );
            commentRepo.delete(comment);
        }
    }
}
