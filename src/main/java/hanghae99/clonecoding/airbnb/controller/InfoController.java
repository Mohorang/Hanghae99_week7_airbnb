package hanghae99.clonecoding.airbnb.controller;

import hanghae99.clonecoding.airbnb.dto.CategoryDto;
import hanghae99.clonecoding.airbnb.dto.FacilityTypeDto;
import hanghae99.clonecoding.airbnb.service.InfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class InfoController {
    private InfoService infoService;
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(){
        return infoService.getCategories();
    }
    @GetMapping("/facilities")
    public List<FacilityTypeDto> getFacilities(){
        return infoService.getFacilities();
    }

}
