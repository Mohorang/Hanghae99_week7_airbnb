package hanghae99.clonecoding.airbnb.controller;


import hanghae99.clonecoding.airbnb.dto.ResponseHotelDetailDto;
import hanghae99.clonecoding.airbnb.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler
//    public

    // TODO: 2022/06/18
    // IllegalArgumentException 처리 handler
    // code : Cxxx -> C = Common
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public static Map<String, String>handle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("code", "C001");
        errorAttributes.put("message", e.getMessage());
        return errorAttributes;
    }

    // TODO: 2022/06/18
    // 숙소 상세 조회 API
    // IllegalArgumentException 처리 필요
    // ExceptionHandler 로 처리
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hotel/{id}")
    public ResponseHotelDetailDto searchHotelDetail(@PathVariable long id) {
        return hotelService.searchHotelDetail(id);
    }

    // PrincipalDetails : 임시 인가받은 유저 class

}