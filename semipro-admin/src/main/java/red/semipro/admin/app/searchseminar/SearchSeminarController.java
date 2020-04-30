package red.semipro.admin.app.searchseminar;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.seminar.SeminarSharedService;

/**
 * セミナー検索 - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/seminars")
public class SearchSeminarController {

    private final SeminarService seminarService;
    private final SearchSeminarFormConverter seminarFormConverter;
    private final SeminarSharedService seminarSharedService;

    @Value("${custom.application.web-root}")
    private String WEB_ROOT;

    /**
     * セミナー一覧を表示します
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
                    direction = Direction.DESC
                )
            }) Pageable pageable,
        ModelAndView model) {

        SeminarSearchCriteria criteria = seminarFormConverter.convert(form);
        Page<Seminar> seminarPage = seminarService.search(criteria, pageable);
        PageWrapper<Seminar> page = new PageWrapper<>(seminarPage, "/seminars");

        model.addObject("page", page);
        model.setViewName("searchseminar/search");
        return model;
    }

    /**
     * セミナー詳細を表示します
     *
     * @param seminarId 　セミナーID
     * @param model     ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/detail")
    public ModelAndView detail(@PathVariable("seminarId") final Long seminarId,
        ModelAndView model) {

        model.addObject("seminar", seminarSharedService.findOneWithDetails(seminarId));
        model.addObject("previewUrl", getPreviewUrl(seminarId));
        model.setViewName("searchseminar/detail");
        return model;
    }

    /**
     * セミナープレビュー画面のURLを返却します
     *
     * @param seminarId セミナーID
     * @return セミナープレビュー画面URL
     */
    private String getPreviewUrl(@NotNull final Long seminarId) {
        return WEB_ROOT + "seminars/" + seminarId + "/preview";
    }
}
