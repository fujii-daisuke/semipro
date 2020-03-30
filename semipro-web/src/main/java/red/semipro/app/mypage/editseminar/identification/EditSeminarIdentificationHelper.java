package red.semipro.app.mypage.editseminar.identification;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.service.identification.IdentificationService;

/**
 * 本人確認 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class EditSeminarIdentificationHelper {

    private final IdentificationService identificationService;
    private final EditSeminarIdentificationFormConverter formConverter;

    /**
     * 本人確認フォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return 本人確認フォーム
     */
    public EditSeminarIdentificationForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        Identification identification = identificationService.findOneEditable(seminarId, accountId);
        return formConverter.convert(identification);
    }

    /**
     * 本人確認を更新します
     *
     * @param form 本人確認フォーム
     */
    public void save(@Nonnull final EditSeminarIdentificationForm form,
        @Nonnull final Long accountId) {

        form.setIp(getRemoteAddr());
        identificationService.save(formConverter.convert(form), accountId);
    }

    private String getRemoteAddr() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        return details.getRemoteAddress();
    }
}
