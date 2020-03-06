package red.semipro.app.searchseminar;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.common.PageWrapper;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;
import red.semipro.domain.model.seminar.SeminarSummary;
import red.semipro.domain.service.seminar.SeminarSummaryService;

/**
 * セミナー検索 - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class SearchSeminarController {

    private final SeminarSummaryService seminarSummaryService;
    private final SearchSeminarFormConverter seminarFormConverter;

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
                    sort = "seminar.entry_starting_at",
                    direction = Direction.ASC
                )
            })
            Pageable pageable,
        ModelAndView model) {

        SeminarSearchCriteria criteria = seminarFormConverter.convert(form);
        criteria.setBeforeEntryEndingAt(true);
        criteria.setOpeningStatus(OpeningStatus.OPENING);
        Page<SeminarSummary> seminarPage = seminarSummaryService.search(criteria, pageable);
        PageWrapper<SeminarSummary> page = new PageWrapper<>(seminarPage, "/");

        model.addObject("page", page);
        model.setViewName("searchseminar/search");
        return model;
    }

}
