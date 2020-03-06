package red.semipro.app.createseminar.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 法人 - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyForm implements Serializable {

    private static final long serialVersionUID = 6827628369954503835L;

    /**
     * 会社名
     */
    @NotNull
    private String name;

    /**
     * 会社名かな
     */
    @NotNull
    private String nameKana;

    /**
     * 登記番号（会社法人等番号）
     */
    private String tax;

    /**
     * 電話番号
     * <p>
     * +81形式に変換する必要がある
     */
    @NotNull
    @Pattern(regexp = "[0-9]{10,11}")
    private String phone;

}
