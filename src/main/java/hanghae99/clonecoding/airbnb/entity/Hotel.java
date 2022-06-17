package hanghae99.clonecoding.airbnb.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
public class Hotel extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String address;

    @Column
    private String discript;

    @Column
    private int type;

    @Column
    private String mainImage;

    @ElementCollection
    @CollectionTable(name="images",joinColumns = {@JoinColumn(name = "hotel_id",referencedColumnName = "id")})
    @Column
    private List<String> images;

    @Column(columnDefinition = "integer default 1")
    @Min(1)
    @Max(100)
    private int max;

    @Column(columnDefinition = "integer default 1")
    @Min(1)
    @Max(100)
    private int min;

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
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<BedRoom> bedRooms = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public String getCheckInTime(){
        if(checkInTime / 12 == 0){
            return "오전 " + checkInTime+"시";
        }else{
            return "오후 " + (checkInTime-12)+"시";
        }
    }
    public String getCheckOutTime(){
        if(checkOutTime / 12 == 0){
            return "오전 " + checkOutTime+"시";
        }else{
            return "오후 " + (checkOutTime-12)+"시";
        }
    }
    public Hotel() {

    }
}
