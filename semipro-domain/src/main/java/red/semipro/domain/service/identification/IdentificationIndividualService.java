package red.semipro.domain.service.identification;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.identification.IdentificationIndividual;
import red.semipro.domain.repository.identification.IdentificationIndividualRepository;

/**
 * 本人確認（個人） - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IdentificationIndividualService {

    private final IdentificationIndividualRepository identificationIndividualRepository;

    /**
     * 本人確認（個人）を登録します
     *
     * @param identificationIndividual 本人確認（個人）
     * @return 本人確認（個人）
     */
    public IdentificationIndividual insert(
        @NotNull final IdentificationIndividual identificationIndividual) {
        identificationIndividualRepository.insert(identificationIndividual);
        return identificationIndividual;
    }

    /**
     * 本人確認（個人）を更新します
     *
     * @param identificationIndividual 本人確認（個人）
     * @return 更新件数
     */
    public int update(@NotNull final IdentificationIndividual identificationIndividual) {
        return identificationIndividualRepository.update(identificationIndividual);
    }

    /**
     * 本人確認（個人）を削除します
     *
     * @param identificationId 本人確認ID
     * @return 本人確認（個人）
     */
    public int delete(@NotNull final Long identificationId) {
        return identificationIndividualRepository.delete(identificationId);
    }

}
