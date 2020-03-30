package red.semipro.domain.model.address;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 都道府県 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prefecture implements Serializable {

    private static final long serialVersionUID = -8101165832506514428L;

    /** 都道府県ID */
    private Integer id;

    /** 都道府県名 */
    private String name;

}
