package hanghae99.clonecoding.airbnb.repository;

import hanghae99.clonecoding.airbnb.dto.RequestHotelsDto;
import hanghae99.clonecoding.airbnb.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    @DisplayName("등록이 DB에 잘 저장 되는지 확인")
    void saveHotel() {
        final Member member = Member.builder()
                .id(1)
                .name("admin")
                .email("dfadsa@gmail.com")
                .password("dasfasfasfFVCZX")
                .phone("01023213431")
                .birth(LocalDate.of(1999,1,23))
                .picture("aerovnjkjfas")
                .isHost(true)
                .intro("test")
                .communicate("English")
                .build();

        final List<String> images = new ArrayList<>();
        images.add("fasfasfsafa");
        images.add("qwefsewrqrqw");

        final Category category1 = Category.builder()
                .id(1)
                .category("통나무집")
                .build();

        final Category category2 = Category.builder()
                .id(2)
                .category("B&B")
                .build();

        final List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        final Facility facility1 = Facility.builder()
                .id(1)
                .name("바다전망")
                .type(0)
                .build();

        final Facility facility2 = Facility.builder()
                .id(2)
                .name("욕조")
                .type(1)
                .build();

        final List<Facility> facilities = new ArrayList<>();
        facilities.add(facility1);
        facilities.add(facility2);

        final Comment comment = Comment.builder()
                .id(1)
                .comment("testest")
                .member(member)
                .build();

        final List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        List<String> imagesFileName = new ArrayList<>();
        imagesFileName.add("dafawqerewar");
        imagesFileName.add("davczxvewrwerwq");

        final Hotel hotel = Hotel.builder()
                .id(1)
                .title("test")
                .address("test address")
                .description("test description")
                .type(0)
                .mainImage("test mainImage")
                .bedRoomCount(2)
                .bedCount(3)
                .bathRoomCount(2)
                .images(images)
                .maxGuest(4)
                .minGuest(1)
                .maxDate(7)
                .minDate(1)
                .region("test region")
                .defaultPrice(100000)
                .cleanPrice(5000)
                .servicePrice(5000)
                .score(3.55)
                .host(member)
                .categories(categories)
                .facilities(facilities)
                .comments(comments)
                .mainImageFileName("test mainImageFileName")
                .imagesFileName(imagesFileName)
                .build();

        hotelRepository.save(hotel);
    }


//    @Test
//    void searchHotels() {
//        // when
//        List<Hotel> searchAllHotels = hotelRepository.searchAllHotels(3, 40000, 5000);
//
//        // then
//        Assertions.assertNull(searchAllHotels);
//    }
}