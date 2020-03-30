package red.semipro.app.searchseminar;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.common.PageWrapper;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.seminar.SeminarSharedService;

/**
 * セミナー検索 - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class SearchSeminarController {

    private final SeminarService seminarService;
    private final SearchSeminarFormConverter seminarFormConverter;
    private final SeminarSharedService seminarSharedService;

    /**
     * セミナーを検索する
     *
     * @param form     セミナー検索フォーム
     * @param result   BindingResult
     * @param pageable Pageable
     * @param model    ModelAndView
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView search(SearchSeminarForm form,
        BindingResult result,
        @PageableDefault(size = 20)
        @SortDefaults(
            {
                @SortDefault(
                    sort = "seminar.updated_at",
                    direction = Direction.ASC
                )
            })
            Pageable pageable,
        ModelAndView model) {

        SeminarSearchCriteria criteria = seminarFormConverter.convert(form);
        criteria.setBeforeEntryEndingAt(true);
        criteria.setOpeningStatus(OpeningStatus.OPENING);
        Page<Seminar> seminarPage = seminarService.search(criteria, pageable);
        PageWrapper<Seminar> page = new PageWrapper<>(seminarPage, "/");

        model.addObject("page", page);

        model.setViewName("searchseminar/search");
        return model;
    }

    /**
     * セミナー詳細画面を表示します
     *
     * @param seminarId セミナーID
     * @param model     ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "seminars/{seminarId}/detail")
    public ModelAndView detail(@PathVariable("seminarId") final Long seminarId,
        ModelAndView model) {

        model.addObject("seminar",
            seminarSharedService.findOneWithDetailsByIdAndOpeningStatusList(
                seminarId, List.of(OpeningStatus.OPENING, OpeningStatus.CLOSED)));

        model.setViewName("searchseminar/detail");
        return model;
    }
}
