package hanghae99.clonecoding.airbnb.controller;


import hanghae99.clonecoding.airbnb.dto.registerHotelDto;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import hanghae99.clonecoding.airbnb.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;


    //숙소 등록
    @PostMapping("/hotel")
    public void registerHotel(@RequestPart(value = "mainImage") MultipartFile mainImage,
                              @RequestPart(value = "images") List<MultipartFile> images,
                              @RequestBody registerHotelDto dto){
        service.registerHotel(mainImage,images,dto);
    }

    //숙소 수정
    @PutMapping("/hotel/{id}")
    public ResponseEntity modifyHotel(@RequestPart(value = "mainImage", required = false) MultipartFile mainImage,
                                      @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                      @RequestBody registerHotelDto dto){
        service.modifyHotel(mainImage, images, dto);
        return new ResponseEntity(HttpStatus.OK);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
