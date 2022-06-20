package hanghae99.clonecoding.airbnb;

import hanghae99.clonecoding.airbnb.entity.Bed;
import hanghae99.clonecoding.airbnb.entity.Category;
import hanghae99.clonecoding.airbnb.entity.Facility;
import hanghae99.clonecoding.airbnb.enums.FacilityType;
import hanghae99.clonecoding.airbnb.repository.BedRepository;
import hanghae99.clonecoding.airbnb.repository.CategoryRepository;
import hanghae99.clonecoding.airbnb.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Initialize {
    private CategoryRepository cateRepo;
    private FacilityRepository facilRepo;

    private BedRepository bedRepo;

    private List<String> categories = new ArrayList<>(List.of(new String[]{
            "섬","국립공원", "통나무집", "기상천외한 숙소","해변 근처","초소형 주택","디자인","캠핑장","A자형 주택","호숫가","북극","멋진 수영장","동굴","서핑","최고의 전망","복토 주택","열대 지역",
            "셰어하우","Luxe","B&B","캐슬","농장","한적한 시골","저택","상징적 도시","객잔","해변 바로 앞","골프장","유서 깊은 주택","돔하우스","캠핑카","키클라데스 주택","풍차","와인 농장","전문가급 주방",
            "보트","스키","료칸","컨테이너하우스","민수","창작 공간","카사 파르티쿨라르","트리하우스","마차","사막","유르트","헛간","타워","속세를 벗어난 숙소","하우스보트","그랜드 피아노","리아드","트룰로",
            "담무소","스키를 탄 채로 출입 가능","호수"}));
    private List<String> beds = new ArrayList<>(List.of(new String[]{"싱글 침대","슈퍼 싱글 침대","더블 침대","퀸 침대","킹 침대"}));
    @Autowired
    public Initialize(CategoryRepository cateRepo,FacilityRepository facilRepo,BedRepository bedRepo){
        this.cateRepo = cateRepo;
        this.facilRepo = facilRepo;
        this.bedRepo = bedRepo;
    }
    @PostConstruct
    @Transactional
    public void initializeData(){
        bedInit();
        categoryInit();
        facilityInit();
    }
    public void bedInit(){
        List<String> deleteBeds = bedRepo.findByBedTypes(beds).orElse(new ArrayList<>());
        if(deleteBeds.size() != beds.size()){
            beds.removeAll(deleteBeds);
            for(String s : beds){
                bedRepo.save(Bed.builder().bedType(s).build());
            }
        }
    }
    public void categoryInit(){
        List<String> deleteCategories = cateRepo.findByCategories(categories).orElse(new ArrayList<>());
        if(deleteCategories.size() != categories.size()) {
            categories.removeAll(deleteCategories);
            for (String s : categories) {
                cateRepo.save(Category.builder().category(s).build());
            }
        }
    }
    public void facilityInit(){
        Map<Integer,List<String>> datas = getFacilityInitData();
        for(int type : datas.keySet()){
            ArrayList<String> data = new ArrayList<>(datas.get(type));
            List<String> deleteList = facilRepo.findNameByType(type).orElse(new ArrayList<>());
            if(data.size() != deleteList.size()){
                data.removeAll(deleteList);
                data.stream().forEach(e->{
                    facilRepo.save(Facility.builder().type(type).name(e).build());
                });
            }
        }
    }
    public Map<Integer,List<String>> getFacilityInitData(){
        Map<Integer,List<String>> ret= new HashMap<>();
        ret.put(FacilityType.아름다운_전망.ordinal(),List.of(new String[]{"바다 전망","산 전망","항구 전망","만 전망","리조트 전망"}));
        ret.put(FacilityType.숙소_편의시설.ordinal(),List.of(new String[]{"욕조","헤어드라이어","온수","청소 용품","청소기","로봇 청소기","샴푸","린스","샤워젤","바디워시"}));
        ret.put(FacilityType.침실_및_세탁_시설.ordinal(),List.of(new String[]{"세탁기","건조기","필수품목","침구","여분 베개와 담요","다리미","옷걸이","의류 보관 공간:드레스룸","의류 보관 공간:옷장","금고"}));
        ret.put(FacilityType.엔터테인먼트.ordinal(), List.of(new String[]{"TV + 일반 케이블 TV","TV","24인치 HDTV","이벤트/행사 가능","탁구대","당구대","블루투스 음향시스템","도서 및 읽을거리"}));
        ret.put(FacilityType.가족.ordinal(),List.of(new String[]{"보드게임","아기 침대","귀저귀 교환대","아기 모니터","벽난로 안전 장치","창문 안전 장치","전원 콘센트 덮개","테이블 모서리 보호대"}));
        ret.put(FacilityType.냉난방.ordinal(), List.of(new String[]{"에어컨","천장 에어컨","선풍기","천장 선풍기","실내 벽난로","복사열 난방시설","온돌","난방 보일러","가스 보일러"}));
        ret.put(FacilityType.숙소_안전.ordinal(),List.of(new String[]{"화재 경보기","일산화탄소 경보기","소화기","구급상자","숙소 내 보안카메라","침실문 잠금장치"}));
        ret.put(FacilityType.인터넷_및_업무_공간.ordinal(), List.of(new String[]{"컴퓨터","무선 인터넷","유선 인터넷","업무 전용 공간"}));
        ret.put(FacilityType.주방_및_식당.ordinal(),List.of(new String[]{"주방","냉장고","소형 냉장고","냉동고","전자레인지","기본 조리도구","식기류","가스레인지 또는 인덕션","식기세척기","오븐","스테인리스 스틸 오븐","와인 잔","토스터기","제빵용 시트","믹서기","커피메이커","바비큐 도구","식탁","에어프라이기"}));
        ret.put(FacilityType.위치_특성.ordinal(),List.of(new String[]{"게스트 전용 출입문","호수나 강과 인접","해변과 인접","호수로 연결됨","리조트 무료 이용 가능"}));
        ret.put(FacilityType.주차장_및_기타_시설.ordinal(),List.of(new String[]{"수영장","스키장","건물 내 무료 주차","도로 주차","단층 주택","전용 온수 욕조","전기차 충전시설"}));
        ret.put(FacilityType.서비스.ordinal(),List.of(new String[]{"장기 숙박 가능","체크아웃 전 청소","여행 가방 보관 가능","아침식사","셀프 체크인","열쇠 보관함","반려동물 입실 가능","안내 직원","호스트가 게스트를 맞이함","흡연 가능"}));
        ret.put(FacilityType.야외.ordinal(),List.of(new String[]{"전용 파티오 또는 발코니","야외 가구","해먹","야외 식사 공간","바비큐 그릴","해수욕 필수품","카약","뒷마당","화로","전용 야외 주방"}));
        return ret;
    }
}
