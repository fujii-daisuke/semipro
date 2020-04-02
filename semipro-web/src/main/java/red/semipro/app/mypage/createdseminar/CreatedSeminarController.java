package red.semipro.app.mypage.createdseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.common.PageWrapper;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * 作成済みセミナー - controller
 */
@Controller
@RequestMapping("seminars/created")
@RequiredArgsConstructor
public class CreatedSeminarController {

    private final SeminarService seminarService;

    /**
     * 作成済みセミナー一覧を表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param pageable           Pageable
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView list(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
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

        SeminarSearchCriteria criteria =
            SeminarSearchCriteria.builder().
                accountId(accountUserDetails.getAccount().getId())
                .build();
        Page<Seminar> seminarPage = seminarService.search(criteria, pageable);
        PageWrapper<Seminar> page = new PageWrapper<>(seminarPage, "/");

        model.addObject("page", page);
        model.setViewName("mypage/createdseminar/list");
        return model;
    }

}
