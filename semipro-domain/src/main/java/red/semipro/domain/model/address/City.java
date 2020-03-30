package red.semipro.domain.model.address;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *　市区町村 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {

    private static final long serialVersionUID = -8228605298951254686L;

    /** 市区町村ID */
    private Integer id;

    /** 都道府県ID */
    private Prefecture prefecture;

    /** 市区町村名 */
    private String name;
}
