package red.semipro.app.createseminar.goal;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.PlaceArrangement;

/**
 * 開催地 - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeminarPlaceForm implements Serializable {

    private static final long serialVersionUID = 2313803398589169421L;

    @NotNull
    private PlaceArrangement placeArrangement;

    private Integer prefectureId;

    private Integer cityId;

    @Size(max = 50)
    private String address;

    @Size(max = 20)
    private String place;

}
