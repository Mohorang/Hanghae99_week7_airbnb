package hanghae99.clonecoding.airbnb.controller;


import hanghae99.clonecoding.airbnb.dto.registerHotelDto;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import hanghae99.clonecoding.airbnb.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
}
