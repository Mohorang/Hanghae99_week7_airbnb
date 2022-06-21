package hanghae99.clonecoding.airbnb.service;

import hanghae99.clonecoding.airbnb.dto.CategoryDto;
import hanghae99.clonecoding.airbnb.dto.FacilityDto;
import hanghae99.clonecoding.airbnb.dto.FacilityTypeDto;
import hanghae99.clonecoding.airbnb.entity.Category;
import hanghae99.clonecoding.airbnb.entity.Facility;
import hanghae99.clonecoding.airbnb.enums.FacilityType;
import hanghae99.clonecoding.airbnb.repository.CategoryRepository;
import hanghae99.clonecoding.airbnb.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final CategoryRepository cateRepo;
    private final FacilityRepository facilRepo;

    public List<CategoryDto> getCategories(){
        List<CategoryDto> dtos = new ArrayList<>();
        cateRepo.findAll().stream().forEach(e->dtos.add(new CategoryDto(e)));
        return dtos;
    }
    public List<FacilityTypeDto> getFacilities(){
        List<FacilityTypeDto> ret = new ArrayList<>();
        List<Facility> facilities = facilRepo.findAll();
        for(FacilityType type : FacilityType.values()){
            List<Facility> filtered = facilities.stream().filter(e->e.getType()==type.ordinal()).collect(Collectors.toList());
            FacilityTypeDto dto = new FacilityTypeDto(type.name());
            filtered.forEach(e->dto.addFaciltiy(e));
            ret.add(dto);
        }
        return ret;

    }
}
