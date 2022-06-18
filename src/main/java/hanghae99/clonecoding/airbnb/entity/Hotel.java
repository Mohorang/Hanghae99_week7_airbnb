package hanghae99.clonecoding.airbnb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name="images",joinColumns = {@JoinColumn(name = "hotel_id",referencedColumnName = "id")})
    @Column
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(columnDefinition = "integer default 1")
    @Min(1)
    @Max(100)
    private int maxGuest;

    @Column(columnDefinition = "integer default 1")
    @Min(1)
    @Max(100)
    private int minGuest;

    @Column
    @Min(1)
    @Max(100)
    private int minDate;

    @Column
    @Min(1)
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
    private int checkInTime;

    @Column
    private int checkOutTime;

    @Column(columnDefinition = "double default 0")
    private double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member host;

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private List<BedRoom> bedRooms = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public String getCheckInTime(){
        if(checkInTime / 12 == 0){
            return "오전 " + checkInTime + ":00 이후"; // "시" 라고 표현한것을 airbnb 사이트와 일치하게 보여주기 위해 00:00 이후 라고 표현 (팀원과 상의 필요)
        }else{
            return "오후 " + (checkInTime-12)+ ":00 이후";
        }
    }
    public String getCheckOutTime(){
        if(checkOutTime / 12 == 0){
            return "오전 " + checkOutTime + ":00";
        }else{
            return "오후 " + (checkOutTime-12) + ":00";
        }
    }
}
