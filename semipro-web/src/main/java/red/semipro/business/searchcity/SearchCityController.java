package red.semipro.business.searchcity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import red.semipro.domain.model.City;
import red.semipro.domain.service.city.CityService;

@Controller
public class SearchCityController {

    @Autowired
    private CityService cityService;
    
    @GetMapping("**/searchCityList")
    @ResponseBody
    public List<City> searchCityList(@RequestParam("prefectureId")Integer prefectureId) {
        return cityService.findByPrefectureId(prefectureId);
    }
}
