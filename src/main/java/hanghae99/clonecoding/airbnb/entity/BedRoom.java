package hanghae99.clonecoding.airbnb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedRoom extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(name="bedrooms",joinColumns = {@JoinColumn(name = "room_id",referencedColumnName = "id")})
    @MapKeyJoinColumn(name="bed_id")
    @Column(name ="count")
    @Builder.Default
    private Map<Bed,Integer> bedRooms = new HashMap<>();
    public ArrayList<Bed> getBeds(){
        return new ArrayList<>(this.bedRooms.keySet());
    }

    public int bedCount(Bed bed){
        return this.bedRooms.get(bed);
    }
}
