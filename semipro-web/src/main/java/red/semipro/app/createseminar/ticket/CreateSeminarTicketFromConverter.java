package red.semipro.app.createseminar.ticket;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarTicket;

/**
 * セミナーチケット - converter
 */
@Component
public class CreateSeminarTicketFromConverter {

    /**
     * セミナーチケットフォームにコンバートします
     *
     * @param seminarTicketList セミナーチケットリスト
     * @return セミナーチケットフォーム
     */
    public CreateSeminarTicketForm convert(@NotNull final Long seminarId,
        @NotNull final List<SeminarTicket> seminarTicketList) {

        if (seminarTicketList.isEmpty()) {
            seminarTicketList.add(SeminarTicket.builder().build());
        }

        List<Ticket> ticketList = seminarTicketList.stream()
            .map(s -> Ticket.builder()
                .name(s.getName())
                .price(s.getPrice())
                .capacity(s.getCapacity())
                .build())
            .collect(Collectors.toList());

        return CreateSeminarTicketForm.builder()
            .seminarId(seminarId)
            .ticketList(ticketList)
            .build();
    }

    /**
     * セミナーチケットリストにコンバートします
     *
     * @param form セミナーチケットフォーム
     * @return セミナーチケットリスト
     */
    public List<SeminarTicket> convert(@Nonnull final CreateSeminarTicketForm form) {
        return form.getTicketList().stream()
            .map(t -> SeminarTicket.builder()
                .seminarId(form.getSeminarId())
                .name(t.getName())
                .price(t.getPrice())
                .capacity(t.getCapacity())
                .build())
            .collect(Collectors.toList());
    }
}
