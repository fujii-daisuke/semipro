package red.semipro.domain.service.identification;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.identification.IdentificationAddress;
import red.semipro.domain.repository.identification.IdentificationAddressRepository;

/**
 * 本人確認（住所） - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IdentificationAddressService {

    private final IdentificationAddressRepository identificationAddressRepository;

    /**
     * 本人確認（住所）を登録します
     *
     * @param identificationAddress 本人確認（住所）
     * @return 本人確認
     */
    public IdentificationAddress insert(@NotNull final IdentificationAddress identificationAddress) {
        identificationAddressRepository.insert(identificationAddress);
        return identificationAddress;
    }

    /**
     * 本人確認（住所）を更新します
     *
     * @param identificationAddress 本人確認（住所）
     * @return 更新件数
     */
    public int update(@NotNull final IdentificationAddress identificationAddress) {
        return identificationAddressRepository.update(identificationAddress);
    }

    /**
     * 本人確認（住所）を削除します
     * @param identificationId 本人確認ID
     * @return 本人確認（住所）
     */
    public int delete(@NotNull final Long identificationId) {
        return identificationAddressRepository.delete(identificationId);
    }
}
