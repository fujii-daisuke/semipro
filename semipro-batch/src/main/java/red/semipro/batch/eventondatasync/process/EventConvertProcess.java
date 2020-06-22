package red.semipro.batch.eventondatasync.process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import red.semipro.batch.eventondatasync.process.subprocess.OwnerConvertSubProcess;
import red.semipro.batch.eventondatasync.process.subprocess.TicketConvertSubProcess;
import red.semipro.domain.eventon.model.Event;
import red.semipro.domain.model.address.Prefecture;
import red.semipro.domain.model.eventon.EventonSeminar;

@Component
@RequiredArgsConstructor
public class EventConvertProcess {

    private final OwnerConvertSubProcess ownerConvertSubProcess;
    private final TicketConvertSubProcess ticketConvertSubProcess;

    public EventonSeminar execute(@Nonnull final Event event) {

        return EventonSeminar.builder()
            .eventId(event.getEvent_id())
            .title(event.getTitle())
            .summary(event.getSummary())
            .contents(event.getContents())
            .imagePath(event.getImage_path())
            .startedAt(dateTimeFormatter(event.getStarted_at()))
            .endedAt(dateTimeFormatter(event.getEnded_at()))
            .openedAt(dateTimeFormatter(event.getOpened_at()))
            .entryStartedAt(dateTimeFormatter(event.getEntry_started_at()))
            .entryEndedAt(dateTimeFormatter(event.getEntry_ended_at()))
            .cancelAt(dateTimeFormatter(event.getCancel_at()))
            .capacity(event.getCapacity())
            .eventUrl(event.getEvent_url())
            .url(event.getUrl())
            .cancelPolicy(event.getCancel_policy())
            .prefecture(Optional.ofNullable(event.getPrefecture_id())
                .map(prefectureId -> Prefecture.builder().id(prefectureId).build())
                .orElse(null))
            .address(event.getAddress())
            .place(event.getPlace())
            .lat(event.getLat())
            .lng(event.getLng())
            .accepted(event.getAccepted())
            .waiting(event.getWaiting())
            .embedCode(event.getEmbed_code())
            .updatedAt(dateTimeFormatter(event.getUpdated_at()))
            .tickets(ticketConvertSubProcess.execute(event.getEvent_id(), event.getTickets()))
            .owners(ownerConvertSubProcess.execute(event.getEvent_id(), event.getOwners()))
            .build();

    }

    private LocalDateTime dateTimeFormatter(@Nullable final String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
