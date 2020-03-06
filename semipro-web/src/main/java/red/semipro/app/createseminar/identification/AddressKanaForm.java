package red.semipro.app.createseminar.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 住所（カタカナ） - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressKanaForm implements Serializable {

    private static final long serialVersionUID = -8970519946706223427L;

    /**
     * 郵便番号（カタカナ）
     */
    @NotNull
    private String postalCode;

    /**
     * 都道府県（カタカナ）
     */
    @NotNull
    private String state;

    /**
     * 市区町村（カタカナ）
     */
    @NotNull
    private String city;

    /**
     * 町名 （カタカナ）
     */
    @NotNull
    private String town;

    /**
     * 番地（カタカナ）
     */
    @NotNull
    private String line1;

    /**
     * 建物・部屋番号（カタカナ）
     */
    @NotNull
    private String line2;
}
