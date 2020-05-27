package red.semipro.domain.service.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryInput {

    private Long seminarId;

    private Long ticketId;

    private Long entryAccountId;

    private String stripeCustomerCardId;
}
