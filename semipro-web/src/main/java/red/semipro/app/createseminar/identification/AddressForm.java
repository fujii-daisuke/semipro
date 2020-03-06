package red.semipro.app.createseminar.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 住所 - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressForm implements Serializable {

    private static final long serialVersionUID = -4022192671621465721L;

    /**
     * 郵便番号
     */
    @NotNull
    private String postalCode;

    /**
     * 都道府県
     */
    @NotNull
    private String state;

    /**
     * 市区町村
     * <p>
     * ex.さいたま市　緑区
     */
    @NotNull
    private String city;

    /**
     * 町名 ex.三室
     */
    @NotNull
    private String town;

    /**
     * 番地
     * <p>
     * ex.1234-5
     */
    @NotNull
    private String line1;

    /**
     * 建物・部屋番号
     * <p>
     * ex.ヤマタワー　502
     */
    @NotNull
    private String line2;

    /**
     * 都道府県（カタカナ）
     */
    @NotNull
    private String stateKana;

    /**
     * 市区町村（カタカナ）
     * <p>
     * ex.さいたま市　緑区
     */
    @NotNull
    private String cityKana;

    /**
     * 町名（カタカナ）
     * <p>
     * ex.三室
     */
    @NotNull
    private String townKana;

    /**
     * 番地（カタカナ）
     * <p>
     * ex.1234-5
     */
    @NotNull
    private String line1Kana;

    /**
     * 建物・部屋番号（カタカナ）
     * <p>
     * ex.ヤマタワー　502
     */
    @NotNull
    private String line2Kana;

}
