package red.semipro.domain.service.identification;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.enums.BusinessType;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.repository.identification.IdentificationAddressRepository;
import red.semipro.domain.repository.identification.IdentificationCompanyRepository;
import red.semipro.domain.repository.identification.IdentificationIndividualRepository;
import red.semipro.domain.repository.identification.IdentificationRepository;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.service.seminar.SeminarSharedService;

/**
 * 本人確認 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IdentificationService {

    private final SeminarSharedService seminarSharedService;
    private final IdentificationRepository identificationRepository;
    private final IdentificationIndividualRepository identificationIndividualRepository;
    private final IdentificationCompanyRepository identificationCompanyRepository;
    private final IdentificationAddressRepository identificationAddressRepository;

    /**
     * 本人確認を取得します
     *
     * @param seminarId     　セミナーID
     * @return セミナーコンテンツ
     */
    public Identification findOne(@Nonnull final Long seminarId) {

        Identification identification = identificationRepository.findOneWithDetails(seminarId);
        if (Objects.isNull(identification)) {
            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }
        return identification;
    }

    /**
     * 本人確認を更新します
     *
     * @param identification 本人確認
     * @param account        アカウント
     */
    public void save(@Nonnull final Identification identification,
        @Nonnull final Account account) {

        seminarSharedService.findOneWithDetailsForUpdate(SeminarCriteria.builder()
            .id(identification.getSeminarId())
            .openingStatus(OpeningStatus.DRAFT)
            .accountId(account.getId())
            .build());

        Identification org = findOne(identification.getSeminarId());
        if (!Objects.equals(identification.getId(), org.getId())) {
            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }

        identificationIndividualRepository.delete(identification.getId());
        identificationCompanyRepository.delete(identification.getId());
        identificationAddressRepository.delete(identification.getId());

        identificationRepository.update(identification);
        identificationAddressRepository.insert(identification.getAddress());
        if (BusinessType.INDIVIDUAL.equals(identification.getBusinessType())) {
            identificationIndividualRepository.insert(identification.getIndividual());
        } else {
            identificationCompanyRepository.insert(identification.getCompany());
        }
    }
}
