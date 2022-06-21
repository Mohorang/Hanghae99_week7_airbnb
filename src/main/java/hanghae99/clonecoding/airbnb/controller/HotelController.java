package hanghae99.clonecoding.airbnb.controller;


import hanghae99.clonecoding.airbnb.dto.ResponseHotelDetailDto;
import hanghae99.clonecoding.airbnb.dto.registerHotelDto;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import hanghae99.clonecoding.airbnb.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService service;


    //숙소 등록
    @PostMapping("/hotel")
    public ResponseEntity registerHotel(@RequestPart(value = "mainImage") MultipartFile mainImage,
                              @RequestPart(value = "images") List<MultipartFile> images,
                              @RequestPart(value = "hotelData") registerHotelDto dto){
        service.registerHotel(mainImage,images,dto);
        return ResponseEntity.ok().body(HttpStatus.CREATED);
    }

    //숙소 수정
    @PutMapping("/hotel/{id}")
    public ResponseEntity modifyHotel(@PathVariable long id,
                                      @RequestPart(value = "mainImage", required = false) MultipartFile mainImage,
                                      @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                      @RequestPart(value = "hotelData") registerHotelDto dto){
        service.modifyHotel(id,mainImage, images, dto);
        return ResponseEntity.ok().body(HttpStatus.CREATED);
    }

    @DeleteMapping("/hotel/{id}")
    public void deleteHotel(@PathVariable long id){
        service.deleteHotel(id);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler
//    public

    // TODO: 2022/06/18
    // IllegalArgumentException 처리 handler
    // code : Cxxx -> C = Common
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
    // 숙소 상세 조회 API
    // IllegalArgumentException 처리 필요
    // ExceptionHandler 로 처리
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hotel/{id}")
    public ResponseHotelDetailDto searchHotelDetail(@PathVariable long id) {
        return service.searchHotelDetail(id);
    }

    // PrincipalDetails : 임시 인가받은 유저 class

}
