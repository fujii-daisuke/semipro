package red.semipro.domain.model.seminar;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.PlaceArrangement;
import red.semipro.domain.model.City;
import red.semipro.domain.model.Prefecture;

/**
 * セミナー開催地 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarPlace implements Serializable {

    private static final long serialVersionUID = -475783353933774378L;

    /** セミナーID */
    private Long seminarId;

    /** 会場手配 */
    private PlaceArrangement placeArrangement;

    /** 都道府県 */
    private Prefecture prefecture;

    /** 市区町村 */
    private City city;

    /** 開催場所 */
    private String address;

    /** 会場名 */
    private String place;

    /** 緯度 */
    private Double lat;

    /** 経度 */
    private Double lng;

}
