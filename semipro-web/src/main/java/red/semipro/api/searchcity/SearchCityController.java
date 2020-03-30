package red.semipro.api.searchcity;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import red.semipro.domain.model.address.City;
import red.semipro.domain.service.address.CityService;

/**
 * 市区町村検索 - controller
 */
@Controller
@RequiredArgsConstructor
public class SearchCityController {

    private final CityService cityService;

    /**
     * 市区町村を検索します
     *
     * @param prefectureId 都道府県ID
     * @return 市区町村リスト
     */
    @GetMapping("**/searchCityList")
    @ResponseBody
    public List<City> searchCityList(@RequestParam("prefectureId") Integer prefectureId) {
        return cityService.findByPrefectureId(prefectureId);
    }
}
