package red.semipro.domain.service.identification;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.identification.IdentificationCompany;
import red.semipro.domain.repository.identification.IdentificationCompanyRepository;

/**
 * 本人確認（法人） - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IdentificationCompanyService {

    private final IdentificationCompanyRepository identificationCompanyRepository;

    /**
     * 本人確認（法人）を登録します
     *
     * @param identificationCompany 本人確認（法人）
     * @return 本人確認（法人）
     */
    public IdentificationCompany insert(@NotNull final IdentificationCompany identificationCompany) {
        identificationCompanyRepository.insert(identificationCompany);
        return identificationCompany;
    }

    /**
     * 本人確認（法人）を更新します
     *
     * @param identificationCompany 本人確認（法人）
     * @return 更新件数
     */
    public int update(@NotNull final IdentificationCompany identificationCompany) {
        return identificationCompanyRepository.update(identificationCompany);
    }

    /**
     * 本人確認（法人）を削除します
     * @param identificationId 本人確認ID
     * @return 本人確認（法人）
     */
    public int delete(@NotNull final Long identificationId) {
        return identificationCompanyRepository.delete(identificationId);
    }
}
