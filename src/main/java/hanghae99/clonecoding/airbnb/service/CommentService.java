package hanghae99.clonecoding.airbnb.service;

import hanghae99.clonecoding.airbnb.dto.CommentRequestDto;
import hanghae99.clonecoding.airbnb.entity.Member;
import hanghae99.clonecoding.airbnb.repository.CommentRepository;
import hanghae99.clonecoding.airbnb.repository.HotelRepository;
import hanghae99.clonecoding.airbnb.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ReservationRepository reservationRepo;
    private final CommentRepository commentRepo;

    // TODO: 2022/06/18
    // 해당 숙소에 예약 기록이 있는 유저만 댓글을 달 수 있어야 한다.
    public void registerComment(Long id, Member member, CommentRequestDto commentRequestDto) { // id : 예약한 숙소의 게시글의 id
        int reservationCheck = reservationRepo.findByUserIdAndHotelId(member.getId(), id);
        if(reservationCheck == 0) throw new IllegalArgumentException("Not exist reservation information");
        else {
            commentRepo.save(commentRequestDto.ToEntity(member));
        }
    }

    public void deleteComment(long id, Member member) {
    }
}
