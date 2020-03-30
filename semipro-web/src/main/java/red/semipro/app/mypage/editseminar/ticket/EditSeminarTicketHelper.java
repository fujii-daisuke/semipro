package red.semipro.app.mypage.editseminar.ticket;

import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.service.seminar.SeminarTicketService;

/**
 * セミナーチケット converter
 */
@Component
@RequiredArgsConstructor
public class EditSeminarTicketHelper {

    private final SeminarTicketService seminarTicketService;
    private final EditSeminarTicketFromConverter editSeminarTicketFromConverter;

    /**
     * セミナーチケットフォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナーチケットフォーム
     */
    public EditSeminarTicketForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        List<SeminarTicket> seminarTicketList = seminarTicketService
            .findAllEditable(seminarId, accountId);
        return editSeminarTicketFromConverter.convert(seminarId, seminarTicketList);
    }

    /**
     * セミナーチケットを更新します
     *
     * @param form      セミナーチケットフォーム
     * @param accountId アカウントID
     */
    public void save(@Nonnull final EditSeminarTicketForm form, @Nonnull final Long accountId) {

        List<SeminarTicket> seminarTicketList = editSeminarTicketFromConverter.convert(form);
        seminarTicketService.save(form.getSeminarId(), accountId,
            seminarTicketList);
    }
}
