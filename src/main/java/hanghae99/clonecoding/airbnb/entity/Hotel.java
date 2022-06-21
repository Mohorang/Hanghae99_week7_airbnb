package hanghae99.clonecoding.airbnb.entity;

import hanghae99.clonecoding.airbnb.dto.registerHotelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String address;

    @Lob
    @Column
    private String description;

    @Column
    private int type;

    @Column
    private String mainImage;

    @Column
    private int bedRoomCount;

    @Column
    private int bedCount;

    @Column
    private int bathRoomCount;

    @ElementCollection
    @CollectionTable(name = "images", joinColumns = {@JoinColumn(name = "hotel_id", referencedColumnName = "id")})
    @Column
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(columnDefinition = "integer default 1")
    @Min(0)
    @Max(100)
    private int maxGuest;

    @Column(columnDefinition = "integer default 1")
    @Min(0)
    @Max(100)
    private int minGuest;

    @Column
    @Min(0)
    @Max(100)
    private int minDate;

    @Column
    @Min(0)
    @Max(100)
    private int maxDate;

    @Column
    private String traffic;

    @Column
    private String region;

    @Column
    @Min(0)
    private int defaultPrice;

    @Column
    @Min(0)
    private int cleanPrice;

    @Column
    @Min(0)
    private int servicePrice;

    @Column
    private int checkInTime = 15;

    @Column
    private int checkOutTime = 11;

    @Column(columnDefinition = "double default 0")
    private double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member host;

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();


    //이미지 삭제를 위해 추가
    @Column
    private String mainImageFileName;

    //이미지 삭제를 위해 추가
    @ElementCollection
    @CollectionTable(name = "imagesFileName", joinColumns = {@JoinColumn(name = "hotel_id", referencedColumnName = "id")})
    @Column
    @Builder.Default
    private List<String> imagesFileName = new ArrayList<>();

    public String getCheckInTime() {
        if (checkInTime / 12 == 0) {
            return "오전 " + checkInTime + ":00 이후"; // "시" 라고 표현한것을 airbnb 사이트와 일치하게 보여주기 위해 00:00 이후 라고 표현 (팀원과 상의 필요)
        } else {
            return "오후 " + (checkInTime - 12) + ":00 이후";
        }
    }

    public String getCheckOutTime() {
        if (checkOutTime / 12 == 0) {
            return "오전 " + checkOutTime + ":00";
        } else {
            return "오후 " + (checkOutTime - 12) + ":00";
        }
    }

    //숙소 등록
    public Hotel(String mainImageUrl, String mainImageFileName, List<String> imagesUrl, List<String> imagesFileName, registerHotelDto dto) {

        this.mainImage = mainImageUrl;
        this.mainImageFileName = mainImageFileName;

        this.imagesFileName = imagesFileName;
        this.images = imagesUrl;

        this.title = dto.getTitle();
        this.address = dto.getAddress();
        this.description = dto.getDescription();
        this.type = dto.getType();

        //bedrooms
//        this.bedRooms = dto.getBedRoom();
        //this.facilities = facilities;
//        this.categories = dto.getCategories();

        this.traffic = dto.getTraffic();
        this.region = dto.getRegion();
        this.maxGuest = dto.getMaxGuest();
        this.minGuest = dto.getMinGuest();
        this.minDate = dto.getMinDate();
        this.maxDate = dto.getMaxDate();
        this.defaultPrice = dto.getDefaultPrice();
        this.cleanPrice = dto.getCleanPrice();
        this.servicePrice = dto.getServicePrice();
        this.checkInTime = dto.getCheckInTime();
        this.checkOutTime = dto.getCheckOutTime();
    }

    //숙소 수정
    public void Update(String mainImageUrl, List<String> imagesUrl, registerHotelDto dto) {
        if (mainImageUrl != null) this.mainImage = mainImageUrl;
        if (!imagesUrl.isEmpty()) this.images = imagesUrl;

        this.title = dto.getTitle();
        this.address = dto.getAddress();
        this.description = dto.getDescription();
        this.type = dto.getType();
        this.traffic = dto.getTraffic();
        this.region = dto.getRegion();
        this.maxGuest = dto.getMaxGuest();
        this.minGuest = dto.getMinGuest();
        this.minDate = dto.getMinDate();
        this.maxDate = dto.getMaxDate();
        this.defaultPrice = dto.getDefaultPrice();
        this.cleanPrice = dto.getCleanPrice();
        this.servicePrice = dto.getServicePrice();
        this.checkInTime = dto.getCheckInTime();
        this.checkOutTime = dto.getCheckOutTime();
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
