package hanghae99.clonecoding.airbnb.service;


import hanghae99.clonecoding.airbnb.dto.ResponseHotelDetailDto;
import hanghae99.clonecoding.airbnb.dto.registerHotelDto;
import hanghae99.clonecoding.airbnb.entity.*;
import hanghae99.clonecoding.airbnb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepo;
    private final FacilityRepository facilityRepo;
    private final CategoryRepository categoryRepo;
    private final BedRoomRepository bedroomRepo;
    private final BedRepository bedRepo;
    private final AwsS3Service s3Service;

    public void registerHotel(MultipartFile mainImage, List<MultipartFile> images, registerHotelDto dto){
        Map<String , String> mainImageResult = s3Service.uploadFile(mainImage);

        //Category와 Facilities를 받아와서 저장
        List<Facility> facilities = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        List<BedRoom> bedRooms = new ArrayList<>();

        //하나의 숙소에 여러개의 침실
        //BedRoomDto에 침실 data가 들어있음
        //한 침실에 어떤타입의 침대가 몇개 존재하는지 각각 담겨져있는 BedDto
        for (int i = 0; i < dto.getBedRoom().size(); i++) {
            for (int j = 0; j <dto.getBedRoom().get(i).getData().size() ; j++) {
                Bed bed = bedRepo.findById(bedRepo.findByBedType(dto.getBedRoom().get(i).getData().get(j).getBedType())).get();
                BedRoom bedRoom = new BedRoom();
                bedRoom.addBedRooms(bed,dto.getBedRoom().get(i).getData().get(j).getCount());
                bedRooms.add(bedRoom);
                bedroomRepo.save(bedRoom);
                System.out.println(bed.getBedType());
            }
        }

        for (int i = 0; i < dto.getFacilities().size(); i++) {
            facilities.add(facilityRepo.findById(facilityRepo.findByIdList(dto.getFacilities().get(i))).get());
            System.out.println(facilities.get(i).getId());
        }

        for (int i = 0; i < dto.getCategory().size(); i++) {
            categories.add(categoryRepo.findById(categoryRepo.findByIdList(dto.getCategory().get(i))).get());
            System.out.println(categories.get(i).getId());
        }

        //대표이미지 이외의 이미지에 들어갈 url
        List<Map<String,String>> getImages = getImageList(images);
        List<String> imagesUrl = new ArrayList<>(getImages.size());
        List<String> imagesFileName = new ArrayList<>(getImages.size());

        for (Map<String, String> getImage : getImages) {
            imagesUrl.add(getImage.get("url"));
            imagesFileName.add(getImage.get("fileName"));
        }

        //메인 이미지에 들어갈 이미지의 url
        String mainImageFileName = mainImageResult.get("fileName");
        String mainImageUrl = mainImageResult.get("url");

        Hotel hotel = new Hotel(mainImageUrl , mainImageFileName ,imagesUrl , imagesFileName, dto , facilities , categories , bedRooms);
        hotelRepo.save(hotel);
    }


    //메인이미지만 바꿀때 , 그 외 사진바꿀때  아니면 다바꿀때 3가지 경우
    @Transactional
    public void modifyHotel(long id,MultipartFile mainImage , List<MultipartFile> images , registerHotelDto dto){

        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 숙소입니다.")
        );

        List<Facility> facilities = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<BedRoom> bedRooms = new ArrayList<>();

        //기존 bedRooms삭제
        bedroomRepo.deleteAll(hotel.getBedRooms());
        //하나의 숙소에 여러개의 침실
        //BedRoomDto에 침실 data가 들어있음
        //한 침실에 어떤타입의 침대가 몇개 존재하는지 각각 담겨져있는 BedDto
        for (int i = 0; i < dto.getBedRoom().size(); i++) {
            for (int j = 0; j <dto.getBedRoom().get(i).getData().size() ; j++) {
                Bed bed = bedRepo.findById(bedRepo.findByBedType(dto.getBedRoom().get(i).getData().get(j).getBedType())).get();
                BedRoom bedRoom = new BedRoom();
                bedRoom.addBedRooms(bed,dto.getBedRoom().get(i).getData().get(j).getCount());
                bedRooms.add(bedRoom);
                bedroomRepo.save(bedRoom);
                System.out.println(bed.getBedType());
            }
        }
        for (int i = 0; i < dto.getFacilities().size(); i++) {
            facilities.add(facilityRepo.findById(facilityRepo.findByIdList(dto.getFacilities().get(i))).get());
            System.out.println(facilities.get(i).getId());
        }

        for (int i = 0; i < dto.getCategory().size(); i++) {
            categories.add(categoryRepo.findById(categoryRepo.findByIdList(dto.getCategory().get(i))).get());
            System.out.println(categories.get(i).getId());
        }

        //메인이미지
        Map<String , String> mainImageResult = new HashMap<>();

        //메인이미지 이외의 사진들
        List<Map<String,String>> getImages = new ArrayList<>();
        List<String> imagesUrl = new ArrayList<>(images.size());
        List<String> imagesFileName = new ArrayList<>();

        String mainImageUrl = "";
        String mainImageFileName = "";

        //메인이미지의 수정여부 체크 후
        //기존이미지 삭제한 후 새로운 이미지 업로드
        if(mainImage != null){
            //기존 이미지 삭제 후
            s3Service.deleteFile(hotel.getMainImageFileName());
            //새로운 이미지 업로드
            mainImageResult = s3Service.uploadFile(mainImage);
            //새로운 이미지 url취득
            mainImageUrl = mainImageResult.get("url");
            mainImageFileName = mainImageResult.get("fileName");
        }

        //메인 이미지 이외의 이미지들의 수정여부 체크
        //삭제후 업로드
        if(!images.isEmpty()){
            getImages = updateImage(hotel,images);
            for (Map<String, String> getImage : getImages) {
                imagesUrl.add(getImage.get("url"));
                imagesFileName.add(getImage.get("fileName"));
            }
        }

        hotel.Update(mainImageUrl,mainImageFileName,imagesUrl,imagesFileName,dto,facilities,categories,bedRooms);

    }

    public void deleteHotel(long id){
        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 숙소입니다.")
        );
        hotelRepo.delete(hotel);
    }

    //이미지 리스트를 받아서 Url 저장하기
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

    public List<Map<String,String>> updateImage(Hotel hotel ,List<MultipartFile> images) {
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



    public ResponseHotelDetailDto searchHotelDetail(long id) {
        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not Find the Registered hotel"));
        List<Integer> facilities = new ArrayList<>();
        List<Integer> categories = new ArrayList<>();

        for (Facility facility : hotel.getFacilities()) {
            facilities.add(facility.getType());
        }

        for (Category category : hotel.getCategories()) {
            categories.add(category.getId());
        }

        return ResponseHotelDetailDto.from(hotel, facilities, categories);
    }
}
