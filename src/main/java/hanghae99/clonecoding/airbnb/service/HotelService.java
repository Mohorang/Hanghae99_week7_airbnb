package hanghae99.clonecoding.airbnb.service;


import hanghae99.clonecoding.airbnb.dto.*;
import hanghae99.clonecoding.airbnb.entity.Category;
import hanghae99.clonecoding.airbnb.entity.Comment;
import hanghae99.clonecoding.airbnb.entity.Facility;
import hanghae99.clonecoding.airbnb.entity.Hotel;
import hanghae99.clonecoding.airbnb.querydsl.HotelRepositoryCustom;
import hanghae99.clonecoding.airbnb.repository.FacilityRepository;
import hanghae99.clonecoding.airbnb.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {
    private final HotelRepository hotelRepo;
    private final HotelRepositoryCustom hotelRepositoryCustom;
    private final AwsS3Service s3Service;

    @Transactional
    public void registerHotel(MultipartFile mainImage, List<MultipartFile> images, registerHotelDto dto) {
        Map<String, String> mainImageResult = s3Service.uploadFile(mainImage);


        //대표이미지 이외의 이미지에 들어갈 url
        List<Map<String, String>> getImages = getImageList(images);
        List<String> imagesUrl = new ArrayList<>(getImages.size());
        List<String> imagesFileName = new ArrayList<>(getImages.size());

        for (Map<String, String> getImage : getImages) {
            imagesUrl.add(getImage.get("url"));
            imagesFileName.add(getImage.get("fileName"));
        }

        //메인 이미지에 들어갈 이미지의 url
        String mainImageFileName = mainImageResult.get("fileName");
        String mainImageUrl = mainImageResult.get("url");

        Hotel hotel = new Hotel(mainImageUrl, mainImageFileName, imagesUrl, imagesFileName, dto);
        hotelRepo.save(hotel);
    }


    //메인이미지만 바꿀때 , 그 외 사진바꿀때  아니면 다바꿀때
    @Transactional
    public void modifyHotel(long id, MultipartFile mainImage, List<MultipartFile> images, registerHotelDto dto) {

        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 숙소입니다.")
        );

        //메인이미지
        Map<String, String> mainImageResult = new HashMap<>();

        //메인이미지 이외의 사진들
        List<Map<String, String>> getImages = new ArrayList<>();
        List<String> imagesUrl = new ArrayList<>(images.size());
        List<String> imagesFileName = new ArrayList<>();

        String mainImageUrl = "";

        //메인이미지의 수정여부 체크
        if (mainImage != null) {
            //기존 이미지 삭제 후
            s3Service.deleteFile(hotel.getMainImageFileName());
            //새로운 이미지 업로드
            mainImageResult = s3Service.uploadFile(mainImage);
            //새로운 이미지 url취득
            mainImageUrl = mainImageResult.get("url");
        }

        //메인 이미지 이외의 이미지들의 수정여부 체크
        if (!images.isEmpty()) {
            getImages = updateImage(hotel, images);
            for (Map<String, String> getImage : getImages) {
                imagesUrl.add(getImage.get("url"));
                imagesFileName.add(getImage.get("fileName"));
            }
        }

        hotel.Update(mainImageUrl, imagesUrl, dto);

    }

    @Transactional
    public void deleteHotel(long id) {
        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 숙소입니다.")
        );
        hotelRepo.delete(hotel);
    }

    //이미지 리스트를 받아서 Url 저장하기
    @Transactional
    public List<Map<String, String>> getImageList(List<MultipartFile> images) {
        List<Map<String, String>> imagesResult = new ArrayList<>();
        Map<String, String> mapImageResult = new HashMap<>();

        for (int i = 0; i < images.size(); i++) {
            //imageResult.add(s3service.uploadFile(images.get(i)));
            mapImageResult = s3Service.uploadFile(images.get(i));
            imagesResult.add(mapImageResult);
            System.out.println(imagesResult.get(i).get("url"));
        }
        return imagesResult;
    }

    @Transactional
    public List<Map<String, String>> updateImage(Hotel hotel, List<MultipartFile> images) {
        List<Map<String, String>> imagesResult = new ArrayList<>();
        Map<String, String> mapImageResult = new HashMap<>();

        for (int i = 0; i < images.size(); i++) {
            s3Service.deleteFile(hotel.getImagesFileName().get(i));
            mapImageResult = s3Service.uploadFile(images.get(i));
            imagesResult.add(mapImageResult);
            System.out.println(imagesResult.get(i).get("url"));
        }
        return imagesResult;
    }

    // TODO: 2022/06/20
    // main page
    public ResponseHotelsDto searchHotels(RequestHotelsDto requestHotelsDto) {
        List<MainPageHotelInfoDto> hotels = hotelRepositoryCustom.filteringHotels(requestHotelsDto);
        return ResponseHotelsDto.builder()
                .mainPageHotelInfoDtoList(hotels)
                .build();
    }

    // 상세 페이지
    public ResponseHotelDetailDto searchHotelDetail(long id) {
        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not Find the registered hotel"));
        List<Integer> facilities = new ArrayList<>();
        List<Integer> categories = new ArrayList<>();

        for (Facility facility : hotel.getFacilities()) {
            facilities.add(facility.getType());
        }

        for (Category category : hotel.getCategories()) {
            categories.add(category.getId());
        }

        double totalScore = 0;

        for (Comment comment : hotel.getComments()) {
            totalScore += comment.getScore();
        }

        double avgScore = totalScore / hotel.getComments().size();


        return ResponseHotelDetailDto.of(hotel, facilities, categories, avgScore);
    }
}
