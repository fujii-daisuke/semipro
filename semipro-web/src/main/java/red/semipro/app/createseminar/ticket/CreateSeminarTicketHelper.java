package red.semipro.app.createseminar.ticket;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.seminar.SeminarTicketService;

/**
 * セミナーチケット converter
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarTicketHelper {

    private final SeminarService seminarService;
    private final SeminarTicketService seminarTicketService;
    private final CreateSeminarTicketFromConverter createSeminarTicketFromConverter;

    /**
     * セミナーチケットフォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナーチケットフォーム
     */
    public CreateSeminarTicketForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, OpeningStatus.DRAFT);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        List<SeminarTicket> seminarTicketList = seminarTicketService.findAll(seminarId);
        if (Objects.isNull(seminarTicketList)) {
            seminarTicketList = List.of();
        }
        return createSeminarTicketFromConverter.convert(seminarId, seminarTicketList);
    }

    /**
     * セミナーチケットを更新します
     *
     * @param form セミナーチケットフォーム
     */
    public void save(@Nonnull final CreateSeminarTicketForm form, @Nonnull final Long accountId)
        throws IllegalAccessException {
        if (Objects.isNull(seminarService
            .findOneBy(Long.valueOf(form.getSeminarId()), accountId, OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        List<SeminarTicket> seminarTicketList = createSeminarTicketFromConverter.convert(form);
        seminarTicketService.register(Long.valueOf(form.getSeminarId()), seminarTicketList);
    }
}
