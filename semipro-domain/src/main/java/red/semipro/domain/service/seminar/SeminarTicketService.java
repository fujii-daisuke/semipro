package red.semipro.domain.service.seminar;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.repository.seminar.SeminarTicketRepository;

/**
 * セミナーチケット - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarTicketService {

    private final SeminarTicketRepository seminarTicketRepository;
    private final EditableSeminarSharedService editableSeminarSharedService;

    /**
     * セミナーチケットリストを取得します
     *
     * @param seminarId     　セミナーID
     * @param accountId     アカウントID
     * @return セミナーチケットリスト
     */
    public List<SeminarTicket> findAllEditable(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        if (Objects
            .isNull(editableSeminarSharedService.findOne(seminarId, accountId))) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }
        return seminarTicketRepository.findAll(seminarId);
    }

    /**
     * セミナーチケットを登録します
     *
     * @param seminarId      セミナーIDス
     * @param accountId      アカウントID
     * @param seminarTickets セミナーチケットリスト
     */
    public void save(@Nonnull Long seminarId,
        @Nonnull final Long accountId,
        @Nonnull List<SeminarTicket> seminarTickets) {

        findAllEditable(seminarId, accountId);
        seminarTicketRepository.delete(seminarId);
        seminarTicketRepository.insert(seminarTickets);
    }

}
