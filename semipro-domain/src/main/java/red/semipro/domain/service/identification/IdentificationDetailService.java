package red.semipro.domain.service.identification;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.identification.IdentificationDetail;
import red.semipro.domain.repository.identification.IdentificationDetailRepository;

/**
 * 本人確認（詳細） - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IdentificationDetailService {

    private final IdentificationDetailRepository identificationDetailRepository;

    /**
     * 本人確認（詳細）を取得します
     *
     * @param seminarId 　セミナーID
     * @return 本人確認（詳細）
     */
    public IdentificationDetail findOneBySeminarId(@NotNull final Long seminarId) {
        return identificationDetailRepository.findOneBySeminarId(seminarId);
    }
}
