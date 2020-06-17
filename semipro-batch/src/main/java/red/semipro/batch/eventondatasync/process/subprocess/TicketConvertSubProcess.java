package red.semipro.batch.eventondatasync.process.subprocess;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import red.semipro.domain.eventon.model.Ticket;
import red.semipro.domain.model.eventon.EventonSeminarTicket;

@Component
@RequiredArgsConstructor
public class TicketConvertSubProcess {

    public List<EventonSeminarTicket> execute(@Nullable final List<Ticket> ticketList) {

        if (CollectionUtils.isEmpty(ticketList)) {
            return Collections.emptyList();
        }

        final List<EventonSeminarTicket> eventonSeminarTicketList = Lists.newArrayList();
        ticketList.forEach(ticket -> eventonSeminarTicketList.add(EventonSeminarTicket.builder()
            .name(ticket.getName())
            .price(ticket.getPrice())
            .build()));

        return eventonSeminarTicketList;
    }

}
