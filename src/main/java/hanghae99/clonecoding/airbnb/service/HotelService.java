package hanghae99.clonecoding.airbnb.service;

import hanghae99.clonecoding.airbnb.dto.registerHotelDto;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import hanghae99.clonecoding.airbnb.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepo;
    private final AwsS3Service s3Service;

    public void registerHotel(MultipartFile mainImage, List<MultipartFile> images, registerHotelDto dto){
        Map<String , String> mainImageResult = s3Service.uploadFile(mainImage);
        List<Map<String,String>> getImages = getImageList(images);

        List<String> imagesResult = new ArrayList<>(getImages.size());

        for (int i = 0; i < getImages.size(); i++) {
            imagesResult.add(getImages.get(i).get("url"));
        }
        Hotel hotel = new Hotel(mainImageResult.get("url") , imagesResult , dto);
        hotelRepo.save(hotel);
    }

    public void modifyHotel(MultipartFile mainImage , List<MultipartFile> images , registerHotelDto dto){
        //메인이미지
        Map<String , String> mainImageResult = new HashMap<>();

        //메인이미지 이외의 사진들
        List<Map<String,String>> getImages = new ArrayList<>();
        List<String> imagesResult = new ArrayList<>(images.size());


        if(mainImage != null){
            mainImageResult = s3Service.uploadFile(mainImage);
        }
        if(images != null){
            getImages = getImageList(images);
            for (int i = 0; i < getImages.size(); i++) {
                imagesResult.add(getImages.get(i).get("url"));
            }
        }

        Hotel hotel = new Hotel(mainImageResult.get("url") , imagesResult , dto);
    }

    //이미지 리스트를 받아서 저장하기
    public List<Map<String,String>> getImageList(List<MultipartFile> images){
        List<Map<String,String>> imagesResult = new ArrayList<>();
        Map<String , String> mapImageResult = new HashMap<>();

        for (int i = 0; i < images.size(); i++) {
            //imageResult.add(s3service.uploadFile(images.get(i)));
            mapImageResult = s3Service.uploadFile(images.get(i));
            imagesResult.add(mapImageResult);
            System.out.println(imagesResult.get(i).get("url"));
        }
        return imagesResult;

    }
}
