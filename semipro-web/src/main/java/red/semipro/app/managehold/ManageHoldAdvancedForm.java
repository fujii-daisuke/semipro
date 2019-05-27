package red.semipro.app.managehold;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class ManageHoldAdvancedForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull
    private MultipartFile mainImage;
    @NotNull
    private String summary;
    @NotNull
    private String contents;
    @NotNull
    private String entryStartingDate;
    @NotNull
    private String entryStartingTime;
    @NotNull
    private String entryEndingDate;
    @NotNull
    private String entryEndingTime;
    @NotNull
    private Integer capacity;
    @NotNull
    private Integer minimumNumberHosts;
    @NotNull
    private Boolean shootingSupported;
    @NotNull
    private Boolean shootingEditSupported;
    @NotNull
    private Boolean movieSalesSupported;
}
