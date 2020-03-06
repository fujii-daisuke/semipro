package red.semipro.domain.model.identification;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本人確認（詳細） - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationDetail implements Serializable {

    private static final long serialVersionUID = -259234106675423155L;

    private Identification identification;

    private IdentificationIndividual individual;

    private IdentificationCompany company;

    private IdentificationAddress address;
}
