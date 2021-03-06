package red.semipro.app.searchseminar;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.common.PageWrapper;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SearchSeminarCriteria;
import red.semipro.domain.service.eventon.EventonSeminarService;
import red.semipro.domain.service.seminar.SeminarDetailOutput;
import red.semipro.domain.service.seminar.SeminarDetailService;
import red.semipro.domain.service.seminar.SeminarSearchOutput;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナー検索 - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class SearchSeminarController {

    private final SeminarService seminarService;
    private final SeminarDetailService seminarDetailService;
    private final SearchSeminarFormConverter seminarFormConverter;
    private final EventonSeminarService eventonSeminarService;

    @Value("${custom.google.api.key}")
    private String GOOGLE_API_KEY;

    /**
     * セミナーを検索する
     *
     * @param form     セミナー検索フォーム
     * @param result   BindingResult
     * @param pageable Pageable
     * @param model    ModelAndView
     * @return ModelAndView
     */
//    @GetMapping
    @Deprecated
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

        SearchSeminarCriteria criteria = seminarFormConverter.convert(form);
        criteria.setBeforeEntryEndingAt(true);
        criteria.setOpeningStatus(OpeningStatus.OPENING);
        Page<Seminar> seminarPage = seminarService.search(criteria, pageable);
        PageWrapper<Seminar> page = new PageWrapper<>(seminarPage, "/");

        model.addObject("page", page);

        model.setViewName("searchseminar/search");
        return model;
    }

    @GetMapping
    public ModelAndView search(SearchSeminarForm form,
        @RequestParam(required = false,defaultValue = "0") int page,
        ModelAndView model) {

        SearchSeminarCriteria criteria = seminarFormConverter.convert(form);
        criteria.setBeforeEntryEndingAt(true);
        criteria.setOpeningStatus(OpeningStatus.OPENING);
        PagedListHolder<SeminarSearchOutput> pagedListHolder = seminarService.search(criteria);
        pagedListHolder.setPage(page);
        pagedListHolder.setPageSize(20);

        model.addObject("pagedListHolder", pagedListHolder);
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
    public ModelAndView detail(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") final Long seminarId,
        ModelAndView model) {

        final SeminarDetailOutput output =
            seminarDetailService.findDetail(seminarId,
                List.of(OpeningStatus.OPENING, OpeningStatus.CLOSED),
                Optional.ofNullable(accountUserDetails)
                    .map(AccountUserDetails::getAccount)
                    .orElse(null));

        model.addObject("output", output);
        model.setViewName("searchseminar/detail");
        return model;
    }

    @GetMapping(value = "seminars/eventon/{eventId}/detail")
    public ModelAndView eventonDetail(
        @PathVariable("eventId") final Integer eventId,
        ModelAndView model) {

        model.addObject("seminar", eventonSeminarService.findOneWithDetails(eventId));
        model.addObject("googleApiKey", GOOGLE_API_KEY);
        model.setViewName("searchseminar/eventon/detail");
        return model;
    }
}
