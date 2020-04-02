package red.semipro.domain.service.email;

import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailInput {

    private EmailDocumentType emailDocumentType;

    private Map<String, Object> variableMap;

    private String recipientEmail;

    private String fromEmail;

    private Locale locale;
}
