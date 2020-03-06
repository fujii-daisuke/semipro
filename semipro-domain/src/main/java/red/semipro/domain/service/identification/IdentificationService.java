package red.semipro.domain.service.identification;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.repository.identification.IdentificationRepository;

/**
 * 本人確認 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IdentificationService {

    private final IdentificationRepository identificationRepository;

    /**
     * 本人確認を登録します
     * @param identification 本人確認
     * @return 本人確認
     */
    public Identification insert(@NotNull final Identification identification) {
        identificationRepository.insert(identification);
        return identification;
    }

    /**
     * 本人確認を更新します
     * @param identification 本人確認
     * @return 更新件数
     */
    public int update(@NotNull final Identification identification) {
        return identificationRepository.update(identification);
    }

}
